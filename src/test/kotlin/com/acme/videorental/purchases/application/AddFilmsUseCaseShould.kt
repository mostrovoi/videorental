package com.acme.videorental.purchases.application

import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import com.acme.videorental.purchases.infrastructure.DummyFilmRepository
import com.acme.videorental.purchases.application.command.AddFilmCommand
import com.acme.videorental.purchases.domain.FilmRepository
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