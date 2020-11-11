package com.acme.videorental.usecase.customer

import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.infrastructure.repository.customer.DummyCustomerRepository
import com.acme.videorental.usecase.customer.command.AddCustomerCommand
import org.assertj.core.api.Assertions
import org.junit.Test

class AddCustomersUseCaseShould {
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun add_a_customer_successfully() {
        customerRepository = DummyCustomerRepository(mutableListOf())
        val customerUseCase = AddCustomersUseCase(customerRepository = customerRepository)
        val addCustomerCommand = mutableListOf(AddCustomerCommand(name = "Pepito"))

        customerUseCase.execute(addCustomerCommand)

        Assertions.assertThat(customerRepository.getAll().size).isEqualTo(1)

    }
}