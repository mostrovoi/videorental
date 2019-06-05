/*
 * This file is generated by jOOQ.
 */
package com.casumo.videorental.infrastructure.repository.jooq;


import com.casumo.videorental.infrastructure.repository.jooq.tables.Customer;
import com.casumo.videorental.infrastructure.repository.jooq.tables.Film;
import com.casumo.videorental.infrastructure.repository.jooq.tables.FilmTransaction;
import com.casumo.videorental.infrastructure.repository.jooq.tables.PaymentTransaction;
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.CustomerRecord;
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.FilmRecord;
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.FilmTransactionRecord;
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.PaymentTransactionRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CustomerRecord> PK_CUSTOMER = UniqueKeys0.PK_CUSTOMER;
    public static final UniqueKey<FilmRecord> PK_FILM = UniqueKeys0.PK_FILM;
    public static final UniqueKey<FilmTransactionRecord> PK_FILM_TRANSACTION = UniqueKeys0.PK_FILM_TRANSACTION;
    public static final UniqueKey<PaymentTransactionRecord> PK_PAYMENT_TRANSACTION = UniqueKeys0.PK_PAYMENT_TRANSACTION;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<FilmTransactionRecord, FilmRecord> FK_FILM_ID_FILM_TRANSACTION = ForeignKeys0.FK_FILM_ID_FILM_TRANSACTION;
    public static final ForeignKey<FilmTransactionRecord, CustomerRecord> FK_CUSTOMER_ID_FILM_TRANSACTION = ForeignKeys0.FK_CUSTOMER_ID_FILM_TRANSACTION;
    public static final ForeignKey<PaymentTransactionRecord, CustomerRecord> FK_CUSTOMER_ID_PAYMENT_TRANSACTION = ForeignKeys0.FK_CUSTOMER_ID_PAYMENT_TRANSACTION;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<CustomerRecord> PK_CUSTOMER = Internal.createUniqueKey(Customer.CUSTOMER, "PK_CUSTOMER", Customer.CUSTOMER.CUSTOMER_ID);
        public static final UniqueKey<FilmRecord> PK_FILM = Internal.createUniqueKey(Film.FILM, "PK_FILM", Film.FILM.FILM_ID);
        public static final UniqueKey<FilmTransactionRecord> PK_FILM_TRANSACTION = Internal.createUniqueKey(FilmTransaction.FILM_TRANSACTION, "PK_FILM_TRANSACTION", FilmTransaction.FILM_TRANSACTION.FILM_TRANSACTION_ID);
        public static final UniqueKey<PaymentTransactionRecord> PK_PAYMENT_TRANSACTION = Internal.createUniqueKey(PaymentTransaction.PAYMENT_TRANSACTION, "PK_PAYMENT_TRANSACTION", PaymentTransaction.PAYMENT_TRANSACTION.PAYMENT_TRANSACTION_ID);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<FilmTransactionRecord, FilmRecord> FK_FILM_ID_FILM_TRANSACTION = Internal.createForeignKey(com.casumo.videorental.infrastructure.repository.jooq.Keys.PK_FILM, FilmTransaction.FILM_TRANSACTION, "FK_FILM_ID_FILM_TRANSACTION", FilmTransaction.FILM_TRANSACTION.FILM_ID);
        public static final ForeignKey<FilmTransactionRecord, CustomerRecord> FK_CUSTOMER_ID_FILM_TRANSACTION = Internal.createForeignKey(com.casumo.videorental.infrastructure.repository.jooq.Keys.PK_CUSTOMER, FilmTransaction.FILM_TRANSACTION, "FK_CUSTOMER_ID_FILM_TRANSACTION", FilmTransaction.FILM_TRANSACTION.CUSTOMER_ID);
        public static final ForeignKey<PaymentTransactionRecord, CustomerRecord> FK_CUSTOMER_ID_PAYMENT_TRANSACTION = Internal.createForeignKey(com.casumo.videorental.infrastructure.repository.jooq.Keys.PK_CUSTOMER, PaymentTransaction.PAYMENT_TRANSACTION, "FK_CUSTOMER_ID_PAYMENT_TRANSACTION", PaymentTransaction.PAYMENT_TRANSACTION.CUSTOMER_ID);
    }
}
