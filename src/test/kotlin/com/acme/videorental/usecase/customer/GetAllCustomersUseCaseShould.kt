package com.acme.videorental.usecase.customer

import com.acme.videorental.domain.model.customer.BonusCard
import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.infrastructure.repository.customer.DummyCustomerRepository
import org.assertj.core.api.Assertions
import org.junit.Test

class GetAllCustomersUseCaseShould {
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun return_none_customer_when_repository_initialized() {
        customerRepository = DummyCustomerRepository(mutableListOf())
        val getCustomersUseCase = GetAllCustomersUseCase(customerRepository = customerRepository)

        getCustomersUseCase.execute()

        Assertions.assertThat(customerRepository.getAll().size).isEqualTo(0)
    }

    @Test
    fun return_all_customers() {
        customerRepository = DummyCustomerRepository(mutableListOf())
        val getCustomersUseCase = GetAllCustomersUseCase(customerRepository = customerRepository)

        val aNewCustomer = aNewCustomer()
        val aSecondCustomer = aSecondCustomer()

        customerRepository.add(aNewCustomer)
        customerRepository.add(aSecondCustomer)

        getCustomersUseCase.execute()
        Assertions.assertThat(customerRepository.getAll().size).isEqualTo(2)
        Assertions.assertThat(customerRepository.getAll()).contains(aNewCustomer)
        Assertions.assertThat(customerRepository.getAll()).contains(aSecondCustomer)
    }


    fun aNewCustomer(): Customer {
        return Customer.aNewCustomer(customerRepository.nextIdentity(), "Alberto")
    }

    fun aSecondCustomer(): Customer {
        return Customer.aNewCustomer(customerRepository.nextIdentity(), "Pedro")
    }
}