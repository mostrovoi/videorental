package com.acme.videorental.usecase.film

import com.acme.videorental.domain.model.common.FilmTypeEnum
import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.infrastructure.repository.film.DummyFilmRepository
import org.assertj.core.api.Assertions
import org.junit.Test


class GetAllFilmsUseCaseShould {

    private lateinit var filmRepository: FilmRepository

    @Test
    fun return_none_film_when_repository_is_initialized() {
        filmRepository = DummyFilmRepository(mutableListOf())
        val getAllFilmsUseCase = GetAllFilmsUseCase(filmRepository = filmRepository)

        val films = getAllFilmsUseCase.execute()

        Assertions.assertThat(films).isEmpty()
    }

    @Test
    fun return_film_when_one_is_added() {
        filmRepository = DummyFilmRepository(mutableListOf())

        val aFilm = aFilm()
        filmRepository.add(aFilm)
        val getAllFilmsUseCase = GetAllFilmsUseCase(filmRepository = filmRepository)

        val films = getAllFilmsUseCase.execute()

        Assertions.assertThat(films.size).isEqualTo(1)
        Assertions.assertThat(films).contains(aFilm)
    }

    private fun aFilm(): Film {
        return Film.create(filmId = filmRepository.nextIdentity(), name = "Casablanca", type = FilmTypeEnum.OLD)
    }
}