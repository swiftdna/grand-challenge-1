package gash.grpc.route.server;

import java.util.concurrent.LinkedBlockingDeque;

public class QueueMonitor {
    private static final int sMaxWork = 10;

	private boolean _verbose = false;
	private int _iter = 1;
	private LinkedBlockingDeque<Work> _queue;
	private LinkedBlockingDeque<Work> _completedqueue;
	private Put _put;
	private Take _take;
	private Monitor _monitor;

	public void start(boolean verbose) {
		_verbose = verbose;
		setup();
		listen();
	}

	public void setup() {
		_queue = new LinkedBlockingDeque<Work>();
		_completedqueue = new LinkedBlockingDeque<Work>();
		_put = new Put[](_queue, _verbose);
		_take = new Take(_queue,_completedqueue, _verbose);
		_monitor = new Monitor(_put, _take, QueueMonitor.sMaxWork, _verbose);
	}

	public void listen() {
		if (_verbose)
			System.out.println("--> starting queue monitor " + _iter);
		_put.start();
		_take.start();
		_monitor.start();

		int maxWaiting = 50;
		while (_take._isRunning) {
			try {
				maxWaiting--;
				if (maxWaiting == 0) {
					System.out.println("--> terminated (timed out) test " + _iter);
					_take._isRunning = false;
				}
				if (_verbose)
					System.out.println("--> waiting: " + _queue.size());
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * the work
	 * 
	 * @author gash
	 *
	 */
	public static final class Work {
		public int _id;
		public int _payload;

		public Work(int id, int payload) {
			_id = id;
			_payload = payload;
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

		@Override
		public void run() {
			while (_isRunning) {
				_genID++;
				_sum += _genID;
				if (_verbose && _genID % 10 == 0)
					System.out.println("---> putting " + _genID);
				// _q.add(new Work(_genID, _genID));

				try {
					Thread.sleep(10); // simulate variability
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
		public int _iter = 0;
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
		}

		@Override
		public void run() {
			while (_isRunning) {
				try {
					if (_q.size() > 0) {
						// Take and process it
					} else {
						// Keep checking the queue to process
						Thread.sleep(10);
					}
				} catch (Exception e) {
					// ignore - part of the test
					e.printStackTrace();
				}
			}

			if (_verbose)
				System.out.println("--> Take is done");
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
		public int _maxWork;
		public boolean _isRunning = true;

		public Monitor(Put p, Take t, int maxWork, boolean verbose) {
			_verbose = verbose;
			_put = p;
			_take = t;
			_maxWork = maxWork;
		}

		public void shutdown() {
			if (_verbose) {
				System.out.println("Monitor: shutting down..");
			}
			_isRunning = false;
			_take.shutdown();
			_put.shutdown();
		}

		@Override
		public void run() {
			while (_isRunning) {
				if (_maxWork == 0) {
					shutdown();
				} else {
					_maxWork--;
					if (_verbose) {
						System.out.println("Monitor: " + _maxWork + ", P: " + _put._sum + ", T: " + _take._sum);
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
		qm.start(true);
	}    
}
