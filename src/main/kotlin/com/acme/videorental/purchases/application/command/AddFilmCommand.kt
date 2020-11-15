package com.acme.videorental.purchases.application.command

import com.acme.videorental.sharedKernel.domain.FilmTypeEnum

class AddFilmCommand(val name: String, val type: FilmTypeEnum)