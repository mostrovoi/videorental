package com.acme.videorental.rentals.domain

import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import java.time.Instant

open class Rental(val rentalId: RentalId, val filmId: FilmId, val customerId: CustomerId, val numDays: Int, val action: FilmActionEnum, val createdOn: Instant)

