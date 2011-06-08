package net.pureessence.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractDatabaseConfigurationTest {
    protected static final String APPLICATION_ONE_FIRST_NAME = "Ying";
    protected static final String APPLICATION_ONE_LAST_NAME = "Zhang";
    protected static final String APPLICATION_ONE_EMAIL = "fake.email@pure-essence.net";
    protected static final String APPLICATION_TWO_FIRST_NAME = "Misty";
    protected static final String APPLICATION_TWO_LAST_NAME = "Boodle";
    protected static final String APPLICATION_TWO_EMAIL = "misty.boodle@pure-essence.net";
    protected static final String ENV_CAR_PROPERTY = "car";

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void setUpTestSuite() throws Exception {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("create table configuration(key varchar(255), value varchar(255), application varchar(255))");
        // load alpha properties
        statement.executeUpdate("insert into configuration(key, value, application) values ('first.name', '" + APPLICATION_ONE_FIRST_NAME + "', '" + Constants.APPLICATION_ONE + "')");
        statement.executeUpdate("insert into configuration(key, value, application) values ('last.name', '" + APPLICATION_ONE_LAST_NAME + "', '" + Constants.APPLICATION_ONE + "')");
        statement.executeUpdate("insert into configuration(key, value, application) values ('email', '" + APPLICATION_ONE_EMAIL + "', '" + Constants.APPLICATION_ONE + "')");
        // load genesis properties
        statement.executeUpdate("insert into configuration(key, value, application) values ('first.name', '" + APPLICATION_TWO_FIRST_NAME + "', '" + Constants.APPLICATION_TWO + "')");
        statement.executeUpdate("insert into configuration(key, value, application) values ('last.name', '" + APPLICATION_TWO_LAST_NAME + "', '" + Constants.APPLICATION_TWO + "')");
        statement.executeUpdate("insert into configuration(key, value, application) values ('email', '" + APPLICATION_TWO_EMAIL + "', '" + Constants.APPLICATION_TWO + "')");
        conn.commit();
        conn.close();
    }

    @AfterClass
    public static void cleanUpTestSuite() throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("drop table configuration");
        connection.commit();
        connection.close();
    }

    private static Connection getConnection() throws Exception {
        Class.forName(getDatabaseForConfigurationProperty("driverClassName"));
        return DriverManager.getConnection(getDatabaseForConfigurationProperty("url"), getDatabaseForConfigurationProperty("username"), getDatabaseForConfigurationProperty("password"));
    }

    protected static String getEnvProperty(String key) {
        return getEnvProperty(key, "env.properties");
    }

    protected static String getDatabaseForConfigurationProperty(String key) {
        return getEnvProperty(key, "databaseForConfiguration.properties");
    }

    protected static String getEnvProperty(String key, String filename) {
        Properties props = new Properties();
        try {
            props.load(AbstractDatabaseConfigurationTest.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String value = props.getProperty(key);
        return value;
    }
}