package com.acme.videorental.infrastructure.repository.customer

import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.domain.model.customer.CustomerNotFoundException
import com.acme.videorental.domain.model.customer.CustomerRepository
import java.util.*

class DummyCustomerRepository(private val customers: MutableCollection<Customer>) : CustomerRepository {

    override fun findByOrFail(customerId: CustomerId): Customer {
        val customer = customers.find { customer -> customer.customerId == customerId }
        return customer ?: throw CustomerNotFoundException("Customer with Id $customerId not found")
    }

    override fun nextIdentity(): CustomerId {
        return CustomerId(UUID.randomUUID().toString())
    }

    override fun getAll(): Collection<Customer> {
        return customers
    }

    @Synchronized
    override fun update(customer: Customer): Boolean {
        customers.removeIf { aCustomer -> aCustomer.customerId == customer.customerId }
        return customers.add(customer)
    }

    @Synchronized
    override fun add(customer: Customer): Boolean {
        return customers.add(customer)
    }
}