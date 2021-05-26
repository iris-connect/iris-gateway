# Iris gateway helm chart

## Environments
There are 2 supported environments: `test` & `production`.

### test
The test kubernetes cluster, provided by AKDB. Install with `helm upgrade --install --namespace iris-gateway --set environment=test iris-gateway .`.

#### test database
Postgres is created as a container. Secrets have to be created manually _before_ deploying this chart, 
with the name `postgres-test`.

### production
The production kubernetes cluster, provided by AKDB. Install with `helm upgrade --install --namespace iris-gateway --set environment=production iris-gateway .`.

#### production database
Postgres is provided outside the kubernetes cluster. Secrets have to be created manually _before_ deploying this chart,
with the name `postgres-production`.

## TODOs
- health checks
- log configuration (JSON)
- restart after config changes

## kubeconfig template
This kubeconfig needs to be set as a secret for github actions.

```yaml
apiVersion: v1
clusters:
  - cluster:
      insecure-skip-tls-verify: true # TODO replace with CA cert
      server: https://api.k8s.akdb.net:6443
    name: default
contexts:
  - context:
      cluster: default
      user: default
    name: default
current-context: default
kind: Config
preferences: {}
users:
  - name: default
    user:
      token: <service-account-token for user namespace-admin>
```
