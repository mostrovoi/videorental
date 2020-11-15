package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.payments.domain.Payment
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.payments.domain.PaymentTypeEnum
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import com.acme.videorental.rentals.application.command.OneFilmReturnCommand
import com.acme.videorental.rentals.application.command.ReturnFilmsCommand
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.sharedKernel.domain.CustomerId
import java.time.Instant

class ReturnFilmsUseCase(private val selectedFilmView: SelectedFilmView,
                         private val customerRepository: CustomerRepository,
                         private val filmReturnedAssembler: FilmReturnAssembler,
                         private val filmRepository: FilmRepository,
                         private val rentalRepository: RentalRepository,
                         private val paymentRepository: PaymentRepository) {
    fun execute(filmReturn: ReturnFilmsCommand): Long {

        val customer = customerRepository.findByOrFail(filmReturn.customerId)
        val filmsReturned = filmReturn.returnFilmCommands

        require(filmsReturned.isNotEmpty())

        return filmsReturned.map { filmReturned -> returnOneFilm(customer, filmReturned) }.sum()
    }

    private fun returnOneFilm(customer: Customer, filmReturnedCommand: OneFilmReturnCommand): Long {

        require(selectedFilmView.isAlreadyRentedByCustomer(customerId = customer.customerId, filmId = filmReturnedCommand.filmId))

        val filmTransactionReturned = filmReturnedAssembler.toFilmReturnEvent(customerId = customer.customerId, filmReturnCommand = filmReturnedCommand)

        val filmReturned = filmRepository.findByOrFail(filmId = filmReturnedCommand.filmId)
        val rentedBy = selectedFilmView.getRentalDetailsFromFilm(filmReturnedCommand.filmId)

        selectedFilmView.returnFilm(filmTransactionReturned)
        rentalRepository.save(filmTransactionReturned)

        return createSurchargeIfOverdue(
                customerId = filmTransactionReturned.customerId,
                filmType = filmReturned.type,
                rentalTime = rentedBy.rentedAt,
                paidRentedDays = rentedBy.numDays,
                returnTime = filmTransactionReturned.createdOn)
    }


    private fun createSurchargeIfOverdue(customerId: CustomerId, filmType: FilmTypeEnum, paidRentedDays: Int, returnTime: Instant, rentalTime: Instant): Long {

        val rentedDaysBasedOnType = Payment.getPaidDaysBasedOnFilmType(filmType)
        val priceBasedOnType = Payment.getPriceBasedOnFilmType(filmType)

        val totalPaidRentedDays = Math.max(rentedDaysBasedOnType, paidRentedDays)

        val overduePriceToPay = Payment.calculatePriceForOverdueReturn(pricePerDay = priceBasedOnType, instantRented = rentalTime, instantReturned = returnTime, paidRentedDays = totalPaidRentedDays)
        if (overduePriceToPay > Payment.NO_SURCHARGE) {
            val overduePaymentTransaction = Payment(paymentId = paymentRepository.nextIdentity(), customerId = customerId, amount = overduePriceToPay, paymentType = PaymentTypeEnum.OVERCHARGE, createdOn = Instant.now())
            paymentRepository.save(overduePaymentTransaction)
        }
        return overduePriceToPay
    }
}
