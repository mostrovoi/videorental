package com.acme.videorental.customers.infrastructure.controller.adapter

import com.acme.videorental.customers.infrastructure.controller.CustomerResponseDTO
import com.acme.videorental.customers.infrastructure.controller.dto.CustomerRequestDTO
import com.acme.videorental.customers.application.AddCustomersUseCase
import com.acme.videorental.customers.application.GetAllCustomersUseCase
import com.acme.videorental.customers.application.command.AddCustomerCommand

class CustomerRestAdapter(private val getAllCustomersUseCase: GetAllCustomersUseCase, private val addCustomersUseCase: AddCustomersUseCase, private val customerMapper: CustomerMapper) {

    fun addCustomers(customersRequestsDTO: Collection<CustomerRequestDTO>) {

        val addCustomerCommands = customersRequestsDTO.map { customerRequestDTO ->
            AddCustomerCommand(name = customerRequestDTO.name)
        }
        return addCustomersUseCase.execute(addCustomerCommands)
    }

    fun getAll(): Collection<CustomerResponseDTO> {
        return getAllCustomersUseCase.execute().map(customerMapper::toDTO)
    }
}