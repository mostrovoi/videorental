package com.acme.videorental.infrastructure.controller.filmrental.adapter

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.infrastructure.controller.filmrental.dto.FilmRentReqDTO
import com.acme.videorental.infrastructure.controller.filmrental.dto.TotalRentalPriceDTO
import com.acme.videorental.usecase.filmtransaction.RentFilmsUseCase
import com.acme.videorental.usecase.filmtransaction.command.OneFilmRentalCommand
import com.acme.videorental.usecase.filmtransaction.command.RentFilmsCommand

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