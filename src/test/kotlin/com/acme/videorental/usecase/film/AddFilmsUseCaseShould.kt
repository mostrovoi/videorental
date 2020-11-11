package com.acme.videorental.usecase.film

import com.acme.videorental.domain.model.common.FilmTypeEnum
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.infrastructure.repository.film.DummyFilmRepository
import com.acme.videorental.usecase.film.command.AddFilmCommand
import org.assertj.core.api.Assertions
import org.junit.Test

class AddFilmsUseCaseShould {
    private lateinit var filmRepository: FilmRepository

    @Test
    fun add_films_successfully() {
        filmRepository = DummyFilmRepository(mutableListOf())
        val addFilmsUseCase = AddFilmsUseCase(filmRepository = filmRepository)
        val addFilmsCommand = mutableListOf(AddFilmCommand(name = "Terminator", type = FilmTypeEnum.NEW), AddFilmCommand(name = "Casablanca", type = FilmTypeEnum.OLD))

        addFilmsUseCase.execute(addFilmsCommand)

        Assertions.assertThat(filmRepository.getAll().size).isEqualTo(2)
    }
}