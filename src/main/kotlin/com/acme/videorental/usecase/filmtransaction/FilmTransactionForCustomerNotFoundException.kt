package com.acme.videorental.usecase.filmtransaction

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.domain.model.film.FilmId

class FilmTransactionForCustomerNotFoundException(filmId: FilmId, customerId: CustomerId) : Exception(String.format("Film with Id %s is not rented by user %s", filmId, customerId))