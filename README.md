# Video rental

Implementation of a video rental store using DDD inspired by the book
**Implementing domain-driven design** by Vaughn Vernon

# Run the application

This application uses **maven** as build tool. 
To run the tests execute:
```
mvnw test
```

To build the application type the following command:

```
mvnw spring-boot:run
```

## Using the application

This application includes swagger to facilitate using the exposed API. Go to the following URL to see the available operations:

```
http://localhost:8080/swagger-ui.html
```

## Design choices and technical considerations

This application has been done trying to use DDD and hexagonal architecture. 

There has been identified 4 different bounded context (customer lifecycle, film lifecycle, filmtransactions and filmpayments). 
There could be a fifth, gamification, but for the time being bonuscard has been modelled as a value object for customer.

Film transactions have an event store associated and use the in memory read model for checking the state of the movie. 
The rental itself is the core of the domain and this allows for more exploitations of the data should it be needed.

A return is considered late for one day rental if one day (3600 seconds * 24 hours) or more has passed.

## Future considerations / Improvements

* The different 'bounded contexts' are all glued up in the use case layer and share a common database. Future improvements could be to separate the data stores and move the logic to different modules if complexity starts growing.

* A transaction manager could have been used, specially in rent and return use cases to ensure all changes to database occur in the same transaction.

* A unique slug for films and customer could have been used along with the UUID to allow easier retrieval and manipulations of films and customers via API

* For simplicity an in-memory database has been used. A dockerized database e.g. Postgresql allows a closer resemblance to production environment.

* The filmtransaction repository works with the assumption that the events are in order. The column createdOn is used also as rentedOn. A different field rentedOn could have been added to allow this separation of concern. 


