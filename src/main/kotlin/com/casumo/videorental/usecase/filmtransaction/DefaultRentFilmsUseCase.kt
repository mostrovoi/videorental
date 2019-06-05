package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.domain.model.filmpayment.PaymentTransaction
import com.casumo.videorental.domain.model.filmpayment.PaymentTransactionRepository
import com.casumo.videorental.domain.model.filmpayment.PaymentTypeEnum
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.usecase.filmtransaction.command.OneFilmRentalCommand
import com.casumo.videorental.usecase.filmtransaction.command.RentFilmsCommand
import java.time.Instant

class DefaultRentFilmsUseCase(private val selectedFilmView: SelectedFilmView,
                              private val customerRepository: CustomerRepository,
                              private val filmRentalAssembler: FilmRentalAssembler,
                              private val filmRepository: FilmRepository,
                              private val paymentTransactionRepository: PaymentTransactionRepository,
                              private val filmTransactionRepository: FilmTransactionRepository) : RentFilmsUseCase {

    override fun execute(filmRental: RentFilmsCommand): Long {
        val customer = customerRepository.findByOrFail(filmRental.customerId)
        val filmsRented = filmRental.rentFilmCommands

        require(filmsRented.isNotEmpty())

        return filmsRented.map { filmRented -> rentOneFilm(customer, filmRented) }.sum()
    }

    private fun rentOneFilm(customer: Customer, filmRentedCommand: OneFilmRentalCommand): Long {

        require(filmRentedCommand.numDays > 0) { "Not allowed to rent a film for 0 or negative days" }
        check(selectedFilmView.isAvailable(filmRentedCommand.filmId)) { "Film with ${filmRentedCommand.filmId} not available for renting" }

        val filmRented = filmRentalAssembler.toFilmRentedEvent(
                customerId = customer.customerId,
                filmRentalCommand = filmRentedCommand
        )
        val filmToRent = filmRepository.findByOrFail(filmId = filmRentedCommand.filmId)
        val updatedCustomer = Customer.addBonusPoints(customer = customer, type = filmToRent.type)
        val totalToPay = PaymentTransaction.calculatePrice(filmToRent.type, filmRentedCommand.numDays)

        val newPaymentTransaction = PaymentTransaction(paymentTransactionId = paymentTransactionRepository.nextIdentity(),
                customerId = updatedCustomer.customerId,
                amount = totalToPay,
                paymentType = PaymentTypeEnum.REGULAR,
                createdOn = Instant.now())
        selectedFilmView.rentFilm(filmRented = filmRented)
        customerRepository.update(updatedCustomer)
        filmTransactionRepository.save(filmTransaction = filmRented)
        paymentTransactionRepository.save(paymentTransaction = newPaymentTransaction)
        return totalToPay
    }

}
