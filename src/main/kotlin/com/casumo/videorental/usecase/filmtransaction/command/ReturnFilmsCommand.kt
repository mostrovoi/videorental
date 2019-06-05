package com.casumo.videorental.usecase.filmtransaction.command

import com.casumo.videorental.domain.model.customer.CustomerId

class ReturnFilmsCommand(val customerId: CustomerId, val returnFilmCommands: Collection<OneFilmReturnCommand>)