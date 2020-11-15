package com.acme.videorental.purchases.domain

class FilmNotFoundException(filmId: String) : Exception("Film with filmId $filmId not found")
