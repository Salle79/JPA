package com.thorben.janssen.hibernate.performance.dialect;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class MyPostgreSQLDialect extends PostgreSQL10Dialect {
 
    public MyPostgreSQLDialect() {
        super();
        registerFunction("calculate", new StandardSQLFunction("calculate"));
    }
}
