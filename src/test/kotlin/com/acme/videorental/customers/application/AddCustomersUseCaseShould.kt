package com.acme.videorental.customers.application

import com.acme.videorental.customers.application.command.AddCustomerCommand
import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.customers.infrastructure.DummyCustomerRepository
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