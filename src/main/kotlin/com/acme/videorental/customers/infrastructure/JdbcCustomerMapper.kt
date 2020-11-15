package com.acme.videorental.customers.infrastructure

import com.acme.videorental.customers.domain.Customer
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.infrastructure.jooq.tables.records.CustomerRecord
import org.jooq.RecordMapper

class JdbcCustomerMapper : RecordMapper<CustomerRecord, Customer> {
    override fun map(customerRecord: CustomerRecord): Customer {
        return Customer.aNewCustomer(customerId = CustomerId(customerRecord.customerId), name = customerRecord.name)
    }
}