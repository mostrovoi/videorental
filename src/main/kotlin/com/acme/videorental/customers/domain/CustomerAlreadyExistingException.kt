package com.acme.videorental.customers.domain

class CustomerAlreadyExistingException(customerSlug: String) : Exception(String.format("Customer with slug %s already exists", customerSlug))
