package com.acme.videorental.customers.domain

import com.acme.videorental.sharedKernel.domain.CustomerId
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class CustomerShould {

    @Test
    fun be_created_correctly() {
        val customer = Customer.aNewCustomer(CustomerId(UUID.randomUUID().toString()), "Pedro")
        Assertions.assertThat(customer.name).isEqualTo("Pedro")
    }

    @Test(expected = IllegalArgumentException::class)
    fun throw_exception_if_name_is_too_short() {
        Customer.aNewCustomer(CustomerId(UUID.randomUUID().toString()), name = "AB")
    }

}