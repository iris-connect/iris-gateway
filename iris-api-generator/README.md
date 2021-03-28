# IRIS-Gateway API Generator

The IRIS-Gateway API Generator generates server and client code for IRIS-Gateway components from a central API specification.

The OpenAPI/Swagger definition of the API can be found in `src/main/resources/iris-api.yaml`. Changes in this file
affect all generated API code for the individual components of IRIS-Gateway.

Code is currently generated for the following components:

		- `iris-api-location-service`: IRIS Location Service
		- `iris-api-public-server`: IRIS Public Server
		- `iris-api-sidecar-server`: IRIS Client SORMAS Sidecar as client against IRIS Location Service and IRIS Public Server
		- `iris-api-sidecar-client`: IRIS Client SORMAS Sidecar as server for the IRIS Client Frontend
		- `iris-api-client-frontend`: IRIS Client Frontend
		- `iris-api-app-client`: the client implementation to be integrated in external apps (currently Java only)
		
To make an operation specified in the OpenAPI definition available in generated server or client code, annotate it by
the respective tag:

```
  /data-requests/{code}:
    get:
      tags:
        - "IrisPublicServer"   # <--- Server implementation
        - "IrisSidecarClient"  # <--- Client implementation
        - "IrisAppClient"      # <--- Client implementation
      summary: 'get DataRequest by Code from IRIS Public Server'
      operationId: getDataRequestByCode
  ...
```

In this example, the operation `postContactsEventsSubmission` will be available in the generated server implementation
for `IrisPublicServer` and the generated client implementation `IrisAppClient`.definition

Available tags are:
  - `IrisLocationService`
  - `IrisPublicServer`
  - `IrisSidecarServer`
  - `IrisSidecarClient`
  - `IrisClientFrontend`
  - `IrisAppClient`

** Note: ** for server implementations, the OpenAPI generator ignores all tags but the first. For annotated operations,
the server implementation must therefore be given by the first tag. All following tags can only specify targeted client APIs.
For servers acting as proxy, such as the IRIS Client SORMAS Sidecar server, the proxied servers' specification are
combined (in this case code for all operations annotated by `IrisSidecarServer`, `IrisPublicServer` or `IrisLocationService`
are generated).
