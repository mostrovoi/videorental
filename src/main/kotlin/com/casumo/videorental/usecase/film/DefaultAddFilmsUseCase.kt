package com.casumo.videorental.usecase.film

import com.casumo.videorental.domain.model.film.Film
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.usecase.film.command.AddFilmCommand

class DefaultAddFilmsUseCase(private val filmRepository: FilmRepository) : AddFilmsUseCase {
    override fun execute(filmCommands: Collection<AddFilmCommand>) {
        val films = filmCommands.map { filmCommand ->
            Film.create(filmId = filmRepository.nextIdentity(),
                    name = filmCommand.name, type = filmCommand.type)
        }

        films.forEach { film -> addOneFilm(film = film) }
    }

    private fun addOneFilm(film: Film): Boolean {
        return filmRepository.add(aFilm = film)
    }

}
