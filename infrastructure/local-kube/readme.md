in order to use these scripts the following programs are required:
* kubectl
* openssl
* k3d

##k3d install
k3d is used to quickly spin up a local cluster:

`wget -q -O - https://raw.githubusercontent.com/rancher/k3d/main/install.sh | bash`

##cluster setup
run `./setup-cluster.sh` to setup a local cluster with the required secrets preconfigured

## test local images
in order to test the deployment of a local image just build it with:
`mvn -B spring-boot:build-image -am -pl iris-backend-service -Dversion.tag=$(git rev-parse HEAD)`

## install helm chart locally
run `./local-install.sh` to create the helm on the current `KUBE_CONFIG`
## covering tracks
run `./delete-cluster.sh` to create the helm on the current `KUBE_CONFIG`

## TLDR
1. install k3d
2. `./setup-cluster.sh`
3. `./local-install.sh`
4. `./delete-cluster.sh`
