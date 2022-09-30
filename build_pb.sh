#!/bin/bash
#
# build the gRPC/protobuf (proto3) classes from the .proto. 
#
# NOTE this requires the java grpc codegen plugin
#

project_base="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# generated code is put here
generated_base=${project_base}/generated

# define protoc to use - otherwise it uses your env path
PROTOC_HOME=/opt/homebrew/Cellar/protobuf/21.6

# REALLY IMPORTANT: what/where is your java codegen plugin
#java_codegen=/usr/local/grpc/java-plugin-1.40.0/install/java_plugin/protoc-gen-grpc-java

# intel processors only (not apple m1)
java_codegen=/Users/prashanth/Downloads/protoc-gen-grpc-java-1.49.0-osx-x86_64.exe

if [ -d ${generated_base} ]; then
  echo -e "\n* removing contents of ${generated_base}"
  rm -r ${generated_base}/*
else
  echo -e "\n* creating directory ${generated_base}"
  mkdir ${generated_base}
fi

# for all .proto files in resources
for p in `ls ${project_base}/resources/*.proto`; do
   base=$(basename $p |cut -f1 -d'.')
   echo -e "\n* compiling $base"
   $PROTOC_HOME/bin/protoc \
        --proto_path=${project_base}/resources \
        --java_out=${generated_base} \
        --plugin=protoc-gen-grpc-java=${java_codegen} \
        --grpc-java_out=${generated_base} $p
done

echo -e "\n* done, created:\n"
find ${generated_base} -name "*.java" | xargs basename
echo -e "\n"

echo -e "*** generating class files ***\n"
route_jv_files=${project_base}/generated/route/*
server_jv_files=${project_base}/src/gash/grpc/route/server/*
client_jv_files=${project_base}/src/gash/grpc/route/client/*

javac -classpath .:${project_base}/lib/'*':${project_base}/lib-ref2/'*':${project_base}/classes -d ${project_base}/classes ${route_jv_files}
javac -classpath .:${project_base}/lib/'*':${project_base}/lib-ref2/'*':${project_base}/classes -d ${project_base}/classes ${server_jv_files}
javac -classpath .:${project_base}/lib/'*':${project_base}/lib-ref2/'*':${project_base}/classes -d ${project_base}/classes ${client_jv_files}
echo -e "*** generated classes ***\n"

echo -e "Now run server and client scripts to proceed\n"