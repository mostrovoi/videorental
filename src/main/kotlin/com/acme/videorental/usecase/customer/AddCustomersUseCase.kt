package com.acme.videorental.usecase.customer

import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.usecase.customer.command.AddCustomerCommand

class AddCustomersUseCase(private val customerRepository: CustomerRepository) {
    fun execute(customerCommands: Collection<AddCustomerCommand>) {
        val customers = customerCommands.map { customerCommand ->
            Customer.aNewCustomer(customerId = customerRepository.nextIdentity(), name = customerCommand.name)
        }
        customers.forEach { customer -> addOneCustomer(customer = customer) }
    }

    private fun addOneCustomer(customer: Customer) {
        customerRepository.add(customer = customer)
    }
}
