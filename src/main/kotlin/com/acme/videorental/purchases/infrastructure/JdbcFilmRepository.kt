package com.acme.videorental.purchases.infrastructure

import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.purchases.domain.FilmNotFoundException
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.sharedKernel.infrastructure.jooq.Tables
import mu.KotlinLogging
import org.jooq.DSLContext
import java.sql.Timestamp
import java.util.*

class JdbcFilmRepository(private val dslContext: DSLContext, private val jdbcFilmMapper: JdbcFilmMapper) : FilmRepository {

    private val logger = KotlinLogging.logger {}

    override fun getAll(): Collection<Film> {
        return dslContext.selectFrom(Tables.FILM).fetch(jdbcFilmMapper)
    }

    override fun findByOrFail(filmId: FilmId): Film {
        return dslContext.selectFrom(Tables.FILM).where(Tables.FILM.FILM_ID.eq(filmId.anId)).fetchOptional(jdbcFilmMapper).toNullable()
                ?: throw FilmNotFoundException("${filmId.anId}")
    }

    override fun add(aFilm: Film): Boolean {
        dslContext.insertInto(Tables.FILM)
                .columns(Tables.FILM.FILM_ID, Tables.FILM.NAME,
                        Tables.FILM.TYPE, Tables.FILM.CREATED_ON,
                        Tables.FILM.UPDATED_ON)
                .values(aFilm.filmId.anId, aFilm.name,
                        aFilm.type.name, Timestamp.from(aFilm.createdOn),
                        Timestamp.from(aFilm.updatedOn)).execute()
        logger.info { "Added a new film with ID: ${aFilm.filmId.anId}" }
        return true

    }

    override fun nextIdentity(): FilmId {
        return FilmId(UUID.randomUUID().toString())
    }

    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)


}
