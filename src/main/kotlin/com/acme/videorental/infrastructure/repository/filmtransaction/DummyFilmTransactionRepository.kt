package com.acme.videorental.infrastructure.repository.filmtransaction

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.domain.model.filmtransaction.FilmTransaction
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionId
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import java.util.*

class DummyFilmTransactionRepository(private val filmTransactions: MutableCollection<FilmTransaction>) : FilmTransactionRepository {

    override fun findBy(customerId: CustomerId): Collection<FilmTransaction> {
        return filmTransactions.filter { filmTransaction -> filmTransaction.customerId == customerId }
    }

    override fun nextIdentity(): FilmTransactionId {
        return FilmTransactionId(UUID.randomUUID().toString())
    }

    @Synchronized
    override fun save(filmTransaction: FilmTransaction): Boolean {
        return filmTransactions.add(filmTransaction)
    }

    override fun getAll(): Collection<FilmTransaction> {
        return filmTransactions
    }
}