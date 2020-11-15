package com.acme.videorental.rentals.application

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.payments.domain.Payment
import com.acme.videorental.payments.domain.PaymentRepository
import com.acme.videorental.payments.domain.PaymentTypeEnum
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.rentals.application.command.OneFilmRentalCommand
import com.acme.videorental.rentals.application.command.RentFilmsCommand
import com.acme.videorental.rentals.domain.RentalRepository

import java.time.Instant

class RentFilmsUseCase(private val selectedFilmView: SelectedFilmView,
                       private val customerRepository: CustomerRepository,
                       private val filmRentalAssembler: FilmRentalAssembler,
                       private val filmRepository: FilmRepository,
                       private val paymentRepository: PaymentRepository,
                       private val rentalRepository: RentalRepository)  {

    fun execute(filmRental: RentFilmsCommand): Long {
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
        val totalToPay = Payment.calculatePrice(filmToRent.type, filmRentedCommand.numDays)

        val newPaymentTransaction = Payment(paymentId = paymentRepository.nextIdentity(),
                customerId = updatedCustomer.customerId,
                amount = totalToPay,
                paymentType = PaymentTypeEnum.REGULAR,
                createdOn = Instant.now())
        selectedFilmView.rentFilm(filmRented = filmRented)
        customerRepository.update(updatedCustomer)
        rentalRepository.save(filmTransaction = filmRented)
        paymentRepository.save(paymentTransaction = newPaymentTransaction)
        return totalToPay
    }

}
