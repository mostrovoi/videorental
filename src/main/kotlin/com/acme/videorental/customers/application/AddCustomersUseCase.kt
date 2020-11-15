package com.acme.videorental.customers.application

import com.acme.videorental.customers.application.command.AddCustomerCommand
import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository

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
