cd ..
project_base="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# echo ${project_base}

# generated code is put here
generated_base=${project_base}/python/generated
# echo ${generated_base}
# define protoc to use - otherwise it uses your env path
PROTOC_HOME=/opt/homebrew/Cellar/protobuf/21.6

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
   echo -e "\n* compiling $p"
   python3.9 -m grpc_tools.protoc --proto_path=generated=proto -I${project_base}/resources --python_out=${generated_base} --grpc_python_out=${generated_base} $p
done
  #  $PROTOC_HOME/bin/protoc \
  #       --proto_path=${project_base}/resources \
  #         --grpc_python_out=${generated_base} \
  #       --python_out=${generated_base} $p

# protoc -I=. --python_out={generated_base} ${project_base}/resources
echo -e "\n* compilation success"