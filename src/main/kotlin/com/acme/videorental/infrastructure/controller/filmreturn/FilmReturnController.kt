package com.acme.videorental.infrastructure.controller.filmrental

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.infrastructure.controller.filmrental.adapter.FilmReturnRestAdapter
import com.acme.videorental.infrastructure.controller.filmreturn.FilmSurchargePaymentResponseDTO
import com.acme.videorental.infrastructure.controller.filmreturn.dto.FilmReturnReqDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class FilmReturnController(@Autowired val filmReturnRestAdapter: FilmReturnRestAdapter) {

    @RequestMapping(path = ["/v1/customers/{customerId}/filmreturns"], method = [(RequestMethod.POST)])
    fun returnFilms(@PathVariable customerId: CustomerId,
                    @RequestBody filmsReturnRequests: Collection<FilmReturnReqDTO>): ResponseEntity<FilmSurchargePaymentResponseDTO> {
        val totalSurcharge = filmReturnRestAdapter.returnFilms(customerId = customerId, filmReturnsRequestDTO = filmsReturnRequests)
        return ResponseEntity.ok(totalSurcharge)
    }
}
