package com.acme.videorental.domain.model.film

import com.acme.videorental.domain.model.common.FilmTypeEnum
import java.time.Instant

data class Film private constructor(val filmId: FilmId, val name: String, val type: FilmTypeEnum, val createdOn: Instant, val updatedOn: Instant) {
    companion object {
        fun create(filmId: FilmId, name: String, type: FilmTypeEnum): Film {
            return Film(filmId = filmId,
                    name = name,
                    type = type,
                    createdOn = Instant.now(),
                    updatedOn = Instant.now())
        }
    }

}