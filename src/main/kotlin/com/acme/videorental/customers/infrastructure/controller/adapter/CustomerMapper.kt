package com.acme.videorental.customers.infrastructure.controller.adapter

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.infrastructure.controller.CustomerResponseDTO

class CustomerMapper {

    fun toDTO(customer: Customer): CustomerResponseDTO {
        return CustomerResponseDTO(id = customer.customerId,
                name = customer.name,
                bonusPoints = customer.bonusCard.bonusPoints)
    }
}
