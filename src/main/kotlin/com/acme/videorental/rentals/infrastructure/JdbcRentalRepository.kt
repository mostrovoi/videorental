package com.acme.videorental.rentals.infrastructure

import com.acme.videorental.rentals.domain.Rental
import com.acme.videorental.rentals.domain.RentalId
import com.acme.videorental.rentals.domain.RentalRepository
import com.acme.videorental.sharedKernel.domain.CustomerId
import com.acme.videorental.sharedKernel.infrastructure.jooq.Tables
import org.jooq.DSLContext
import java.sql.Timestamp
import java.util.*

class JdbcRentalRepository(private val dslContext: DSLContext, private val jdbcFilmTransactionMapper: JdbcRentalMapper) : RentalRepository {

    override fun findBy(customerId: CustomerId): Collection<Rental> {
        return dslContext.selectFrom(Tables.FILM_TRANSACTION).where(Tables.FILM_TRANSACTION.CUSTOMER_ID.eq(customerId.anId)).fetch(jdbcFilmTransactionMapper)
    }

    override fun nextIdentity(): RentalId {
        return RentalId(UUID.randomUUID().toString())
    }

    override fun save(filmTransaction: Rental): Boolean {
        dslContext.insertInto(Tables.FILM_TRANSACTION)
                .columns(Tables.FILM_TRANSACTION.FILM_TRANSACTION_ID,
                        Tables.FILM_TRANSACTION.ACTION,
                        Tables.FILM_TRANSACTION.CUSTOMER_ID,
                        Tables.FILM_TRANSACTION.FILM_ID,
                        Tables.FILM_TRANSACTION.NUM_DAYS,
                        Tables.FILM_TRANSACTION.CREATED_ON)
                .values(filmTransaction.rentalId.anId,
                        filmTransaction.action.name,
                        filmTransaction.customerId.anId,
                        filmTransaction.filmId.anId,
                        filmTransaction.numDays,
                        Timestamp.from(filmTransaction.createdOn)).execute()
        return true
    }


    override fun getAll(): Collection<Rental> {
        return dslContext.selectFrom(Tables.FILM_TRANSACTION).fetch(jdbcFilmTransactionMapper)
    }

    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)
}