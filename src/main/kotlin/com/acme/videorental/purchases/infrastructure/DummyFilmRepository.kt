package com.acme.videorental.purchases.infrastructure

import com.acme.videorental.purchases.domain.Film
import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.purchases.domain.FilmNotFoundException
import com.acme.videorental.purchases.domain.FilmRepository
import java.util.*

class DummyFilmRepository(private val films: MutableCollection<Film>) : FilmRepository {
    override fun findByOrFail(filmId: FilmId): Film {
        val film = films.find { film -> film.filmId == filmId }
        return film ?: throw FilmNotFoundException("Film with Id $filmId not found")
    }

    override fun nextIdentity(): FilmId {
        return FilmId(UUID.randomUUID().toString())
    }

    @Synchronized
    override fun add(aFilm: Film): Boolean {
        films.removeIf { film -> aFilm.filmId == film.filmId }
        return films.add(aFilm)
    }

    override fun getAll(): Collection<Film> {
        return films
    }

}