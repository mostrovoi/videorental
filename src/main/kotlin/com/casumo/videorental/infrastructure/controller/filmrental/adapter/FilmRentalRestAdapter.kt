package com.casumo.videorental.infrastructure.controller.filmrental.adapter

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.infrastructure.controller.filmrental.dto.FilmRentReqDTO
import com.casumo.videorental.infrastructure.controller.filmrental.dto.TotalRentalPriceDTO
import com.casumo.videorental.usecase.filmtransaction.RentFilmsUseCase
import com.casumo.videorental.usecase.filmtransaction.command.OneFilmRentalCommand
import com.casumo.videorental.usecase.filmtransaction.command.RentFilmsCommand

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