# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

from generated import route_pb2 as route__pb2


class RouteServiceStub(object):
    """a service interface (contract)

    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.request = channel.unary_unary(
                '/route.RouteService/request',
                request_serializer=route__pb2.Route.SerializeToString,
                response_deserializer=route__pb2.Route.FromString,
                )


class RouteServiceServicer(object):
    """a service interface (contract)

    """

    def request(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_RouteServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'request': grpc.unary_unary_rpc_method_handler(
                    servicer.request,
                    request_deserializer=route__pb2.Route.FromString,
                    response_serializer=route__pb2.Route.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'route.RouteService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class RouteService(object):
    """a service interface (contract)

    """

    @staticmethod
    def request(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/route.RouteService/request',
            route__pb2.Route.SerializeToString,
            route__pb2.Route.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
