package com.acme.videorental.usecase.filmtransaction.command

import com.acme.videorental.domain.model.customer.CustomerId

class ReturnFilmsCommand(val customerId: CustomerId, val returnFilmCommands: Collection<OneFilmReturnCommand>)