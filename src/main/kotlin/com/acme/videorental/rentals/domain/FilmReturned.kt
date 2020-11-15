package com.acme.videorental.rentals.domain

import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import java.time.Instant

class FilmReturned(rentalId: RentalId, filmId: FilmId, customerId: CustomerId, createdOn: Instant) : Rental(rentalId = rentalId, filmId = filmId, customerId = customerId, numDays = 0, action = FilmActionEnum.RETURN, createdOn = createdOn)