package com.acme.videorental.customers.domain

class CustomerNotFoundException(customerId: String) : Exception("Customer with id ${customerId} not found")
