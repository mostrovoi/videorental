package com.acme.videorental.rentals.infrastructure

import com.acme.videorental.sharedKernel.domain.FilmActionEnum
import com.acme.videorental.rentals.domain.Rental
import com.acme.videorental.rentals.domain.RentalId
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.domain.FilmId
import com.acme.videorental.sharedKernel.infrastructure.jooq.tables.records.FilmTransactionRecord
import org.jooq.RecordMapper

class JdbcRentalMapper : RecordMapper<FilmTransactionRecord, Rental> {
    override fun map(filmTransactionRecord: FilmTransactionRecord): Rental {
        return Rental(rentalId = RentalId(filmTransactionRecord.filmId),
                filmId = FilmId(filmTransactionRecord.filmId),
                customerId = CustomerId(filmTransactionRecord.customerId),
                numDays = filmTransactionRecord.numDays,
                action = FilmActionEnum.valueOf(filmTransactionRecord.action),
                createdOn = filmTransactionRecord.createdOn.toInstant())
    }

}