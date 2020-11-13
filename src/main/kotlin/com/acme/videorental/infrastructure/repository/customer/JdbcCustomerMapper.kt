package com.acme.videorental.infrastructure.repository.customer

import com.acme.videorental.domain.model.customer.BonusCard
import com.acme.videorental.domain.model.customer.Customer
import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.infrastructure.repository.jooq.tables.records.CustomerRecord
import org.jooq.RecordMapper

class JdbcCustomerMapper : RecordMapper<CustomerRecord, Customer> {
    override fun map(customerRecord: CustomerRecord): Customer {
        return Customer.aNewCustomer(customerId = CustomerId(customerRecord.customerId), name = customerRecord.name)
    }
}