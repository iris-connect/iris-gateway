#!/bin/bash
k3d cluster create -p "32323-32330:32323-32330@server[0]"
kubectl apply -f storage-class.yaml
kubectl create secret generic iris-gateway-locations-postgres --from-literal=POSTGRES_PASSWORD=test
./make_certs.sh
kubectl create secret generic iris-gateway-tls --from-file=./certs
rm -rf ./certs
