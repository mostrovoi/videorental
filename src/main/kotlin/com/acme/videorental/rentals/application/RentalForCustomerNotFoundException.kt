package com.acme.videorental.rentals.application

import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmId

class RentalForCustomerNotFoundException(filmId: FilmId, customerId: CustomerId) : Exception(String.format("Film with Id %s is not rented by user %s", filmId, customerId))