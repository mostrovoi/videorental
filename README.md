# Video rental

Implementation of a video rental store using DDD inspired by the book
**Implementing domain-driven design** by Vaughn Vernon

**Application description:**

We wamt to build an online video rental store with three main features:

1.- Have an inventory of all the films
  List all films available and existing in the online store  
2.- Calculate the price for rentals 
  The price of rentals is based on the type of film and how many days the film is rented. The customers say when renting for how many days they want to rent and pay upfront. If the film is returned late, then money for the extra days is charged when returning.
  The online store has 3 types of films: 
   1.- **new film:** price is <PREMIUM PRICE> times number of days rented
   2.- **regular film:** price is <BASIC PRICE> for the first 3 days and then <BASIC PRICE> times the number of days over 3
   3.- **old film:** price is <BASIC PRICE> for the first 5 days and then <BASIC PRICE> times the number of days over 5
3.- Keep track of the costumers bonus points
  Customers get bonus points when renting films. A new release gives 2 points and other films give one point per rental

## Design choices and technical considerations

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



