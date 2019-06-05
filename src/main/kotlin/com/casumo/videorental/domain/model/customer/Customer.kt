package com.casumo.videorental.domain.model.customer

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import java.time.Instant

data class Customer(val customerId: CustomerId, var bonusCard: BonusCard, val name: String, val createdOn: Instant, val updatedOn: Instant) {

    companion object {
        fun aNewCustomer(customerId: CustomerId, name: String): Customer {
            require(name.length > 2)
            return Customer(customerId = customerId,
                    bonusCard = BonusCard.aNewCard(),
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