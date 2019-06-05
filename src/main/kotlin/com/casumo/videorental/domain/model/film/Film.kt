package com.casumo.videorental.domain.model.film

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import java.time.Instant

data class Film(val filmId: FilmId, val name: String, val type: FilmTypeEnum, val createdOn: Instant, val updatedOn: Instant) {
    companion object {
        fun create(filmId: FilmId, name: String, type: FilmTypeEnum): Film {
            //TODO: To send event
            return Film(filmId = filmId,
                    name = name,
                    type = type,
                    createdOn = Instant.now(),
                    updatedOn = Instant.now())
        }
    }

}