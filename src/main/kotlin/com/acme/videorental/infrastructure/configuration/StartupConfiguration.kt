package com.acme.videorental.infrastructure.configuration

import com.acme.videorental.infrastructure.startup.ApplicationStartup
import com.acme.videorental.usecase.replay.ReplayFilmTransactionsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StartupConfiguration {

    @Bean
    fun applicationStartup(replayFilmTransactionsUseCase: ReplayFilmTransactionsUseCase): ApplicationStartup {
        return ApplicationStartup(replayFilmTransactionsUseCase)
    }

}
