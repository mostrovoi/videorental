package com.casumo.videorental.usecase.customer

import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerRepository

class DefaultGetAllCustomersUseCase(private val customerRepository: CustomerRepository) : GetAllCustomersUseCase {
    override fun execute(): Collection<Customer> {
        return customerRepository.getAll()
    }

}