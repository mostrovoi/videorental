package com.acme.videorental.rentals.application.command

import com.acme.videorental.sharedKernel.domain.CustomerId

class ReturnFilmsCommand(val customerId: CustomerId, val returnFilmCommands: Collection<OneFilmReturnCommand>)