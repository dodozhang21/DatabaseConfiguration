package net.pureessence.example;



import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations={"/datasource.xml","/jdbc-template.xml","/spring-config-application-1.xml"})
public class DatabaseConfigurationApplicationOneTest extends AbstractDatabaseConfigurationTest {
    @Autowired
    @Qualifier("person")
    private Person person;

    @Test
    public void testPropertiesPrinter() {
        int totalConfigurations = jdbcTemplate.queryForInt("select count(*) from configuration");
        assertEquals(6, totalConfigurations);
        int configurationsForAlpha = jdbcTemplate.queryForInt("select count(*) from configuration where application='"+Constants.APPLICATION_ONE+"'");
        assertEquals(3, configurationsForAlpha);
        assertEquals(APPLICATION_ONE_FIRST_NAME, person.getFirstName());
        assertEquals(APPLICATION_ONE_LAST_NAME, person.getLastName());
        assertEquals(APPLICATION_ONE_EMAIL, person.getEmail());
        assertEquals(getEnvProperty(ENV_CAR_PROPERTY), person.getCar());
    }
}