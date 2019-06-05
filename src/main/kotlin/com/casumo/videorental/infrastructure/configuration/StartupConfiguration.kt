package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.infrastructure.startup.ApplicationStartup
import com.casumo.videorental.usecase.replay.ReplayFilmTransactionsUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StartupConfiguration {

    @Bean
    fun applicationStartup(replayFilmTransactionsUseCase: ReplayFilmTransactionsUseCase): ApplicationStartup {
        return ApplicationStartup(replayFilmTransactionsUseCase)
    }

}
