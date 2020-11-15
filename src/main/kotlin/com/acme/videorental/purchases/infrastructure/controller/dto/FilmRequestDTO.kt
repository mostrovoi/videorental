package com.acme.videorental.purchases.infrastructure.controller.dto

import com.acme.videorental.sharedKernel.domain.FilmTypeEnum

data class FilmRequestDTO(val name: String, val type: FilmTypeEnum)
