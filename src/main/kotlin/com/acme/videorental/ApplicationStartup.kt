package com.acme.videorental

import com.acme.videorental.rentals.application.ReplayRentalsUseCase
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.lang.NonNull

class ApplicationStartup(private val replayFilmTransactionsUseCase: ReplayRentalsUseCase) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(@NonNull event: ApplicationReadyEvent) {
        replayFilmTransactionsUseCase.execute()
    }

}
