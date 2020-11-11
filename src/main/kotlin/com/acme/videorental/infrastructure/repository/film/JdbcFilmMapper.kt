package com.acme.videorental.infrastructure.repository.film

import com.acme.videorental.domain.model.common.FilmTypeEnum
import com.acme.videorental.domain.model.film.Film
import com.acme.videorental.domain.model.film.FilmId
import com.acme.videorental.infrastructure.repository.jooq.tables.records.FilmRecord
import org.jooq.RecordMapper

class JdbcFilmMapper : RecordMapper<FilmRecord, Film> {
    override fun map(filmRecord: FilmRecord): Film {
        return Film(filmId = FilmId(filmRecord.filmId),
                name = filmRecord.name,
                type = FilmTypeEnum.valueOf(filmRecord.type),
                createdOn = filmRecord.createdOn.toInstant(),
                updatedOn = filmRecord.updatedOn.toInstant())

    }

}