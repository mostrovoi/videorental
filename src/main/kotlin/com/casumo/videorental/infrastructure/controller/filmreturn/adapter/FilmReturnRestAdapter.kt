package com.casumo.videorental.infrastructure.controller.filmrental.adapter

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.infrastructure.controller.filmreturn.FilmSurchargePaymentResponseDTO
import com.casumo.videorental.infrastructure.controller.filmreturn.dto.FilmReturnReqDTO
import com.casumo.videorental.usecase.filmtransaction.ReturnFilmsUseCase
import com.casumo.videorental.usecase.filmtransaction.command.OneFilmReturnCommand
import com.casumo.videorental.usecase.filmtransaction.command.ReturnFilmsCommand

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