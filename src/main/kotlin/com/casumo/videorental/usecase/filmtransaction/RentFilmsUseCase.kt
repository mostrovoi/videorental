package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.usecase.filmtransaction.command.RentFilmsCommand

interface RentFilmsUseCase {
    fun execute(filmRental: RentFilmsCommand): Long
}
