package com.acme.videorental.usecase.filmtransaction.command

import com.acme.videorental.domain.model.customer.CustomerId

class RentFilmsCommand(val customerId: CustomerId, val rentFilmCommands: Collection<OneFilmRentalCommand>)