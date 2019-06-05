package com.casumo.videorental.usecase.film

import com.casumo.videorental.usecase.film.command.AddFilmCommand

interface AddFilmsUseCase {
    fun execute(filmCommands: Collection<AddFilmCommand>)
}
