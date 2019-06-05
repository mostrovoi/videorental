package com.casumo.videorental.domain.model.film

class FilmNotFoundException(filmId: String) : Exception("Film with filmId $filmId not found")
