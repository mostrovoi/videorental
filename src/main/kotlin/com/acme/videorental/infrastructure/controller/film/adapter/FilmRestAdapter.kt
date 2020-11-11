package com.acme.videorental.infrastructure.controller.film.adapter

import com.acme.videorental.infrastructure.controller.film.dto.FilmRequestDTO
import com.acme.videorental.infrastructure.controller.film.dto.FilmResponseDTO
import com.acme.videorental.usecase.film.AddFilmsUseCase
import com.acme.videorental.usecase.film.GetAllFilmsUseCase
import com.acme.videorental.usecase.film.command.AddFilmCommand

class FilmRestAdapter(val getAllFilmsUseCase: GetAllFilmsUseCase,
                      val addFilmsUseCase: AddFilmsUseCase,
                      val filmMapper: FilmMapper) {

    fun getAll(): Collection<FilmResponseDTO> {
        return getAllFilmsUseCase.execute().map(filmMapper::toDTO)
    }

    fun addFilms(filmsRequestDTO: Collection<FilmRequestDTO>) {

        val addFilmsCommands = filmsRequestDTO.map { filmRequestDTO ->
            AddFilmCommand(name = filmRequestDTO.name, type = filmRequestDTO.type)
        }

        return addFilmsUseCase.execute(addFilmsCommands)
    }
}