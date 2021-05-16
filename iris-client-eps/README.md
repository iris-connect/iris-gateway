#IRIS EPS 

To communicate with the distributed services, IRIS uses its own EPS (endpoint server). The following describes how to install and configure the EPS.

## Local development setup
### Install EPS
The following steps are necessary:
* In `iris-client-eps` directory run `docker run -v ./settings:/app/settings luckylusa/iris-eps-scripts certs` to generate certificates for development
* Run demo-eps config with `docker-compose up -d`
* `eps-client.clientUrl=https://localhost:5556/jsonrpc` should be set in iris-client-bff

### Create and change locations in location server

Create demo location by POST to https://localhost:5554/jsonrpc 
    
    {
        "method": "ls-1.postLocationsToSearchIndex",
        "id": "1",
        "params": {
            "providerId": "6b3f5dee-acb0-11eb-8529-0242ac130003",
            "locations": [
                {
                    "id": "5eddd61036d39a0ff8b11fdb",
                    "name": "Restaurant Alberts",
                    "contact": {
                        "officialName": "Darfichrein GmbH",
                        "representative": "Silke ",
                        "address": {
                            "street": "Türkenstr. 7",
                            "city": "München",
                            "zip": "80333"
                        },
                        "email": "covid2@restaurant.de",
                        "phone": "die bleibt privat :-)"
                    }
                }
            ]
        },
        "jsonrpc": "2.0"
    }
    
 That emulates how the operator would use his eps on port 5554.
 
 Delete location
 
    {
        "method": "deleteLocationFromSearchIndex",
        "id": "1",
        "params": {
            "providerId": "6b3f5dee-acb0-11eb-8529-0242ac130003",
            "locationId": "5eddd61036d39a0ff8b11fdb"
        },
        "jsonrpc": "2.0"
    }
    
 Get provider locations
 
    {
        "method": "ls-1.getLocationDetails",
        "id": "1",
        "params": {
            "providerId": "6b3f5dee-acb0-11eb-8529-0242ac130003",
            "locationId": "5eddd61036d39a0ff8b11fdb"
        },
        "jsonrpc": "2.0"
    }
    
 ### Note
 Currently providerId is used to identify the provider. That will change soon and the provider will be identified by eps via certificate.    
 
 
## Staging
Todo: 
* Create binary for linux / eps docker image
* Integrate staging config and certificates to docker image
* Deployment to staging 
