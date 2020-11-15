package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.ApplicationStartup
import com.acme.videorental.rentals.application.ReplayRentalsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StartupConfiguration {

    @Bean
    fun applicationStartup(replayRentalsUseCase: ReplayRentalsUseCase): ApplicationStartup {
        return ApplicationStartup(replayRentalsUseCase)
    }

}
