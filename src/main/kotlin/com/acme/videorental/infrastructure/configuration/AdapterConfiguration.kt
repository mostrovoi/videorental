package com.acme.videorental.infrastructure.configuration

import com.acme.videorental.infrastructure.controller.customer.adapter.CustomerMapper
import com.acme.videorental.infrastructure.controller.customer.adapter.CustomerRestAdapter
import com.acme.videorental.infrastructure.controller.film.adapter.FilmMapper
import com.acme.videorental.infrastructure.controller.film.adapter.FilmRestAdapter
import com.acme.videorental.infrastructure.controller.filmrental.adapter.FilmRentalRestAdapter
import com.acme.videorental.infrastructure.controller.filmrental.adapter.FilmReturnRestAdapter
import com.acme.videorental.usecase.customer.AddCustomersUseCase
import com.acme.videorental.usecase.customer.GetAllCustomersUseCase
import com.acme.videorental.usecase.film.AddFilmsUseCase
import com.acme.videorental.usecase.film.GetAllFilmsUseCase
import com.acme.videorental.usecase.filmtransaction.RentFilmsUseCase
import com.acme.videorental.usecase.filmtransaction.ReturnFilmsUseCase
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