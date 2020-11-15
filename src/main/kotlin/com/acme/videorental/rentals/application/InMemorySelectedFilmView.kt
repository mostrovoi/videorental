package com.acme.videorental.rentals.application

import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import com.acme.videorental.rentals.domain.FilmRented
import com.acme.videorental.rentals.domain.FilmReturned
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmId
import mu.KotlinLogging
import java.util.concurrent.ConcurrentHashMap

class InMemorySelectedFilmView(private val selections: ConcurrentHashMap<FilmId, RentedBy>) : SelectedFilmView {

    private val logger = KotlinLogging.logger {}

    override fun getRentalDetailsFromFilm(filmId: FilmId): RentedBy {
        return selections[filmId] ?: throw RentalNotFoundException(filmId = filmId)
    }

    override fun rentFilm(filmRented: FilmRented): Map<FilmId, RentedBy> {

        require(filmRented.action === FilmActionEnum.RENT)
        val rentedBy = RentedBy(customerId = filmRented.customerId,
                rentedAt = filmRented.createdOn, numDays = filmRented.numDays)
        val filmFromEventRented = selections.map { filmTransaction -> filmTransaction.key }
                .filter { filmSlug -> filmRented.filmId == filmSlug }

        if (filmFromEventRented.isNotEmpty())
            throw FilmAlreadyRentedException(filmId = filmRented.filmId)

        selections.put(key = filmRented.filmId, value = rentedBy)
        return selections
    }

    override fun returnFilm(filmReturned: FilmReturned): Map<FilmId, RentedBy> {
        require(filmReturned.action === FilmActionEnum.RETURN)
        val rentedBy = RentedBy(customerId = filmReturned.customerId,
                rentedAt = filmReturned.createdOn, numDays = filmReturned.numDays)

        val filmRentedByUser = selections
                .filter { selectedEntry ->
                    selectedEntry.key.equals(filmReturned.filmId)
                            && selectedEntry.value.customerId.equals(filmReturned.customerId)
                }

        logger.info { "Films rented: ${rentedBy}" }
        if (filmRentedByUser.isEmpty())
            throw RentalForCustomerNotFoundException(filmId = filmReturned.filmId, customerId = filmReturned.customerId)
        selections.remove(key = filmReturned.filmId)
        return selections
    }

    override fun isAvailable(filmId: FilmId): Boolean {
        return !selections.contains(key = filmId)
    }

    override fun isEmpty(): Boolean {
        return selections.isEmpty()
    }

    override fun isAlreadyRentedByCustomer(customerId: CustomerId, filmId: FilmId): Boolean {
        if (selections.get(filmId) == null) {
            return false
        }
        return selections.get(filmId)!!.customerId == customerId
    }

}