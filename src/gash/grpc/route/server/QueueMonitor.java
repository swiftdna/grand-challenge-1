package gash.grpc.route.server;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import route.Route;
import route.WorkItem;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.File;
import java.io.FileWriter; 
import java.io.IOException;
import java.security.spec.ECFieldF2m;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class QueueMonitor {
    // private static final int sMaxWork = 10;
    private static final int sleepTime = 200;
	private static int _num_threads;
	private static int _pendingPickupCount = 0;
	private static int _addedItems = 0;
	private boolean _verbose = false;
	private LinkedBlockingDeque<Work> _queue;
	private LinkedBlockingDeque<Work> _completedqueue;
	private Put _put;
	private Take _take;
	private Monitor _monitor;
	public Thread threadPool[];
	public static Map<Long,Integer> overallThreadLoadMap = new HashMap<>();
	public static Map<Long, Long> overallTaskTimeMap = new HashMap<>();
	public static Map<Integer,Double> overallCPULoadMap = new HashMap<>();
	public void start(boolean verbose, int num_threads) {
		_verbose = verbose;
		if (num_threads > 0) {
			_num_threads = num_threads;
		} else {
			// default threads
			_num_threads = 10;
		}
		threadPool = new Thread[_num_threads];
		setup();
		listen();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				QueueMonitor.this.shutdown();
			}
		});
	}

	public void shutdown() {
		_monitor.shutdown();
	}

	public void setup() {
		_queue = new LinkedBlockingDeque<Work>();
		_completedqueue = new LinkedBlockingDeque<Work>();
		_put = new Put(_queue, _verbose);
		_take = new Take(_queue, _completedqueue, _verbose);
		
		for (int i = 0; i < _num_threads; i++) {
            threadPool[i] = new Thread(new Take(_queue, _completedqueue, _verbose));
        }
		//_take = new Take(_queue, _completedqueue, _verbose);
		_monitor = new Monitor(_put, _take, _queue, _completedqueue, _verbose);
	}

	public void listen() {
		if (_verbose)
			System.out.println("--> starting queue monitor ");
		_put.start();
		//_take.start();
		for (int i = 0; i < _num_threads; i++) {
			if (threadPool[i] != null) {
				if (_verbose) {
					System.out.println("Starting take thread " + threadPool[i].getId());
				}
				threadPool[i].start();
			}
        }
		_monitor.start();

		int maxWaiting = 5000;
		while (_take!=null && _take._isRunning) {
			try {
				maxWaiting--;
				if (maxWaiting == 0) {
					System.out.println("--> terminated (timed out)");
					_take._isRunning = false;
				}
				if (_verbose)
					System.out.println("--> listening with current queue size: " + _queue.size());
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static long getTimeNow() {
		return System.currentTimeMillis();
	}

	public static long calcTimeDifference(long startTime) {
		long endTime = getTimeNow();
		return endTime - startTime;
	}

	public void addWork(route.Route msg) {
		String content = new String(msg.getPayload().toByteArray());
		Work input_work = new Work((int) msg.getId(), content, (int)msg.getOrigin(), (int)msg.getDestination());
		Long task_id = msg.getId();
		_put.add(input_work);
		overallTaskTimeMap.put(task_id, getTimeNow());
		_addedItems += 1;
	}

	public int getPendingPickupCount() {
		return _pendingPickupCount;
	}

	// public ArrayList<WorkItem.Builder> fetch(route.PollingRequest msg) {
	public ArrayList<WorkItem> fetch(route.Route msg) {
		// Extract sender id
		long senderID = msg.getOrigin();
		int length = _completedqueue.size();
		ArrayList<WorkItem> workItems = new ArrayList<WorkItem>();
		// WorkItem.Builder builder = WorkItem.newBuilder();
		// ArrayList<Work> workItems = new ArrayList<Work>();
		while (length > 0) {
			Work w = _completedqueue.pop();
			if (w._receiver == (int)senderID) {
				WorkItem.Builder workBuilder = WorkItem.newBuilder();
				workBuilder.setId(w._id);
				workBuilder.setOrigin(w._sender);
				workBuilder.setMessage(w._message);
				workBuilder.setDestination(w._receiver);
				workBuilder.setVowels(w._vowels);
				// workItems.add(workBuilder);
				workItems.add(workBuilder.build());
			} else {
				// If it doesn't belong to the requesting client, then add it back
				_completedqueue.add(w);
			}
			length--;
		}
		return workItems;
	}

	/**
	 * the work
	 * 
	 * @author gash
	 *
	 */
	public static final class Work {
		public int _id;
		public String _message;
		public int _sender;
		public int _receiver;
		public int _vowels;

		public Work(int id, String message, int sender, int receiver) {
			_id = id;
			_message = message;
			_sender = sender;
			_receiver = receiver;
		}

		public void calculateVowels(){
			int count = 0;
			for (int i=0 ; i<_message.length(); i++){
				char ch = _message.charAt(i);
				if(ch == 'a'|| ch == 'e'|| ch == 'i' ||ch == 'o' ||ch == 'u'||ch == ' '){
				   count ++;
				}
			}
			try {
				Thread.sleep(1000);
			} catch(Exception e) {
				e.printStackTrace();
			}
			_vowels = count;
		}
	}

	/**
	 * puts work on the queue
	 * 
	 * @author gash
	 *
	 */
	public static final class Put extends Thread {
		public boolean _verbose = false;
		public int _genID = 0;
		public int _sum = 0;
		public boolean _isRunning = true;
		public LinkedBlockingDeque<Work> _q;

		public Put(LinkedBlockingDeque<Work> q, boolean verbose) {
			_verbose = verbose;
			_q = q;
		}

		public void shutdown() {
			if (_verbose) {
				System.out.println("Put: shutting down..");
			}
			_isRunning = false;
		}

		public static Double getProcessCpuLoad() {
			try {
				MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
				ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
				AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});
		
				return Optional.ofNullable(list)
						.map(l -> l.isEmpty() ? null : l)
						.map(List::iterator)
						.map(Iterator::next)
						.map(Attribute.class::cast)
						.map(Attribute::getValue)
						.map(Double.class::cast)
						.orElse(null);
		
			} catch (Exception ex) {
				return null;
			}
		}

		public void add(Work w) {
			double x = getProcessCpuLoad();
			overallCPULoadMap.put(_addedItems, x);
			_q.add(w);
		}

		@Override
		public void run() {
			while (_isRunning) {
				// _q.add(new Work(_genID, _genID));
				try {
					Thread.sleep(sleepTime); // simulate variability
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			_isRunning = false;
			if (_verbose)
				System.out.println("--> Put is done");
		}
	}

	/**
	 * takes work from the queue
	 * 
	 * @author gash
	 *
	 */
	public static final class Take extends Thread {
		public boolean _verbose = false;
		public int _sum = 0;
		public boolean _isRunning = true;
		public LinkedBlockingDeque<Work> _q;
		public LinkedBlockingDeque<Work> _pq;

		public Take(LinkedBlockingDeque<Work> q, LinkedBlockingDeque<Work> pq, boolean verbose) {
			_verbose = verbose;
			_q = q;
			_pq = pq;
		}

		public void shutdown() {
			if (_verbose) {
				System.out.println("Take: shutting down..");
			}
			_isRunning = false;
			// Write the stats to file
			writeOverallThreadLoad("overallThreadLoad.csv");
			writeOverallThreadTaskTime("overallThreadTaskTime.csv");
			writeOverallCPULoad("overallCPULoad.csv");
		}

		public void writeOverallThreadTaskTime(String fileName) {
			try {
				FileWriter myWriter = new FileWriter(fileName);
				for (Map.Entry<Long,Long> entry : overallTaskTimeMap.entrySet()) { 
				   // put key and value separated by a colon 
				   int length = String.valueOf(entry.getValue()).length();
				   	if (length < 7) {
					   myWriter.write("Task "+entry.getKey() + ":" + entry.getValue()+"\n"); 
				   	}
					// System.out.println(entry.getKey()+"         "+entry.getValue());
				}
				myWriter.close();
				System.out.println("Successfully wrote thread time to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

		public void writeOverallThreadLoad(String fileName) {
			try {
				FileWriter myWriter = new FileWriter(fileName);
				for (Map.Entry<Long,Integer> entry : overallThreadLoadMap.entrySet()) { 
				   // put key and value separated by a colon 
					   myWriter.write("Thread "+entry.getKey() + ":" + entry.getValue()+"\n"); 
					// System.out.println(entry.getKey()+"         "+entry.getValue());
				   } 
				myWriter.close();
				// System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

		public void writeOverallCPULoad(String fileName) {
			try {
				FileWriter myWriter = new FileWriter(fileName);
				for (Map.Entry<Integer,Double> entry : overallCPULoadMap.entrySet()) {
					if (entry.getValue() >= 0) {
						myWriter.write(entry.getKey() + ":" + entry.getValue()+"\n"); 
					}
				} 
				myWriter.close();
				System.out.println("Successfully wrote writeOverallCPULoad to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			long currentThreadID = Thread.currentThread().getId();
			
			while (_isRunning) {
				try {
					// Update overallThreadLoadMap
					int count = overallThreadLoadMap.containsKey(currentThreadID) ? overallThreadLoadMap.get(currentThreadID) : 0;
					overallThreadLoadMap.put(currentThreadID, count + 1);
					Work x = _q.take();
					Long task_id = (long) x._id;
					if (_verbose) {
						System.out.println("got "+ x._message+ " from: "+ x._sender + " processed by thread: "+ currentThreadID);
					}
					// Processing code
					x.calculateVowels();
					_pq.add(x);
					Long added_time = overallTaskTimeMap.get(task_id);
					overallTaskTimeMap.put(task_id, calcTimeDifference(added_time));
				} catch (Exception e) {
					// ignore - part of the test
					e.printStackTrace();
				}
			}

			if (_verbose)
				System.out.println("--> Bye from Take " + currentThreadID);
		}
	}

	/**
	 * monitors work performed
	 * 
	 * @author gash
	 *
	 */
	public static final class Monitor extends Thread {
		public boolean _verbose = false;
		public Put _put;
		public Take _take;
		public boolean _isRunning = true;
		public LinkedBlockingDeque<Work> _pq;
		public LinkedBlockingDeque<Work> _cq;

		public Monitor(Put p, Take t, LinkedBlockingDeque<Work> pq, LinkedBlockingDeque<Work> cq,boolean verbose) {
			_verbose = verbose;
			_put = p;
			_take = t;
			_cq = cq;
			_pq = pq;
		}

		public void shutdown() {
			if (_verbose) {
				System.out.println("Monitor: shutting down..");
			}
			_isRunning = false;
			_take.shutdown();
			_put.shutdown();
		}

		private static void printUsage() {
			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
			// What % CPU load this current JVM is taking, from 0.0-1.0
			System.out.println("printUsage");
			// System.out.println(operatingSystemMXBean.getProcessCpuLoad());
			for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
				method.setAccessible(true);
				System.out.println(method.getName());
				if (method.getName().startsWith("get")
					&& Modifier.isPublic(method.getModifiers())) {
						Object value;
					try {
						value = method.invoke(operatingSystemMXBean);
					} catch (Exception e) {
						value = e;
					} // try
					System.out.println(method.getName() + " = " + value);
				} // if
			} // for
		}

		public static Double getProcessCpuLoad() {
			try {
				MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
				ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
				AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});
		
				return Optional.ofNullable(list)
						.map(l -> l.isEmpty() ? null : l)
						.map(List::iterator)
						.map(Iterator::next)
						.map(Attribute.class::cast)
						.map(Attribute::getValue)
						.map(Double.class::cast)
						.orElse(null);
		
			} catch (Exception ex) {
				return null;
			}
		}

		@Override
		public void run() {
			while (_isRunning) {
				try {
					// Scale up or down the thread pool size based on the work load
					if (_verbose) {
						System.out.println("Pending Queue: " + _pq.size() + " Completed items: " + _cq.size());
					}
					// Print CPU stats
					// double x = getProcessCpuLoad();
					// overallCPULoadMap.put(_addedItems, x);
					// System.out.println("cpu load - " + x);
					_pendingPickupCount = _cq.size();
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * run our test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		QueueMonitor qm = new QueueMonitor();
		qm.start(true, 5);
	}    
}
