package com.casumo.videorental.domain.model.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.film.FilmId
import java.time.Instant

class FilmReturned(filmTransactionId: FilmTransactionId, filmId: FilmId, customerId: CustomerId, createdOn: Instant) : FilmTransaction(filmTransactionId = filmTransactionId, filmId = filmId, customerId = customerId, numDays = 0, action = FilmActionEnum.RETURN, createdOn = createdOn)