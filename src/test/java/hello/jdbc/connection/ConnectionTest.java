package hello.jdbc.connection;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {
    @Test
    void drivermannager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info ("connection={}, class={}", con1, con1.getClass());
        log.info ("connection={}, class={}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        //DriverManagerDataSource - 항상 새로운 커넥션을 획득
//        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        //Thread.sleep(1000); //커넥션 풀에서 커넥션 생성시간 대기
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
//        Connection con3 = dataSource.getConnection();
//        Connection con4 = dataSource.getConnection();
//        Connection con5 = dataSource.getConnection();
//        Connection con6 = dataSource.getConnection();
//        Connection con7 = dataSource.getConnection();
//        Connection con8 = dataSource.getConnection();
//        Connection con9 = dataSource.getConnection();
//        Connection con10 = dataSource.getConnection();
//        Connection con11 = dataSource.getConnection();
        // 풀 가득 찼을경우??;
        log.info ("connection={}, class={}", con1, con1.getClass());
        log.info ("connection={}, class={}", con2, con2.getClass());
    }
}