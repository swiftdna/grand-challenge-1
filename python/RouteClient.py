import logging
import grpc
from generated import route_pb2
from generated import route_pb2_grpc

def run():
    channel = grpc.insecure_channel('localhost:2345')
    stub = route_pb2_grpc.RouteServiceStub(channel)
    params = route_pb2.Route(id=111, origin=801, path="/python/to/somewhere", destination=501, payload=str.encode("hello from Python 1"), type="regular")
    response = stub.request(params)
    print(response)

if __name__ == '__main__':
    logging.basicConfig()
    run()