package com.acme.videorental.infrastructure.configuration

import org.jooq.ConnectionProvider
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DatabaseConfiguration {

    @Bean
    fun connectionProvider(dataSource: DataSource): DataSourceConnectionProvider {
        return DataSourceConnectionProvider(dataSource)
    }

    @Bean
    fun config(connectionProvider: ConnectionProvider): org.jooq.Configuration {
        val config = DefaultConfiguration()
        config.set(connectionProvider)
        config.set(SQLDialect.H2)
        return config
    }

    @Bean
    fun dslContext(config: org.jooq.Configuration): DSLContext {
        return DSL.using(config)
    }
}