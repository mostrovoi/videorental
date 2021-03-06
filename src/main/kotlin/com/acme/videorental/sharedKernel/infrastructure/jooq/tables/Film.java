/*
 * This file is generated by jOOQ.
 */
package com.acme.videorental.sharedKernel.infrastructure.jooq.tables;


import com.acme.videorental.sharedKernel.infrastructure.jooq.Indexes;
import com.acme.videorental.sharedKernel.infrastructure.jooq.Keys;
import com.acme.videorental.sharedKernel.infrastructure.jooq.Public;
import com.acme.videorental.sharedKernel.infrastructure.jooq.tables.records.FilmRecord;

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
public class Film extends TableImpl<FilmRecord> {

    private static final long serialVersionUID = -659118619;

    /**
     * The reference instance of <code>PUBLIC.FILM</code>
     */
    public static final Film FILM = new Film();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FilmRecord> getRecordType() {
        return FilmRecord.class;
    }

    /**
     * The column <code>PUBLIC.FILM.FILM_ID</code>.
     */
    public final TableField<FilmRecord, String> FILM_ID = createField("FILM_ID", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.FILM.NAME</code>.
     */
    public final TableField<FilmRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.FILM.TYPE</code>.
     */
    public final TableField<FilmRecord, String> TYPE = createField("TYPE", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.FILM.CREATED_ON</code>.
     */
    public final TableField<FilmRecord, Timestamp> CREATED_ON = createField("CREATED_ON", org.jooq.impl.SQLDataType.TIMESTAMP.precision(6).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.FILM.UPDATED_ON</code>.
     */
    public final TableField<FilmRecord, Timestamp> UPDATED_ON = createField("UPDATED_ON", org.jooq.impl.SQLDataType.TIMESTAMP.precision(6).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.FILM</code> table reference
     */
    public Film() {
        this(DSL.name("FILM"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.FILM</code> table reference
     */
    public Film(String alias) {
        this(DSL.name(alias), FILM);
    }

    /**
     * Create an aliased <code>PUBLIC.FILM</code> table reference
     */
    public Film(Name alias) {
        this(alias, FILM);
    }

    private Film(Name alias, Table<FilmRecord> aliased) {
        this(alias, aliased, null);
    }

    private Film(Name alias, Table<FilmRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Film(Table<O> child, ForeignKey<O, FilmRecord> key) {
        super(child, key, FILM);
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FilmRecord> getPrimaryKey() {
        return Keys.PK_FILM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FilmRecord>> getKeys() {
        return Arrays.<UniqueKey<FilmRecord>>asList(Keys.PK_FILM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Film as(String alias) {
        return new Film(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Film as(Name alias) {
        return new Film(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Film rename(String name) {
        return new Film(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Film rename(Name name) {
        return new Film(name, null);
    }
}
