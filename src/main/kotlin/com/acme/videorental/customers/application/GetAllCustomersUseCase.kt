package com.acme.videorental.customers.application

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    fun execute(): Collection<Customer> {
        return customerRepository.getAll()
    }
}