package com.casumo.videorental.infrastructure.controller.customer

import com.casumo.videorental.infrastructure.controller.customer.adapter.CustomerRestAdapter
import com.casumo.videorental.infrastructure.controller.customer.dto.CustomerRequestDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class CustomerController(@Autowired val customerRestAdapter: CustomerRestAdapter) {

    @RequestMapping(path = ["/v1/customers"], method = [(RequestMethod.GET)])
    fun getAll(): ResponseEntity<Collection<CustomerResponseDTO>> {
        return ResponseEntity.ok(customerRestAdapter.getAll())
    }

    @RequestMapping(path = ["/v1/customers"], method = [(RequestMethod.POST)])
    fun addFilms(@RequestBody customersRequestDTO: Collection<CustomerRequestDTO>): ResponseEntity<String> {
        customerRestAdapter.addCustomers(customersRequestDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}

