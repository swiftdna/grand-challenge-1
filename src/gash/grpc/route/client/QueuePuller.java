package gash.grpc.route.client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import route.Route;
import route.RouteServiceGrpc;
import route.WorkItem;

public class QueuePuller {
    private static final long sleepTime = 5000;
    private static long _clientID;
    private static String _host;
    private static int _port;
	private Puller _puller;
    
    public QueuePuller(long clientID, String host, int port) {
        _clientID = clientID;
        _host = host;
        _port = port;
    }
    public void start(boolean verbose) {
        _puller = new Puller(verbose);
        _puller.start();
        System.out.println("Puller active");
    }

    public void shutdown() {
        _puller.shutdown();
    }

    public static final class Puller extends Thread {
		public boolean _verbose = false;
		public boolean _isRunning = true;

		public Puller(boolean verbose) {
			_verbose = verbose;
		}

		public void shutdown() {
			if (_verbose) {
				System.out.println("Puller: shutting down..");
			}
			_isRunning = false;
		}

        private static final Route constructQueueCheckMessage(int mID, String path) {
            Route.Builder bld = Route.newBuilder();
            bld.setId(mID);
            bld.setOrigin(_clientID);
            bld.setPath(path);
            bld.setType("queue_poll");
    
            return bld.build();
        }

        private static final void q_response(Route reply) {
            // TODO handle the reply/response from the server	
            // var payload = new String(reply.getPayload().toByteArray());
            List<route.WorkItem> pl = reply.getDatapacketList();
            // System.out.println("Q poll reply pl size: " + pl.size());
            for (WorkItem wi:pl) {
                // System.out.println("-- q data --> got "+ wi.getId() + " from "+ wi.getOrigin()+ " data: "+ wi.getMessage() +" count: "+ wi.getVowels());
                System.out.println("Msg from "+ wi.getOrigin()+ " data: "+ wi.getMessage() +" count: "+ wi.getVowels());
            }
        }

        private void checkData() {
            ManagedChannel cch = ManagedChannelBuilder.forAddress(_host, _port).usePlaintext().build();
		    RouteServiceGrpc.RouteServiceBlockingStub cstub = RouteServiceGrpc.newBlockingStub(cch);

            var msg = constructQueueCheckMessage(999, "/to/queue");
			
			// blocking!
			var r = cstub.request(msg);
			q_response(r);
        }

		@Override
		public void run() {
			while (_isRunning) {
				try {
                    // Pull items from server queue and print
                    System.out.println("Puller checking..");
                    checkData();
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			_isRunning = false;
		}
	}
}
