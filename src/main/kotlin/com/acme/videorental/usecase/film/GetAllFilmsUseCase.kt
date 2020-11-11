package com.acme.videorental.usecase.film

import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.domain.model.film.FilmRepository

class GetAllFilmsUseCase(private val filmRepository: FilmRepository) {
    fun execute(): Collection<Film> {
        return filmRepository.getAll()
    }

}
