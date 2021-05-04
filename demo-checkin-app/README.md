# App API Demo
***
Showcases the API usage for apps. Also works as a mockup data generator for local development.

### Search Index

Search index is filled with demo data located in resources/bootstrap/locations

Second location gets immediately removed on startup. 

### Data Request

Guests Endpoint is implemented and triggers data submission with demo data to submission url. Example:

```

{
    "healthDepartment": "string",
    "keyOfHealthDepartment": "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtcEUFlnEZfDkPO/mxXwC\nNmNTjwlndnp4fk521W+lPqhQ5f8lipp6A2tnIhPeLtvwVN6q68hzASaWxbhAypp2\nBv77YRjoDacqx4gaq2eLGepb01CHNudGGvQGwhTYbfa8k13d2+z9+uN0/SrmofGc\nZvhjZWnxALGcdZRUn3Trk3O6uYrBODVk6HmpFMZKqL9tKtrCxLG17Yr9ek53sFJI\n7YuEKxFbvF/20w5rcPYsmGgkoNjGhq2ajdGwe5UfcsGOEE/4tGScF2GMZM/Tjsh9\nW9wISn6/e1v/Hhj9I19UfgasbQrE9lC1D01i1kTCjpYQ+dWcnA1Ulj2evymPpq9H\nXVoKl8LmsfQ7n9w0vAfY2sPNW3H3wN/NlcuZslUTeTopZxtt4j7b7i+7Ik62t7Yr\nCrioWQOlsWYME2YChzDCp6IlBOjeZtA9IDh6V3hbnDlV4AZygoMWWUWb1WS3oFNf\nvaJfolxZRZXDUrsrQYpJPUZ8BexE0OPEdNNS8KCa9ANbhgO3xvSTSsPpbE7P346k\nzyTCQxyJM66FLGu7vmGyt1sGiUBXFQCVYbSFNT3opke70f9/rYyzZoVA4ZBbAK7F\nazMTNzUZzt9EICWupkdrDEcyuFQ3Q/9a8Bp14zAciIAujpWNMaeO9zi57W9V0Vd4\nLyPpQIplL03J5EtG6FLHRWECAwEAAQ==\n",
    "keyReference": "string",
    "start": "2021-03-28T13:28:46.408Z",
    "end": "2021-03-28T13:28:46.408Z",
    "requestDetails": "string",
    "submissionUri": "https://localhost:8443/data-submissions/35b7df90-8670-409a-9375-15a56fd995c1/guests",
    "locationId": "35b7df90-8670-409a-9375-15a56fd995c1"
}

```

#### ToDo / ToCome
* Match demo DateTimes to requested DateTime
* Match location to requested location
* contact_events endpoint
* ...


