package com.acme.videorental.rentals.application.command

import com.acme.videorental.sharedKernel.domain.CustomerId

class RentFilmsCommand(val customerId: CustomerId, val rentFilmCommands: Collection<OneFilmRentalCommand>)