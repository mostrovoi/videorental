package com.acme.videorental.rentals.domain

import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import java.time.Instant

class FilmRented(rentalId: RentalId, filmId: FilmId, customerId: CustomerId, numDays: Int, createdOn: Instant) : Rental(rentalId = rentalId, filmId = filmId, customerId = customerId, numDays = numDays, action = FilmActionEnum.RENT, createdOn = createdOn)