package com.acme.videorental.purchases.infrastructure.controller.adapter

import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.purchases.infrastructure.controller.dto.FilmResponseDTO

class FilmMapper {

    fun toDTO(film: Film): FilmResponseDTO {
        return FilmResponseDTO(id = film.filmId,
                name = film.name,
                type = film.type)
    }
}
