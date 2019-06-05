package com.casumo.videorental.infrastructure.controller.film.adapter

import com.casumo.videorental.domain.model.film.Film
import com.casumo.videorental.infrastructure.controller.film.dto.FilmResponseDTO

class FilmMapper {

    fun toDTO(film: Film): FilmResponseDTO {
        return FilmResponseDTO(id = film.filmId,
                name = film.name,
                type = film.type)
    }
}
