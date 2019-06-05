package com.casumo.videorental.usecase.filmtransaction.command

import com.casumo.videorental.domain.model.customer.CustomerId

class RentFilmsCommand(val customerId: CustomerId, val rentFilmCommands: Collection<OneFilmRentalCommand>)