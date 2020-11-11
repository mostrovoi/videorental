package com.acme.videorental.usecase.customer

import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerRepository

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    fun execute(): Collection<Customer> {
        return customerRepository.getAll()
    }

}