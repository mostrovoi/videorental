package com.acme.videorental.infrastructure.controller.film.adapter

import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.infrastructure.controller.film.dto.FilmResponseDTO

class FilmMapper {

    fun toDTO(film: Film): FilmResponseDTO {
        return FilmResponseDTO(id = film.filmId,
                name = film.name,
                type = film.type)
    }
}
