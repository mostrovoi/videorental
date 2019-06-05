package com.casumo.videorental.usecase.customer

import com.casumo.videorental.domain.model.customer.BonusCard
import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.infrastructure.repository.customer.DummyCustomerRepository
import org.assertj.core.api.Assertions
import org.junit.Test

class DefaultGetAllCustomersUseCaseShould {
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun return_none_customer_when_repository_initialized() {
        customerRepository = DummyCustomerRepository(mutableListOf())
        val getCustomersUseCase = DefaultGetAllCustomersUseCase(customerRepository = customerRepository)

        getCustomersUseCase.execute()

        Assertions.assertThat(customerRepository.getAll().size).isEqualTo(0)
    }

    @Test
    fun return_all_customers() {
        customerRepository = DummyCustomerRepository(mutableListOf())
        val getCustomersUseCase = DefaultGetAllCustomersUseCase(customerRepository = customerRepository)

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