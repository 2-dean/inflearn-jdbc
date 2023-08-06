package hello.jdbc.exception.basic;

import java.sql.SQLException;

public class RuntimeSQLException extends Throwable {
    public RuntimeSQLException(SQLException e) {
    }
}
