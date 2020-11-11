package com.acme.videorental.usecase.film

import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.usecase.film.command.AddFilmCommand

class AddFilmsUseCase(private val filmRepository: FilmRepository)  {
    fun execute(filmCommands: Collection<AddFilmCommand>) {
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
