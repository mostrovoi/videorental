package com.casumo.videorental.infrastructure.controller.filmrental.dto

import com.casumo.videorental.domain.model.film.FilmId

data class FilmRentReqDTO(val filmId: FilmId, val numDays: Int)
