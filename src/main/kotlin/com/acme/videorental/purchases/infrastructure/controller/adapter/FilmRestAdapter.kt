package com.acme.videorental.purchases.infrastructure.controller.adapter

import com.acme.videorental.purchases.application.AddFilmsUseCase
import com.acme.videorental.purchases.application.GetAllFilmsUseCase
import com.acme.videorental.purchases.infrastructure.controller.dto.FilmRequestDTO
import com.acme.videorental.purchases.infrastructure.controller.dto.FilmResponseDTO
import com.acme.videorental.purchases.application.command.AddFilmCommand

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