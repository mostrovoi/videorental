package com.acme.videorental.customers.domain

import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import java.time.Instant

data class Customer private constructor(val customerId: CustomerId, var bonusCard: BonusCard, val name: String, val createdOn: Instant, val updatedOn: Instant) {

    companion object {
        fun aNewCustomer(customerId: CustomerId, name: String): Customer {
            require(name.length > 2)
            return Customer(customerId = customerId,
                    bonusCard = BonusCard(),
                    name = name,
                    createdOn = Instant.now(),
                    updatedOn = Instant.now())
        }

        fun addBonusPoints(customer: Customer, type: FilmTypeEnum): Customer {
            return Customer(customerId = customer.customerId,
                    bonusCard = BonusCard.updateBonusPoints(customer.bonusCard, filmType = type),
                    name = customer.name,
                    createdOn = customer.createdOn,
                    updatedOn = Instant.now())
        }
    }
}