# IRIS Backend Services

One module of this is the Location Service.  
This service allowing [Contact Tracing Apps](https://www.wirfuerdigitalisierung.de/ber-uns) to upload their location data (Restaurants, Shops, Hairdressers). The index can be searched by Healthe Authoritiy employees to easily request contact data from those IRIS enabled locations. 

The second module is the Alert Service.  
Other components can send an alert to this service, which caches them and sends them to Slack and Zammad depending on the type. 

## Postman Collection

Postman is a useful tool to test the API. 

1) Download [Postman](https://www.postman.com)
2) Import [Localhost Environment](postman/IRIS%20localhost.postman_environment.json)
3) Import [Staging Environment](postman/IRIS%20staging.postman_environment.json)
4) Import [Collection](postman/iris-location-service.postman_collection.json)
