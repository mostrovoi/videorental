package com.casumo.videorental.domain.model.filmpayment

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import org.assertj.core.api.Assertions
import org.junit.Test
import java.time.Duration
import java.time.Instant

class PaymentTransactionShould {

    @Test
    fun return_no_charge_when_return_is_on_time() {
        val rentalTime = Instant.now().minus(Duration.ofDays(2))
        val returnTime = Instant.now()
        val paidRentedDays = 3

        val totalToPay = PaymentTransaction.calculatePriceForOverdueReturn(10, rentalTime, returnTime, paidRentedDays)
        Assertions.assertThat(totalToPay).isEqualTo(PaymentTransaction.NO_SURCHARGE)
    }

    @Test
    fun return_total_payment_when_a_return_is_overdue_for_one_second() {
        val returnTime = Instant.now()
        val rentalTime = returnTime.minusSeconds(3600*24)
        val paidRentedDays = 1

        val totalToPay = PaymentTransaction.calculatePriceForOverdueReturn(10, rentalTime, returnTime, paidRentedDays)
        Assertions.assertThat(totalToPay).isEqualTo(10)
    }

    @Test(expected = IllegalStateException::class)
    fun throw_exception_on_calculating_surcharge_when_rental_time_is_after_return_time() {
        val returnTime = Instant.now().minus(Duration.ofDays(2))
        val rentalTime = Instant.now()
        val paidRentedDays = 1
        PaymentTransaction.calculatePriceForOverdueReturn(10, rentalTime, returnTime, paidRentedDays)
    }

    @Test(expected = IllegalArgumentException::class)
    fun throw_exception_on_calculating_surcharge_when_total_rented_days_is_minus_than_one() {
        val rentalTime = Instant.now().minus(Duration.ofDays(2))
        val returnTime = Instant.now()
        val paidRentedDays = 0
        PaymentTransaction.calculatePriceForOverdueReturn(10, rentalTime, returnTime, paidRentedDays)
    }

    @Test(expected = IllegalArgumentException::class)
    fun throw_exception_on_calculating_surcharge_when_price_is_less_or_equal_than_zero() {
        val rentalTime = Instant.now().minus(Duration.ofDays(2))
        val returnTime = Instant.now()
        val paidRentedDays = 1
        PaymentTransaction.calculatePriceForOverdueReturn(0, rentalTime, returnTime, paidRentedDays)
    }

    @Test
    fun calculate_rental_price_for_regular_movie_for_multiple_days() {
        val totalPrice = PaymentTransaction.calculatePrice(FilmTypeEnum.REGULAR, 5)
        Assertions.assertThat(totalPrice).isEqualTo(90)
    }

    @Test
    fun calculate_rental_price_for_old_movie_for_multiple_days() {
        val totalPrice = PaymentTransaction.calculatePrice(FilmTypeEnum.OLD, 7)
        Assertions.assertThat(totalPrice).isEqualTo(90)
    }
}