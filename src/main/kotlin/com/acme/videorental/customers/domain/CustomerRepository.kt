package com.acme.videorental.customers.domain

import com.acme.videorental.sharedKernel.domain.CustomerId

interface CustomerRepository {
    fun findByOrFail(customerId: CustomerId): Customer
    fun getAll(): Collection<Customer>
    fun update(customer: Customer): Boolean
    fun add(customer: Customer): Boolean
    fun nextIdentity(): CustomerId
}