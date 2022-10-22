# Grand Challenge 1

## Preparing

Please read/lookup information on protobuf and gRPC. While these are 
separate technologies, they are coupled in the .proto file for service
specification.

### Apple M1 (ARM) Issues

Note the protobuf (protoc) plugin for java v20 or older and the java-plugin (v1.49) 
do not currently support the new apple M1 chipset. 

These two are used to build the java source code files from protobuf (.proto) files.

Options:
  - Native (M1) 
    - Protoc: Upgrade to version 3.21.x (works on m1)
    - Java plugin: **protoc-gen-java-plugin from github does not compile**.
        - There's an issue (7690) talking about apple m1 support 
        - If **you don't want to install Rosetta**
          - Generate the source files on an intel based computer and copy them to your Mac.
          - Use generated.tar.gz to get you started
  - Rosetta
    - If you have installed Rosetta then you can download the x86-64 exe from
      https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/1.49.0/
      
## Building

Building is more complex as we are now dependent upon a set of new 
libraries (jars), and build dependencies. 

  - Install gRPC, protobuf, and the java-plugin onto your computer. 
    Apple M1 users see notes above.
  - Configure lmod files (.lua) as needed
  - The project has two internal library directories (lib, lib-ref2).
  - Code generation (build_pb.sh) is required

## Running the project
Follow the below instructions to run the project

1. Update the protoc and java codegen path in the `build_pb.sh` file
2. Run the `build_pb.sh` script to generate the proto files, class files for the source code
3. Run the `runServer.sh` script to boot up the server
4. Run the `runClient.sh` script to boot up the client

## Contributors
Prashanth Subbiah
Venkata Satya Phani Sai Sravya
Ravi Shanker Thadishetti
