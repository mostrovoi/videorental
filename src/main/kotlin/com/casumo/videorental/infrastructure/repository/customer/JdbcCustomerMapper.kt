package com.casumo.videorental.infrastructure.repository.customer

import com.casumo.videorental.domain.model.customer.BonusCard
import com.casumo.videorental.domain.model.customer.Customer
import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.CustomerRecord
import org.jooq.RecordMapper

class JdbcCustomerMapper : RecordMapper<CustomerRecord, Customer> {
    override fun map(customerRecord: CustomerRecord): Customer {
        return Customer(customerId = CustomerId(customerRecord.customerId),
                bonusCard = BonusCard(customerRecord.bonusCardPoints),
                name = customerRecord.name,
                createdOn = customerRecord.createdOn.toInstant(),
                updatedOn = customerRecord.updatedOn.toInstant())

    }

}