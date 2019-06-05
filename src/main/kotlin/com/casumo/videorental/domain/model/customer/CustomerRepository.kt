package com.casumo.videorental.domain.model.customer

interface CustomerRepository {
    fun findByOrFail(customerId: CustomerId): Customer
    fun getAll(): Collection<Customer>
    fun update(customer: Customer): Boolean
    fun add(customer: Customer): Boolean
    fun nextIdentity(): CustomerId
}