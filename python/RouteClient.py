import logging
import sys
from threading import Thread
import time
import grpc
from generated import route_pb2
from generated import route_pb2_grpc

from random_word import RandomWords

rcvdMsgTracker = {}
sentMsgTracker = 0

def getRandomWord():
    r = RandomWords()
    return r.get_random_word()

def sendRequests():
    channel = grpc.insecure_channel('localhost:2345')
    stub = route_pb2_grpc.RouteServiceStub(channel)
    global sentMsgTracker
    while True:
        params = route_pb2.Route(id=111, origin=801, path="/python/to/somewhere", destination=501, payload=str.encode(getRandomWord()), type="regular")
        response = stub.request(params)
        sentMsgTracker += 1
        # print(response)
        time.sleep(1)

def listenMessages():
    channel = grpc.insecure_channel('localhost:2345')
    stub = route_pb2_grpc.RouteServiceStub(channel)
    
    while True:
        params = route_pb2.Route(id=111, origin=801, type="queue_poll")
        response = stub.request(params)
        # print(response)
        for item in response.datapacket:
            # print(f'got message - {item.message} from {item.origin}')
            if item.origin in rcvdMsgTracker:
                rcvdMsgTracker[item.origin] += 1
            else:
                rcvdMsgTracker[item.origin] = 1
        time.sleep(2)

def printStats():
    while True:
        # print(rcvdMsgTracker)
        # print(sentMsgTracker)
        # for client in rcvdMsgTracker:
        #     sys.stdout.write("\rReceived %i %i\n" % client % rcvdMsgTracker[client])
        # sys.stdout.write("\rSent %i" % sentMsgTracker)
        # sys.stdout.flush()
        rcvd_total_items = len(rcvdMsgTracker)
        total_items = rcvd_total_items + 1
        # print("\033[F"*total_items)
        for client in rcvdMsgTracker:
            print(
                f'Received: {rcvdMsgTracker[client]} messages from {client}'
            )
        print(
            f'Sent: {sentMsgTracker}'
        )
        # sys.stdout.flush()
        sys.stdout.write("\033[F"*total_items)
        time.sleep(2)

def run():
    req_thread = Thread(target=sendRequests)
    req_thread.start()

    puller_thread = Thread(target=listenMessages)
    puller_thread.start()

    stats_thread = Thread(target=printStats)
    stats_thread.start()

if __name__ == '__main__':
    logging.basicConfig()
    run()