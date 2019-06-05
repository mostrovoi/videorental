package com.casumo.videorental.domain.model.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId

interface FilmTransactionRepository {
    fun save(filmTransaction: FilmTransaction): Boolean
    fun getAll(): Collection<FilmTransaction>
    fun findBy(customerId: CustomerId): Collection<FilmTransaction>
    fun nextIdentity(): FilmTransactionId
}