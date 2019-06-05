package com.casumo.videorental.infrastructure.controller.film

import com.casumo.videorental.infrastructure.controller.film.adapter.FilmRestAdapter
import com.casumo.videorental.infrastructure.controller.film.dto.FilmRequestDTO
import com.casumo.videorental.infrastructure.controller.film.dto.FilmResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class FilmController(@Autowired val filmRestAdapter: FilmRestAdapter) {

    @RequestMapping(path = ["/v1/films"], method = [(RequestMethod.GET)])
    fun getAll(): ResponseEntity<Collection<FilmResponseDTO>> {
        return ResponseEntity.ok(filmRestAdapter.getAll())
    }

    @RequestMapping(path = ["/v1/films"], method = [(RequestMethod.POST)])
    fun addFilms(@RequestBody filmsRequestDTO: Collection<FilmRequestDTO>): ResponseEntity<String> {
        filmRestAdapter.addFilms(filmsRequestDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}
