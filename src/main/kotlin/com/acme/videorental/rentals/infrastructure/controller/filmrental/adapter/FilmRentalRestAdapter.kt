package com.acme.videorental.rentals.infrastructure.controller.filmrental.adapter

import com.acme.videorental.rentals.infrastructure.controller.filmrental.dto.FilmRentReqDTO
import com.acme.videorental.rentals.infrastructure.controller.filmrental.dto.TotalRentalPriceDTO
import com.acme.videorental.rentals.application.RentFilmsUseCase
import com.acme.videorental.rentals.application.command.OneFilmRentalCommand
import com.acme.videorental.rentals.application.command.RentFilmsCommand
import com.acme.videorental.sharedKernel.domain.CustomerId

class FilmRentalRestAdapter(private val rentFilmsUseCase: RentFilmsUseCase) {


    fun rentFilms(customerId: CustomerId, filmRentedRequestDTO: Collection<FilmRentReqDTO>): TotalRentalPriceDTO {
        val filmRentalCommands = filmRentedRequestDTO.map { filmRentReqDTO ->
            OneFilmRentalCommand(
                    filmId = filmRentReqDTO.filmId,
                    numDays = filmRentReqDTO.numDays
            )
        }

        val filmRentalCommand = RentFilmsCommand(customerId = customerId,
                rentFilmCommands = filmRentalCommands)
        val totalRentalPrice = rentFilmsUseCase.execute(filmRental = filmRentalCommand)
        return TotalRentalPriceDTO(amount = totalRentalPrice)
    }

}