package com.acme.videorental.infrastructure.controller.customer

import com.acme.videorental.domain.model.customer.CustomerId

data class CustomerResponseDTO(val id: CustomerId, val name: String, val bonusPoints: Int)