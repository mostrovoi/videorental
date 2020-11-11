package com.acme.videorental.domain.model.customer

class CustomerNotFoundException(customerId: String) : Exception("Customer with id ${customerId} not found")