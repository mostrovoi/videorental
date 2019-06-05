package com.casumo.videorental.infrastructure.repository.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.film.FilmId
import com.casumo.videorental.domain.model.filmtransaction.FilmActionEnum
import com.casumo.videorental.domain.model.filmtransaction.FilmTransaction
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionId
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.FilmTransactionRecord
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