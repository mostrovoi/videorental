package com.casumo.videorental.usecase.filmtransaction

import com.casumo.videorental.usecase.filmtransaction.command.ReturnFilmsCommand

interface ReturnFilmsUseCase {
    fun execute(filmReturn: ReturnFilmsCommand): Long
}
