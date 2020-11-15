package com.acme.videorental.rentals.domain

import com.acme.videorental.sharedKernel.domain.CustomerId

interface RentalRepository {
    fun save(filmTransaction: Rental): Boolean
    fun getAll(): Collection<Rental>
    fun findBy(customerId: CustomerId): Collection<Rental>
    fun nextIdentity(): RentalId
}