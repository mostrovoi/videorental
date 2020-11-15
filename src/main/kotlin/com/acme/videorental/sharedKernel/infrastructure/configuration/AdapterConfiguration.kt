package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.customers.application.AddCustomersUseCase
import com.acme.videorental.customers.application.GetAllCustomersUseCase
import com.acme.videorental.customers.infrastructure.controller.adapter.CustomerMapper
import com.acme.videorental.customers.infrastructure.controller.adapter.CustomerRestAdapter
import com.acme.videorental.purchases.application.AddFilmsUseCase
import com.acme.videorental.purchases.application.GetAllFilmsUseCase
import com.acme.videorental.purchases.infrastructure.controller.adapter.FilmMapper
import com.acme.videorental.purchases.infrastructure.controller.adapter.FilmRestAdapter
import com.acme.videorental.rentals.application.RentFilmsUseCase
import com.acme.videorental.rentals.application.ReturnFilmsUseCase
import com.acme.videorental.rentals.infrastructure.controller.filmrental.adapter.FilmRentalRestAdapter
import com.acme.videorental.rentals.infrastructure.controller.filmreturn.adapter.FilmReturnRestAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdapterConfiguration {

    @Bean
    fun customerRestAdapter(addCustomersUseCase: AddCustomersUseCase, getAllCustomersUseCase: GetAllCustomersUseCase, customerMapper: CustomerMapper): CustomerRestAdapter {
        return CustomerRestAdapter(addCustomersUseCase = addCustomersUseCase, getAllCustomersUseCase = getAllCustomersUseCase, customerMapper = customerMapper)
    }

    @Bean
    fun filmRestAdapter(getAllFilmsUseCase: GetAllFilmsUseCase, addFilmsUseCase: AddFilmsUseCase, filmMapper: FilmMapper): FilmRestAdapter {
        return FilmRestAdapter(getAllFilmsUseCase = getAllFilmsUseCase, addFilmsUseCase = addFilmsUseCase, filmMapper = filmMapper)
    }

    @Bean
    fun filmRentalRestAdapter(rentFilmsUseCase: RentFilmsUseCase): FilmRentalRestAdapter {
        return FilmRentalRestAdapter(rentFilmsUseCase = rentFilmsUseCase)
    }

    @Bean
    fun filmReturnRestAdapter(returnFilmUseCase: ReturnFilmsUseCase): FilmReturnRestAdapter {
        return FilmReturnRestAdapter(returnFilmsUseCase = returnFilmUseCase)
    }
}