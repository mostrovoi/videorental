package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.domain.model.filmpayment.PaymentTransaction
import com.casumo.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.casumo.videorental.domain.model.filmpayment.PaymentTypeEnum
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.usecase.filmtransaction.command.OneFilmReturnCommand
import com.casumo.videorental.usecase.filmtransaction.command.ReturnFilmsCommand
import java.time.Instant

class DefaultReturnFilmsUseCase(private val selectedFilmView: SelectedFilmView,
                                private val customerRepository: CustomerRepository,
                                private val filmReturnedAssembler: FilmReturnAssembler,
                                private val filmRepository: FilmRepository,
                                private val filmTransactionRepository: FilmTransactionRepository,
                                private val paymentTransactionRepository: PaymentTransactionRepository) : ReturnFilmsUseCase {
    override fun execute(filmReturn: ReturnFilmsCommand): Long {

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
        filmTransactionRepository.save(filmTransactionReturned)

        return createSurchargeIfOverdue(
                customerId = filmTransactionReturned.customerId,
                filmType = filmReturned.type,
                rentalTime = rentedBy.rentedAt,
                paidRentedDays = rentedBy.numDays,
                returnTime = filmTransactionReturned.createdOn)
    }


    private fun createSurchargeIfOverdue(customerId: CustomerId, filmType: FilmTypeEnum, paidRentedDays: Int, returnTime: Instant, rentalTime: Instant): Long {

        val rentedDaysBasedOnType = PaymentTransaction.getPaidDaysBasedOnFilmType(filmType)
        val priceBasedOnType = PaymentTransaction.getPriceBasedOnFilmType(filmType)

        val totalPaidRentedDays = Math.max(rentedDaysBasedOnType, paidRentedDays)

        val overduePriceToPay = PaymentTransaction.calculatePriceForOverdueReturn(pricePerDay = priceBasedOnType, instantRented = rentalTime, instantReturned = returnTime, paidRentedDays = totalPaidRentedDays)
        if (overduePriceToPay > PaymentTransaction.NO_SURCHARGE) {
            val overduePaymentTransaction = PaymentTransaction(paymentTransactionId = paymentTransactionRepository.nextIdentity(), customerId = customerId, amount = overduePriceToPay, paymentType = PaymentTypeEnum.OVERCHARGE, createdOn = Instant.now())
            paymentTransactionRepository.save(overduePaymentTransaction)
        }
        return overduePriceToPay
    }
}
