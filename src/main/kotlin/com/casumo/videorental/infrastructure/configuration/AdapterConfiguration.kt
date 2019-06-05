package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.infrastructure.controller.customer.adapter.CustomerMapper
import com.casumo.videorental.infrastructure.controller.customer.adapter.CustomerRestAdapter
import com.casumo.videorental.infrastructure.controller.film.adapter.FilmMapper
import com.casumo.videorental.infrastructure.controller.film.adapter.FilmRestAdapter
import com.casumo.videorental.infrastructure.controller.filmrental.adapter.FilmRentalRestAdapter
import com.casumo.videorental.infrastructure.controller.filmrental.adapter.FilmReturnRestAdapter
import com.casumo.videorental.usecase.customer.AddCustomersUseCase
import com.casumo.videorental.usecase.customer.GetAllCustomersUseCase
import com.casumo.videorental.usecase.film.AddFilmsUseCase
import com.casumo.videorental.usecase.film.GetAllFilmsUseCase
import com.casumo.videorental.usecase.filmtransaction.RentFilmsUseCase
import com.casumo.videorental.usecase.filmtransaction.ReturnFilmsUseCase
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