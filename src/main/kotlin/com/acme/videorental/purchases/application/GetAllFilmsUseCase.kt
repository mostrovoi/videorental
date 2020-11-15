package com.acme.videorental.purchases.application

import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.purchases.domain.FilmRepository

class GetAllFilmsUseCase(private val filmRepository: FilmRepository) {
    fun execute(): Collection<Film> {
        return filmRepository.getAll()
    }

}
