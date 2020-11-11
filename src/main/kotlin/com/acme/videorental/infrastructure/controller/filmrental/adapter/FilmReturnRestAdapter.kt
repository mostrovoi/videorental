package com.acme.videorental.infrastructure.controller.filmrental.adapter

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.infrastructure.controller.filmreturn.FilmSurchargePaymentResponseDTO
import com.acme.videorental.infrastructure.controller.filmreturn.dto.FilmReturnReqDTO
import com.acme.videorental.usecase.filmtransaction.ReturnFilmsUseCase
import com.acme.videorental.usecase.filmtransaction.command.OneFilmReturnCommand
import com.acme.videorental.usecase.filmtransaction.command.ReturnFilmsCommand

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