package gash.grpc.route.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SupportedCipherSuiteFilter;
import route.Route;
import route.RouteServiceGrpc;

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

public class RouteClient {
	private static long clientID = 501;
	private static int port = 2345;
	private static int dest_port = 201;
	private static int requestsSent = 0;
	private static String host = "localhost";
	private static QueuePuller qp;

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

	public static String createRandomWord(int len) {
        String name = "";
        for (int i = 0; i < len; i++) {
            int v = 1 + (int) (Math.random() * 26);
            char c = (char) (v + (i == 0 ? 'A' : 'a') - 1);
            name += c;
        }
        return name;
    }

	private static final Route constructMessage(int mID, String path) {
		Route.Builder bld = Route.newBuilder();
		// System.out.println(100000 * clientID + requestsSent);
		bld.setId(100000 * clientID + requestsSent);
		bld.setOrigin(RouteClient.clientID);
		bld.setPath(path);
		bld.setDestination(dest_port);
		bld.setType("regular");

		String payload = createRandomWord(10);
		byte[] randomPayload = payload.getBytes();
		bld.setPayload(ByteString.copyFrom(randomPayload));

		return bld.build();
	}
	
	private static final void response(Route reply) {
		// TODO handle the reply/response from the server	
		var payload = new String(reply.getPayload().toByteArray());
		System.out.println("reply: " + reply.getId() + ", from: " + reply.getOrigin() + ", payload: " + payload);
	}

	public static StreamObserver<route.Route> getServerResponseObserver(){
		StreamObserver<route.Route> observer = new StreamObserver<route.Route> (){
		   @Override
		   public void onNext(Route msg) {
			// String payload = new String(msg.getPayload().toByteArray());
			// System.out.println("Server returned following route: " +payload);
			// response(msg);
		   }
		   @Override
		   public void onError(Throwable t) {
			System.out.println("Error while reading response fromServer: " + t);
		   }
		   @Override
		   public void onCompleted() {
			// System.out.println("Server returned");
		   }
		};
		return observer;
	}
	public static void main(String[] args) {

		// Get properties from file and override the variables
		String path = args[0];
		if (!path.isEmpty()) {
			try {
				Properties conf = getConfiguration(new File(path));
				String portStr = conf.getProperty("client.port");
				if (portStr == null)
					throw new RuntimeException("Server ID missing");
				port = Integer.parseInt(portStr);
				host = conf.getProperty("client.host");
				if (host == null)
					throw new RuntimeException("Server Port missing");	
				String destPortStr = conf.getProperty("client.dest");
				if (destPortStr == null)
					throw new RuntimeException("Destination ID missing");
				dest_port = Integer.parseInt(destPortStr);
				String clientIDStr = conf.getProperty("client.id");
				if (clientIDStr == null)
					throw new RuntimeException("Client ID missing");
				clientID = Long.parseLong(clientIDStr);
				System.out.println("Propery files overwritten!");
			} catch (IOException e) {
				// TODO better error message
				e.printStackTrace();
			}
		}

		// Start Queue Puller
		qp = new QueuePuller(clientID, host, port);
		qp.start(true);

		// Print stats
		// new Thread(() -> {
		// 	while (true) {
		// 		try {
		// 			// Get pending pickup count
		// 			int receivedMsgsCount = qp.getReceivedMsgsCount();
		// 			System.out.print("\rRequests sent: " + requestsSent +" msgs received: "+ receivedMsgsCount + "\r");
		// 			System.out.flush();
		// 			Thread.sleep(1000);
		// 		} catch(Exception e) {
		// 			e.printStackTrace();
		// 		}
		// 	}
		// }).start();

		//non-blocking stub
		ManagedChannel ch = ManagedChannelBuilder.forAddress(RouteClient.host, RouteClient.port).usePlaintext().build();
		RouteServiceGrpc.RouteServiceStub asyncstub = RouteServiceGrpc.newStub(ch);
		System.out.println(RouteClient.host+ RouteClient.port);
		while (true) {
			try {
				final int I = 1000;
				for (int i = 0; i < I; i++) {
					var msg = RouteClient.constructMessage(i, "/to/somewhere");
					requestsSent += 1;
					System.out.println("Sending request.. " + requestsSent);
					asyncstub.request(msg,getServerResponseObserver());
				}
				// System.out.println("Sent "+ I + " requests..");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Runtime.getRuntime().addShutdownHook(new Thread() {
		// 	@Override
		// 	public void run() {
		// 		// RouteClient.this.stop();
		// 		qp.shutdown();
		// 		ch.shutdown();
		// 	}
		// });
		// Held until all the 10 requests are responded
		// ch.shutdown();
		
	}
}

