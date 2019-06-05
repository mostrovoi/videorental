package com.casumo.videorental.usecase.film

import com.casumo.videorental.domain.model.film.Film

interface GetAllFilmsUseCase {
    fun execute(): Collection<Film>
}
