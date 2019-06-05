package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import java.time.Instant

data class RentedBy(val customerId: CustomerId,
                    val rentedAt: Instant, val numDays: Int)