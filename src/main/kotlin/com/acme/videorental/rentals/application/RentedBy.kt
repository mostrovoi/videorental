package com.acme.videorental.rentals.application

import com.acme.videorental.sharedKernel.domain.CustomerId
import java.time.Instant

data class RentedBy(val customerId: CustomerId,
                    val rentedAt: Instant, val numDays: Int)