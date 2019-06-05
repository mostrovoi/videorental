package com.casumo.videorental.infrastructure.startup


import com.casumo.videorental.usecase.replay.ReplayFilmTransactionsUseCase
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.lang.NonNull

class ApplicationStartup(private val replayFilmTransactionsUseCase: ReplayFilmTransactionsUseCase) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(@NonNull event: ApplicationReadyEvent) {
        replayFilmTransactionsUseCase.execute()
    }

}
