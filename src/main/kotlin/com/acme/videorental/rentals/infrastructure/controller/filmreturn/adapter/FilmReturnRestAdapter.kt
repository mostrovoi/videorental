package com.acme.videorental.rentals.infrastructure.controller.filmreturn.adapter

import com.acme.videorental.rentals.infrastructure.controller.filmreturn.dto.FilmSurchargePaymentResponseDTO
import com.acme.videorental.rentals.infrastructure.controller.filmreturn.dto.FilmReturnReqDTO
import com.acme.videorental.rentals.application.ReturnFilmsUseCase
import com.acme.videorental.rentals.application.command.OneFilmReturnCommand
import com.acme.videorental.rentals.application.command.ReturnFilmsCommand
import com.acme.videorental.sharedKernel.domain.CustomerId

class FilmReturnRestAdapter(private val returnFilmsUseCase: ReturnFilmsUseCase) {

    fun returnFilms(customerId: CustomerId, filmReturnsRequestDTO: Collection<FilmReturnReqDTO>): FilmSurchargePaymentResponseDTO {
        val filmReturnCommands = filmReturnsRequestDTO.map { filmReturnReqDTO ->
            OneFilmReturnCommand(
                    filmId = filmReturnReqDTO.filmId
            )
        }

        val filmReturnCommand = ReturnFilmsCommand(customerId = customerId,
                returnFilmCommands = filmReturnCommands)
        val totalSurcharge = returnFilmsUseCase.execute(filmReturn = filmReturnCommand)
        return FilmSurchargePaymentResponseDTO(amount = totalSurcharge)
    }

}