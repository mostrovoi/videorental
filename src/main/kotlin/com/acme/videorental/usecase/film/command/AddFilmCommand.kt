package com.acme.videorental.usecase.film.command

import com.acme.videorental.domain.model.common.FilmTypeEnum

class AddFilmCommand(val name: String, val type: FilmTypeEnum)