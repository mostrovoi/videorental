package com.acme.videorental.purchases.domain

class FilmAlreadyExistingException(filmSlug: String) : Exception("Film with slug $filmSlug already exists")

