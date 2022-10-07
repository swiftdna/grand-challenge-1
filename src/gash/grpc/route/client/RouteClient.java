package gash.grpc.route.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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

	private static final Route constructMessage(int mID, String path, String payload) {
		Route.Builder bld = Route.newBuilder();
		bld.setId(mID);
		bld.setOrigin(RouteClient.clientID);
		bld.setPath(path);
		bld.setDestination(dest_port);
		bld.setType("regular");

		byte[] hello = payload.getBytes();
		bld.setPayload(ByteString.copyFrom(hello));

		return bld.build();
	}
	
	private static final void response(Route reply) {
		// TODO handle the reply/response from the server	
		var payload = new String(reply.getPayload().toByteArray());
		System.out.println("reply: " + reply.getId() + ", from: " + reply.getOrigin() + ", payload: " + payload);
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

		ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", RouteClient.port).usePlaintext().build();
		RouteServiceGrpc.RouteServiceBlockingStub stub = RouteServiceGrpc.newBlockingStub(ch);

		// Start Queue Puller
		qp = new QueuePuller(clientID, port);
		qp.start(true);

		final int I = 10;
		for (int i = 0; i < I; i++) {
			var msg = RouteClient.constructMessage(i, "/to/somewhere", "hello");
			
			// blocking!
			var r = stub.request(msg);
			response(r);
		}
		// Todo: Collect all 10 requests in an array, fire them together to the server.
		// If requests are more than 50, batch it and do the same

		// Held until all the 10 requests are responded
		// ch.shutdown();

		// Runtime.getRuntime().addShutdownHook(new Thread() {
		// 	@Override
		// 	public void run() {
		// 		// RouteClient.this.stop();
		// 		qp.shutdown();
		// 		ch.shutdown();
		// 	}
		// });
		while (true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
