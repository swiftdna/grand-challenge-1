package gash.grpc.route.client;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
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

	private static final Route constructMessage(int mID, String path, String payload) {
		Route.Builder bld = Route.newBuilder();
		bld.setId(mID);
		bld.setOrigin(RouteClient.clientID);
		bld.setPath(path);
		bld.setDestination(201);

		byte[] hello = payload.getBytes();
		bld.setPayload(ByteString.copyFrom(hello));

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
			System.out.println("Server returned following route: " +msg);
		   }
		   @Override
		   public void onError(Throwable t) {
			System.out.println("Error while reading response fromServer: " + t);
		   }
		   @Override
		   public void onCompleted() {
			System.out.println("Server returned");
		   }
		};
		return observer;
	}
	public static void main(String[] args) {
		ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", RouteClient.port).usePlaintext().build();
		
		//non-blocking stub
		RouteServiceGrpc.RouteServiceStub asyncstub = RouteServiceGrpc.newStub(ch);
		
		final int I = 10;
		for (int i = 0; i < I; i++) {
			var msg = RouteClient.constructMessage(i, "/to/somewhere", "there are seven!");
			System.out.println("before response observer ");
			asyncstub.request(msg,getServerResponseObserver());

			// response(r);
		}
		
		while (true) {
			System.out.println("Checking..");
			try {
				Thread.sleep(1000);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		// Held until all the 10 requests are responded
		// ch.shutdown();
	}
}

