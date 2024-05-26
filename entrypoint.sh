#!/bin/bash
/entrypoint.sh couchbase-server &

# Wait for Couchbase server to be up
until $(curl --output /dev/null --silent --head --fail http://127.0.0.1:8091); do
    echo "Waiting for Couchbase server to start..."
    sleep 5
done
# Cluster init
couchbase-cli cluster-init -c 127.0.0.1 --cluster-username admin --cluster-password password \
  --services data,index,query,fts --cluster-ramsize 1024 --cluster-index-ramsize 512 --cluster-fts-ramsize 256

# Bucket create
couchbase-cli bucket-create -c 127.0.0.1 --username admin --password password \
  --bucket todo --bucket-type couchbase --bucket-ramsize 256 --enable-flush 1

echo "Couchbase setup complete."
# Keep the container running
tail -f /dev/null