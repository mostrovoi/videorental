package com.casumo.videorental.usecase.film

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.infrastructure.repository.film.DummyFilmRepository
import com.casumo.videorental.usecase.film.command.AddFilmCommand
import org.assertj.core.api.Assertions
import org.junit.Test

class DefaultAddFilmsUseCaseShould {
    private lateinit var filmRepository: FilmRepository

    @Test
    fun add_films_successfully() {
        filmRepository = DummyFilmRepository(mutableListOf())
        val addFilmsUseCase = DefaultAddFilmsUseCase(filmRepository = filmRepository)
        val addFilmsCommand = mutableListOf(AddFilmCommand(name = "Terminator", type = FilmTypeEnum.NEW), AddFilmCommand(name = "Casablanca", type = FilmTypeEnum.OLD))

        addFilmsUseCase.execute(addFilmsCommand)

        Assertions.assertThat(filmRepository.getAll().size).isEqualTo(2)
    }
}