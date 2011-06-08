package net.pureessence.example;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

// for more info on BasicDataSourceFactory property keys
// http://commons.apache.org/dbcp/xref-test/org/apache/commons/dbcp/TestBasicDataSourceFactory.html#50
public class PropertiesDataSourceFactory {
    public static DataSource createDataSource(String propertyFilename) throws Exception {
        Properties properties = new Properties();
        InputStream in = PropertiesDataSourceFactory.class.getClassLoader().getResourceAsStream(propertyFilename);
        properties.load(in);
        in.close();

        return BasicDataSourceFactory.createDataSource(properties);
    }
}