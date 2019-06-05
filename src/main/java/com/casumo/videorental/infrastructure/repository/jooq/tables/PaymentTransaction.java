/*
 * This file is generated by jOOQ.
 */
package com.casumo.videorental.infrastructure.repository.jooq.tables;


import com.casumo.videorental.infrastructure.repository.jooq.Indexes;
import com.casumo.videorental.infrastructure.repository.jooq.Keys;
import com.casumo.videorental.infrastructure.repository.jooq.Public;
import com.casumo.videorental.infrastructure.repository.jooq.tables.records.PaymentTransactionRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PaymentTransaction extends TableImpl<PaymentTransactionRecord> {

    private static final long serialVersionUID = -1705318568;

    /**
     * The reference instance of <code>PUBLIC.PAYMENT_TRANSACTION</code>
     */
    public static final PaymentTransaction PAYMENT_TRANSACTION = new PaymentTransaction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PaymentTransactionRecord> getRecordType() {
        return PaymentTransactionRecord.class;
    }

    /**
     * The column <code>PUBLIC.PAYMENT_TRANSACTION.PAYMENT_TRANSACTION_ID</code>.
     */
    public final TableField<PaymentTransactionRecord, String> PAYMENT_TRANSACTION_ID = createField("PAYMENT_TRANSACTION_ID", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PAYMENT_TRANSACTION.CUSTOMER_ID</code>.
     */
    public final TableField<PaymentTransactionRecord, String> CUSTOMER_ID = createField("CUSTOMER_ID", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PAYMENT_TRANSACTION.AMOUNT</code>.
     */
    public final TableField<PaymentTransactionRecord, Long> AMOUNT = createField("AMOUNT", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>PUBLIC.PAYMENT_TRANSACTION.PAYMENT_TYPE</code>.
     */
    public final TableField<PaymentTransactionRecord, String> PAYMENT_TYPE = createField("PAYMENT_TYPE", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PAYMENT_TRANSACTION.CREATED_ON</code>.
     */
    public final TableField<PaymentTransactionRecord, Timestamp> CREATED_ON = createField("CREATED_ON", org.jooq.impl.SQLDataType.TIMESTAMP.precision(6).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.PAYMENT_TRANSACTION</code> table reference
     */
    public PaymentTransaction() {
        this(DSL.name("PAYMENT_TRANSACTION"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.PAYMENT_TRANSACTION</code> table reference
     */
    public PaymentTransaction(String alias) {
        this(DSL.name(alias), PAYMENT_TRANSACTION);
    }

    /**
     * Create an aliased <code>PUBLIC.PAYMENT_TRANSACTION</code> table reference
     */
    public PaymentTransaction(Name alias) {
        this(alias, PAYMENT_TRANSACTION);
    }

    private PaymentTransaction(Name alias, Table<PaymentTransactionRecord> aliased) {
        this(alias, aliased, null);
    }

    private PaymentTransaction(Name alias, Table<PaymentTransactionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> PaymentTransaction(Table<O> child, ForeignKey<O, PaymentTransactionRecord> key) {
        super(child, key, PAYMENT_TRANSACTION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.FK_CUSTOMER_ID_PAYMENT_TRANSACTION_INDEX_3, Indexes.PRIMARY_KEY_3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PaymentTransactionRecord> getPrimaryKey() {
        return Keys.PK_PAYMENT_TRANSACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PaymentTransactionRecord>> getKeys() {
        return Arrays.<UniqueKey<PaymentTransactionRecord>>asList(Keys.PK_PAYMENT_TRANSACTION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<PaymentTransactionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PaymentTransactionRecord, ?>>asList(Keys.FK_CUSTOMER_ID_PAYMENT_TRANSACTION);
    }

    public Customer customer() {
        return new Customer(this, Keys.FK_CUSTOMER_ID_PAYMENT_TRANSACTION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentTransaction as(String alias) {
        return new PaymentTransaction(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentTransaction as(Name alias) {
        return new PaymentTransaction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PaymentTransaction rename(String name) {
        return new PaymentTransaction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PaymentTransaction rename(Name name) {
        return new PaymentTransaction(name, null);
    }
}
