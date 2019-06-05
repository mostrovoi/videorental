package com.casumo.videorental.domain.model.film

interface FilmRepository {

    fun getAll(): Collection<Film>
    fun findByOrFail(filmId: FilmId): Film
    fun add(aFilm: Film): Boolean
    fun nextIdentity(): FilmId
}