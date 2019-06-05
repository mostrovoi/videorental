package com.casumo.videorental.infrastructure.controller.customer

import com.casumo.videorental.domain.model.customer.CustomerId

data class CustomerResponseDTO(val id: CustomerId, val name: String, val bonusPoints: Int)