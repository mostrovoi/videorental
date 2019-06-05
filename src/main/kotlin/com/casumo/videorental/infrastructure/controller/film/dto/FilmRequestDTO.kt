package com.casumo.videorental.infrastructure.controller.film.dto

import com.casumo.videorental.domain.model.common.FilmTypeEnum

data class FilmRequestDTO(val name: String, val type: FilmTypeEnum)
