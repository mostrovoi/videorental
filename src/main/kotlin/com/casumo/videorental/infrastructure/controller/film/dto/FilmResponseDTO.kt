package com.casumo.videorental.infrastructure.controller.film.dto

import com.casumo.videorental.domain.model.common.FilmTypeEnum
import com.casumo.videorental.domain.model.film.FilmId

data class FilmResponseDTO(val id: FilmId, val name: String, val type: FilmTypeEnum)
