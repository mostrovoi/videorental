package com.acme.videorental.infrastructure.controller.customer.adapter

import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.infrastructure.controller.customer.CustomerResponseDTO

class CustomerMapper {

    fun toDTO(customer: Customer): CustomerResponseDTO {
        return CustomerResponseDTO(id = customer.customerId,
                name = customer.name,
                bonusPoints = customer.bonusCard.bonusPoints)
    }
}
