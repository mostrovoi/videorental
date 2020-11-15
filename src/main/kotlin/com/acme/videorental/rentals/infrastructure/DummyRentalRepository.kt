package com.acme.videorental.rentals.infrastructure

import com.acme.videorental.rentals.domain.Rental
import com.acme.videorental.rentals.domain.RentalId
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.sharedKernel.domain.CustomerId
import java.util.*

class DummyRentalRepository(private val filmTransactions: MutableCollection<Rental>) : RentalRepository {

    override fun findBy(customerId: CustomerId): Collection<Rental> {
        return filmTransactions.filter { filmTransaction -> filmTransaction.customerId == customerId }
    }

    override fun nextIdentity(): RentalId {
        return RentalId(UUID.randomUUID().toString())
    }

    @Synchronized
    override fun save(filmTransaction: Rental): Boolean {
        return filmTransactions.add(filmTransaction)
    }

    override fun getAll(): Collection<Rental> {
        return filmTransactions
    }
}