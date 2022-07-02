# BANKING DEMO APPLICATION
An example of a RESTful WebServer developed using SpringBoot.

## Requirements

The fully fledged server uses the following:
* SpringBoot
## Dependencies
There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.

## Building the project
You will need:

*	Java JDK 8 or higher
*	Maven 3.1.1 or higher

Clone the project and use Maven to build the server

	$ mvn clean install


## Details
In this application exposed two endpoint.

[GET Account details](http://localhost:8080/accounts/)
- REST METHOD : POST (For security reasons)
- Media Type : JSON (Consume & Produce)
- Body
    - accountNumber
    - requestId

[Transfer funds](http://localhost:8080/transfers/)
- REST METHOD : POST (For security reasons)
- Media Type : JSON (Consume & Produce)
- Body
    - requestId
    - sourceAccountNumber
    - destinationAccountNumber
    - transferAmount

## Unit test cases covered
  - Transfer Success
  - Same account transfer negative test
  - Source account not found negative test
  - Destination account not found negative test
  - Insufficient balance