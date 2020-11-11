package com.acme.videorental.usecase.filmtransaction

import com.acme.videorental.domain.model.customer.CustomerId
import java.time.Instant

data class RentedBy(val customerId: CustomerId,
                    val rentedAt: Instant, val numDays: Int)