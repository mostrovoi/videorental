package com.acme.videorental.infrastructure.controller.customer.adapter

import com.acme.videorental.infrastructure.controller.customer.CustomerResponseDTO
import com.acme.videorental.infrastructure.controller.customer.dto.CustomerRequestDTO
import com.acme.videorental.usecase.customer.AddCustomersUseCase
import com.acme.videorental.usecase.customer.GetAllCustomersUseCase
import com.acme.videorental.usecase.customer.command.AddCustomerCommand

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