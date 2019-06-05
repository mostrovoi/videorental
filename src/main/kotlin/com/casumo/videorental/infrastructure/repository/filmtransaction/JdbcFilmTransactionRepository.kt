package com.casumo.videorental.infrastructure.repository.filmtransaction

import com.casumo.videorental.domain.model.customer.CustomerId
import com.casumo.videorental.domain.model.filmtransaction.FilmTransaction
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionId
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.infrastructure.repository.jooq.Tables
import org.jooq.DSLContext
import java.sql.Timestamp
import java.util.*

class JdbcFilmTransactionRepository(private val dslContext: DSLContext, private val jdbcFilmTransactionMapper: JdbcFilmTransactionMapper) : FilmTransactionRepository {

    override fun findBy(customerId: CustomerId): Collection<FilmTransaction> {
        return dslContext.selectFrom(Tables.FILM_TRANSACTION).where(Tables.FILM_TRANSACTION.CUSTOMER_ID.eq(customerId.anId)).fetch(jdbcFilmTransactionMapper)
    }

    override fun nextIdentity(): FilmTransactionId {
        return FilmTransactionId(UUID.randomUUID().toString())
    }

    override fun save(filmTransaction: FilmTransaction): Boolean {
        dslContext.insertInto(Tables.FILM_TRANSACTION)
                .columns(Tables.FILM_TRANSACTION.FILM_TRANSACTION_ID,
                        Tables.FILM_TRANSACTION.ACTION,
                        Tables.FILM_TRANSACTION.CUSTOMER_ID,
                        Tables.FILM_TRANSACTION.FILM_ID,
                        Tables.FILM_TRANSACTION.NUM_DAYS,
                        Tables.FILM_TRANSACTION.CREATED_ON)
                .values(filmTransaction.filmTransactionId.anId,
                        filmTransaction.action.name,
                        filmTransaction.customerId.anId,
                        filmTransaction.filmId.anId,
                        filmTransaction.numDays,
                        Timestamp.from(filmTransaction.createdOn)).execute()
        return true
    }


    override fun getAll(): Collection<FilmTransaction> {
        return dslContext.selectFrom(Tables.FILM_TRANSACTION).fetch(jdbcFilmTransactionMapper)
    }

    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)
}