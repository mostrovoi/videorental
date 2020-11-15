package com.acme.videorental.rentals.infrastructure.controller.filmrental

import com.acme.videorental.rentals.infrastructure.controller.filmrental.adapter.FilmRentalRestAdapter
import com.acme.videorental.rentals.infrastructure.controller.filmrental.dto.FilmRentReqDTO
import com.acme.videorental.rentals.infrastructure.controller.filmrental.dto.TotalRentalPriceDTO
import com.acme.videorental.sharedKernel.domain.CustomerId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class FilmRentalController(val filmRentalRestAdapter: FilmRentalRestAdapter) {

    @RequestMapping(path = ["/v1/customers/{customerId}/filmrentals"], method = [(RequestMethod.POST)])
    fun rentFilms(@PathVariable customerId: CustomerId, @RequestBody filmRentalRequests: Collection<FilmRentReqDTO>): ResponseEntity<TotalRentalPriceDTO> {

        val totalRental = filmRentalRestAdapter.rentFilms(customerId = customerId, filmRentedRequestDTO = filmRentalRequests)

        return ResponseEntity.ok(totalRental)
    }
}
