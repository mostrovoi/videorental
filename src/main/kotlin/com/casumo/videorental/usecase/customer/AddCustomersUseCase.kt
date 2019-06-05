package com.casumo.videorental.usecase.customer

import com.casumo.videorental.usecase.customer.command.AddCustomerCommand


interface AddCustomersUseCase {
    fun execute(customerCommands: Collection<AddCustomerCommand>)
}
