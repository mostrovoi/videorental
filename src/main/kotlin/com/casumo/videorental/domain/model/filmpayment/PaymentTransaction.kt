package com.casumo.videorental.domain.model.filmpayment

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import com.casumo.videorental.domain.model.customer.CustomerId
import java.time.Duration
import java.time.Instant

data class PaymentTransaction(val paymentTransactionId: PaymentTransactionId, val customerId: CustomerId, val amount: Long, val paymentType: PaymentTypeEnum, val createdOn: Instant) {

    companion object {

        private const val PREMIUM_PRICE = 40L
        private const val BASIC_PRICE = 30L

        private const val REGULAR_FILMS_MAX_DAYS = 3
        private const val OLD_FILMS_MAX_DAYS = 5
        private const val NEW_FILMS_MAX_DAYS = 1

        const val NO_SURCHARGE = 0L


        fun calculatePriceForOverdueReturn(pricePerDay: Long, instantRented: Instant, instantReturned: Instant, paidRentedDays: Int): Long {

            check(instantReturned.isAfter(instantRented))
            require(paidRentedDays > 0)
            require(pricePerDay > 0)

            val daysInLoan = (Duration.between(instantRented, instantReturned).toDays()) + 1
            return if (paidRentedDays < daysInLoan) {
                pricePerDay * (daysInLoan - paidRentedDays)
            } else
                NO_SURCHARGE
        }

        fun getPaidDaysBasedOnFilmType(filmType: FilmTypeEnum): Int {
            return when (filmType) {
                FilmTypeEnum.NEW -> NEW_FILMS_MAX_DAYS
                FilmTypeEnum.REGULAR -> REGULAR_FILMS_MAX_DAYS
                FilmTypeEnum.OLD -> OLD_FILMS_MAX_DAYS
            }
        }

        fun calculatePrice(filmType: FilmTypeEnum, numDays: Int): Long {
            require(numDays > 0)

            return when (filmType) {
                FilmTypeEnum.NEW -> PREMIUM_PRICE * numDays
                FilmTypeEnum.REGULAR -> calculatePriceBasedOnDays(pricePerDay = BASIC_PRICE, numDays = numDays, maxDays = REGULAR_FILMS_MAX_DAYS)
                FilmTypeEnum.OLD -> calculatePriceBasedOnDays(pricePerDay = BASIC_PRICE, numDays = numDays, maxDays = OLD_FILMS_MAX_DAYS)
            }
        }

        fun getPriceBasedOnFilmType(filmType: FilmTypeEnum): Long {
            return when (filmType) {
                FilmTypeEnum.NEW -> PREMIUM_PRICE
                FilmTypeEnum.REGULAR -> BASIC_PRICE
                FilmTypeEnum.OLD -> BASIC_PRICE
            }
        }


        private fun calculatePriceBasedOnDays(pricePerDay: Long, numDays: Int, maxDays: Int): Long {
            return if (numDays <= maxDays) {
                pricePerDay
            } else {
                pricePerDay + (pricePerDay * (numDays - maxDays))
            }
        }

    }
}