package com.casumo.videorental.usecase.customer

import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.infrastructure.repository.customer.DummyCustomerRepository
import com.casumo.videorental.usecase.customer.command.AddCustomerCommand
import org.assertj.core.api.Assertions
import org.junit.Test

class DefaultAddCustomersUseCaseShould {
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun add_a_customer_successfully() {
        customerRepository = DummyCustomerRepository(mutableListOf())
        val customerUseCase = DefaultAddCustomersUseCase(customerRepository = customerRepository)
        val addCustomerCommand = mutableListOf(AddCustomerCommand(name = "Pepito"))

        customerUseCase.execute(addCustomerCommand)

        Assertions.assertThat(customerRepository.getAll().size).isEqualTo(1)

    }
}