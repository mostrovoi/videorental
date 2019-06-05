package com.casumo.videorental.usecase.customer

import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.usecase.customer.command.AddCustomerCommand

class DefaultAddCustomersUseCase(private val customerRepository: CustomerRepository) : AddCustomersUseCase {
    override fun execute(customerCommands: Collection<AddCustomerCommand>) {
        val customers = customerCommands.map { customerCommand ->
            Customer.aNewCustomer(customerId = customerRepository.nextIdentity(), name = customerCommand.name)
        }
        customers.forEach { customer -> addOneCustomer(customer = customer) }
    }

    private fun addOneCustomer(customer: Customer) {
        customerRepository.add(customer = customer)
    }
}
