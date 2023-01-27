
## Solution

The exercise solution uses the reactive spring server in order to call supplier in parallel. Moreover, `RestTemplate`  
class is in maintenance mode, so  I used `WebClient` which is part of reactive stack. In term of architecture, I used the Onion architecture, aka. hexagonal architecture. The model is in the `flight` package and the business logic in `search` package. Those two packages don't know about suppliers and API related types.  
As a consequence suppliers and APIs can be added without changing them.

In order to test I wrote automatic tests covering most of the application logic. I did not test mapping code as it adds  little value. I also created  fake endpoints using [mockbin.org](http://mockbin.org) with hard coded responses. Due to the various validation, the  requests returning results need  to be from `LHR` to `JFK` from `2033-02-02` to `2033-02-28`. Once the code is running locally, you can see a response by  clicking  
on this  link [http://localhost:8080/search?origin=LHR&destination=JFK&departureDate=2033-02-02&returnDate=2033-02-28&numberOfPassenger=1](http://localhost:8080/search?origin=LHR&destination=JFK&departureDate=2033-02-02&returnDate=2033-02-28&numberOfPassenger=1).

## Missing

This code still has some TODOs to make it production ready. It also lacks security, actuator for monitoring, CI/CD configurations, etc.

## Mocked endpoints

Request to CrazyAir:

```bash  
curl -s http://mockbin.org/bin/5a263272-bd54-4688-a017-cd151a7aa0a4 | jq ''
[  
	{  
		"airline": "Air France",  
		"price": 1234.56,  
		"cabinclass": "E",  
		"departureAirportCode": "LHR",  
		"destinationAirportCode": "JFK",  
		"departureDate": "2033-02-02T06:22:00",  
		"arrivalDate": "2033-02-28T16:13:00"  
	},  
	{  
		"airline": "BMI",  
		"price": 1300.56,  
		"cabinclass": "E",  
		"departureAirportCode": "LHR",  
		"destinationAirportCode": "JFK",  
		"departureDate": "2033-02-02T07:42:00",  
		"arrivalDate": "2033-02-28T15:23:00"  
	},  
	{  
		"airline": "Delta Airline",  
		"price": 1267.56,  
		"cabinclass": "B",  
		"departureAirportCode": "LHR",  
		"destinationAirportCode": "JFK",  
		"departureDate": "2033-02-02T08:22:00",  
		"arrivalDate": "2033-02-28T20:13:00"  
	}  
]  
```  

Request to ToughJet:

```bash  
curl -s http://mockbin.org/bin/4ef7310b-307e-42a9-bca6-90b6d3b34510 | jq ''  
[  
	{  
		"carrier": "American Airline",  
		"basePrice": 2345.6,  
		"tax": 130,  
		"discount": 10,  
		"departureAirportName": "LHR",  
		"arrivalAirportName": "JFK",  
		"outboundDateTime": "2033-02-02T23:22:00Z",  
		"inboundDateTime": "2033-02-28T02:22:00Z"  
	},  
	{  
		"carrier": "British Airways",  
		"basePrice": 2145.6,  
		"tax": 160,  
		"discount": 5,  
		"departureAirportName": "LHR",  
		"arrivalAirportName": "JFK",  
		"outboundDateTime": "2033-02-02T20:22:00Z",  
		"inboundDateTime": "2033-02-28T23:22:00Z"  
	}  
]  
```  

Request to DeBlock:

```bash  
curl -X GET  'localhost:8080/search?origin=LHR&destination=JFK&departureDate=2033-02-02&returnDate=2033-02-28&numberOfPassenger=1' -H 'Accept:  
application/json' | jq '' 
```
