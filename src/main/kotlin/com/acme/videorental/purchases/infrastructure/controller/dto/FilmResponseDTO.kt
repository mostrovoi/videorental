package com.acme.videorental.purchases.infrastructure.controller.dto

import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import com.acme.videorental.sharedKernel.domain.FilmId

data class FilmResponseDTO(val id: FilmId, val name: String, val type: FilmTypeEnum)
