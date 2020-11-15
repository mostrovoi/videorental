package com.acme.videorental.purchases.infrastructure

import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.sharedKernel.infrastructure.jooq.tables.records.FilmRecord
import org.jooq.RecordMapper

class JdbcFilmMapper : RecordMapper<FilmRecord, Film> {
    override fun map(filmRecord: FilmRecord): Film {
        return Film.create(filmId = FilmId(filmRecord.filmId),
                name = filmRecord.name,
                type = FilmTypeEnum.valueOf(filmRecord.type))
    }
}