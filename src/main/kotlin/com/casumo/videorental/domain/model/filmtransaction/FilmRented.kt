package com.casumo.videorental.domain.model.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.film.FilmId
import java.time.Instant

class FilmRented(filmTransactionId: FilmTransactionId, filmId: FilmId, customerId: CustomerId, numDays: Int, createdOn: Instant) : FilmTransaction(filmTransactionId = filmTransactionId, filmId = filmId, customerId = customerId, numDays = numDays, action = FilmActionEnum.RENT, createdOn = createdOn)