package com.casumo.videorental.usecase.customer

import com.casumo.videorental.domain.model.customer.Customer


interface GetAllCustomersUseCase {
    fun execute(): Collection<Customer>
}
