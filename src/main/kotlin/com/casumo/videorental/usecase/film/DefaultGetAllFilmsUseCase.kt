package com.casumo.videorental.usecase.film

import com.casumo.videorental.domain.model.film.Film
import com.casumo.videorental.domain.model.film.FilmRepository

class DefaultGetAllFilmsUseCase(private val filmRepository: FilmRepository) : GetAllFilmsUseCase {
    override fun execute(): Collection<Film> {
        return filmRepository.getAll()
    }

}
