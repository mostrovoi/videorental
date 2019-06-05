package com.casumo.videorental.domain.model.film

class FilmAlreadyExistingException(filmSlug: String) : Exception("Film with slug $filmSlug already exists")

