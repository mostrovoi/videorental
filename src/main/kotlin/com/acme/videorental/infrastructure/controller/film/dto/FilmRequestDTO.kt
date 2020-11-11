package com.acme.videorental.infrastructure.controller.film.dto

import com.acme.videorental.domain.model.common.FilmTypeEnum

data class FilmRequestDTO(val name: String, val type: FilmTypeEnum)
