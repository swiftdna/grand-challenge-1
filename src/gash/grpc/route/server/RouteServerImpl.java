package gash.grpc.route.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.google.protobuf.ByteString;

import gash.grpc.route.server.QueueMonitor.Work;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import route.WorkItem;
import route.RouteServiceGrpc.RouteServiceImplBase;

/**
 * copyright 2021, gash
 *
 * Gash licenses this file to you under the Apache License, version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

public class RouteServerImpl extends RouteServiceImplBase {
	private Server svr;
	private static QueueMonitor qm;
	private static int rcvdRequests = 0;
	private static int prcsdRequests = 0;
	/**
	* Configuration of the server's identity, port, and role
	*/
	private static Properties getConfiguration(final File path) throws IOException {
		if (!path.exists())
			throw new IOException("missing file");

		Properties rtn = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			rtn.load(fis);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return rtn;
	}

	/**
	 * TODO refactor this!
	 * 
	 * @param path
	 * @param payload
	 * @return
	 */
	protected ByteString process(route.Route msg) {
		// TODO placeholder
		String content = new String(msg.getPayload().toByteArray());
		// System.out.println("-- got: " + msg.getOrigin() +  ", to: " + msg.getDestination() + ", path: " + msg.getPath() + ", with: " + content);
		rcvdRequests += 1;
		// Add item to queue
		qm.addWork(msg);

		// Send ACK
		final String blank = "Request accepted";
		byte[] raw = blank.getBytes();

		return ByteString.copyFrom(raw);
	}

	protected ArrayList<WorkItem> processPolling(route.Route msg) {
		// System.out.println("msg_id -> " + msg.getId());
		// System.out.println("Checking completed queue for " + msg.getOrigin());
		return qm.fetch(msg);
	}

	public static void main(String[] args) throws Exception {
		// TODO check args!

		String path = args[0];
		try {
			Properties conf = RouteServerImpl.getConfiguration(new File(path));
			RouteServer.configure(conf);

			/* Similar to the socket, waiting for a connection */
			final RouteServerImpl impl = new RouteServerImpl();
			impl.start();
			impl.blockUntilShutdown();

		} catch (IOException e) {
			// TODO better error message
			e.printStackTrace();
		}
	}

	private void start() throws Exception {
		svr = ServerBuilder.forPort(RouteServer.getInstance().getServerPort()).addService(new RouteServerImpl())
				.build();
		qm = new QueueMonitor();

		System.out.println("-- starting server");
		svr.start();
		System.out.println("-- starting queue monitor");
		new Thread(() -> {
			qm.start(true, 2000);
		}).start();

		// new Thread(() -> {
		// 	while (true) {
		// 		try {
		// 			// Get pending pickup count
		// 			int pendingPickupCount = qm.getPendingPickupCount();
		// 			System.out.print("\rRequests received: " + rcvdRequests +" processed: "+ prcsdRequests +" pickup pending: " + pendingPickupCount + "\r");
		// 			System.out.flush();
		// 			Thread.sleep(1000);
		// 		} catch(Exception e) {
		// 			e.printStackTrace();
		// 		}
		// 	}
		// }).start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				RouteServerImpl.this.stop();
			}
		});
	}

	protected void stop() {
		svr.shutdown();
		qm.shutdown();
	}

	private void blockUntilShutdown() throws Exception {
		/* TODO what clean up is required? */
		svr.awaitTermination();
	}

	/**
	 * server received a message!
	 */
	@Override
	public void request(route.Route request, StreamObserver<route.Route> responseObserver) {

		// TODO refactor to use RouteServer to isolate implementation from
		// transportation

		route.Route.Builder builder = route.Route.newBuilder();

		// routing/header information
		builder.setId(RouteServer.getInstance().getNextMessageID());
		builder.setOrigin(RouteServer.getInstance().getServerID());
		builder.setDestination(request.getOrigin());
		builder.setPath(request.getPath());

		// do the work and reply
		if (request.getType().equals("queue_poll")) {
			ArrayList<WorkItem> items = processPolling(request);
			prcsdRequests += items.size();
			builder.addAllDatapacket(items);
		} else {
			builder.setPayload(process(request));
		}
		route.Route rtn = builder.build();
		responseObserver.onNext(rtn);
		responseObserver.onCompleted();
	}
}
