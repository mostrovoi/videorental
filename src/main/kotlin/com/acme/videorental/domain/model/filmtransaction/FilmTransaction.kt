package com.acme.videorental.domain.model.filmtransaction

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.domain.model.film.FilmId
import java.time.Instant

open class FilmTransaction(val filmTransactionId: FilmTransactionId, val filmId: FilmId, val customerId: CustomerId, val numDays: Int, val action: FilmActionEnum, val createdOn: Instant)

