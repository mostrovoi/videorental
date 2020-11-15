package com.acme.videorental.customers.infrastructure.controller

import com.acme.videorental.sharedKernel.domain.CustomerId

data class CustomerResponseDTO(val id: CustomerId, val name: String, val bonusPoints: Int)