package com.acme.videorental.domain.model.customer

class CustomerAlreadyExistingException(customerSlug: String) : Exception(String.format("Customer with slug %s already exists", customerSlug))
