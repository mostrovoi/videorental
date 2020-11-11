package com.acme.videorental.infrastructure.repository.filmtransaction

import com.acme.videorental.domain.model.customer.CustomerId
import com.acme.videorental.domain.model.film.FilmId
import com.acme.videorental.domain.model.filmtransaction.FilmActionEnum
import com.acme.videorental.domain.model.filmtransaction.FilmTransaction
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionId
import com.acme.videorental.infrastructure.repository.jooq.tables.records.FilmTransactionRecord
import org.jooq.RecordMapper

class JdbcFilmTransactionMapper : RecordMapper<FilmTransactionRecord, FilmTransaction> {
    override fun map(filmTransactionRecord: FilmTransactionRecord): FilmTransaction {
        return FilmTransaction(filmTransactionId = FilmTransactionId(filmTransactionRecord.filmId),
                filmId = FilmId(filmTransactionRecord.filmId),
                customerId = CustomerId(filmTransactionRecord.customerId),
                numDays = filmTransactionRecord.numDays,
                action = FilmActionEnum.valueOf(filmTransactionRecord.action),
                createdOn = filmTransactionRecord.createdOn.toInstant())
    }

}