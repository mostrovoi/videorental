package com.casumo.videorental.infrastructure.repository.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.filmtransaction.FilmTransaction
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionId
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
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