package com.testing.two.config.rootconfig.database;

import org.springframework.orm.jpa.vendor.Database;

import java.util.Properties;

public class HibernateProperties extends Properties {
    public static final String HIBERNATE_DIALECT_PARAM = "hibernate.dialect";
    public static final String DATABASE_TYPE = "database.type";
    public static final String ORACLE_DIALECT = "org.hibernate.dialect.Oracle10gDialect";
    public static final String H2_DIALECT = "org.hibernate.dialect.H2Dialect";
    public static final String ORACLE_DRIVER_CLASS = "oracle.jdbc.OracleDriver";
    public static final String H2_DRIVER_CLASS = "org.h2.Driver";

    private Database jpaDatabaseType;

    public Database getJpaDatabaseType() {
        return jpaDatabaseType;
    }

    public void setJpaDatabaseType(Database jpaDatabaseType) {
        this.jpaDatabaseType = jpaDatabaseType;
    }
}
