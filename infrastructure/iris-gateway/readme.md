# Iris gateway helm chart

## AKDB

### Citrix issue: SSL error 61 / ca cert missing
1. Download https://www.telesec.de/assets/downloads/PKI-Repository/TeleSec_Business_CA_1.cer
1. Convert to pem: `openssl x509 -inform der -outform pem -in TeleSec_Business_CA_1.cer -out TeleSec_Business_CA_1.pem`
1. Move to citrix ca cert dir: `sudo cp TeleSec_Business_CA_1.pem /opt/Citrix/ICAClient/keystore/cacerts/`

### Proxy in AKDB (e.g. to pull docker images von dockerhub)
proxy.akdb.net:3128


## Environments
There are 2 supported environments: `test` & `production`.

Domain | LB IP
---|---
test.iris-gateway.de | 193.28.249.45
prod.iris-gateway.de | 193.28.249.53

## Prepare a cluster for deployments
1. create project `iris`
1. create namespace `iris-gateway` in that project
1. create service account `namespace-admin` in `iris-gateway`
1. create RoleBinding
    ```yaml
    apiVersion: rbac.authorization.k8s.io/v1
    kind: RoleBinding
    metadata:
      name: namespace-admin
      namespace: iris-gateway
    roleRef:
      apiGroup: rbac.authorization.k8s.io
      kind: ClusterRole
      name: admin
    subjects:
    - kind: ServiceAccount
      name: namespace-admin
      namespace: iris-gateway
    ```
1. extract token
    ```shell
    SECRET_NAME=$(kubectl -n iris-gateway get sa namespace-admin -o jsonpath='{.secrets[0].name}')
    TOKEN=$(kubectl -n iris-gateway get secret $SECRET_NAME -o json | jq -r '.data.token' | base64 -d)
    echo $TOKEN
    ```
1. add token to kubeconfig, add as secret to GitHub repo, as `KUBECONFIG_TEST` or `KUBECONFIG_PROD`
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
1. create secret `iris-gateway-locations-postgres` manually in `iris-gateway`
    ```yaml
    apiVersion: v1
    kind: Secret
    type: Opaque
    metadata:
      name: iris-gateway-locations-postgres
      namespace: iris-gateway
    data:
      POSTGRES_HOST: aXJpcy1nYXRld2F5LWxvY2F0aW9ucy1wb3N0Z3Jlcw==  # iris-gateway-locations-postgres
      POSTGRES_PASSWORD: ...  # b64 encoded
      POSTGRES_USER: ...  # b64 encoded
    ```
1. create secret `iris-gateway-tls` manually in `iris-gateway`
    ```yaml
    apiVersion: v1
    kind: Secret
    type: Opaque
    metadata:
      name: iris-gateway-tls
      namespace: iris-gateway
    data:
      ls-1.crt: ...  # b64 encoded
      ls-1.key: ...  # b64 encoded
      public-proxy-1.crt: ...  # b64 encoded
      public-proxy-1.key: ...  # b64 encoded
      root.crt: ...  # b64 encoded
      sd-1.crt: ...  # b64 encoded
      sd-1.key: ...  # b64 encoded
    ```
