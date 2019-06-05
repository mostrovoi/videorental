package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.film.FilmId

class FilmTransactionForCustomerNotFoundException(filmId: FilmId, customerId: CustomerId) : Exception(String.format("Film with Id %s is not rented by user %s", filmId, customerId))