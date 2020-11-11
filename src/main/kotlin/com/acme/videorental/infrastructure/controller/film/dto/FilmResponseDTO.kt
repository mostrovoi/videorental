package com.acme.videorental.infrastructure.controller.film.dto

import com.acme.videorental.domain.model.common.FilmTypeEnum
import com.acme.videorental.domain.model.film.FilmId

data class FilmResponseDTO(val id: FilmId, val name: String, val type: FilmTypeEnum)
