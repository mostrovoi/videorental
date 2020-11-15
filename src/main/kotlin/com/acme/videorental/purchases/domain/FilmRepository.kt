package com.acme.videorental.purchases.domain

import com.acme.videorental.sharedKernel.domain.FilmId

interface FilmRepository {

    fun getAll(): Collection<Film>
    fun findByOrFail(filmId: FilmId): Film
    fun add(aFilm: Film): Boolean
    fun nextIdentity(): FilmId
}