package com.casumo.videorental.usecase.film.command

import com.casumo.videorental.domain.model.common.FilmTypeEnum

class AddFilmCommand(val name: String, val type: FilmTypeEnum)