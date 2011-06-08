package net.pureessence.example;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import static junit.framework.Assert.assertEquals;

@ContextConfiguration(locations={"/datasource.xml","/jdbc-template.xml","/spring-config-application-2.xml"})
public class DatabaseConfigurationApplicationTwoTest extends AbstractDatabaseConfigurationTest {
    @Autowired
    @Qualifier("person")
    private Person person;

    @Test
    public void testPropertiesPrinter() {
        int totalConfigurations = jdbcTemplate.queryForInt("select count(*) from configuration");
        assertEquals(6, totalConfigurations);
        int configurationsForAlpha = jdbcTemplate.queryForInt("select count(*) from configuration where application='"+Constants.APPLICATION_TWO+"'");
        assertEquals(3, configurationsForAlpha);
        assertEquals(APPLICATION_TWO_FIRST_NAME, person.getFirstName());
        assertEquals(APPLICATION_TWO_LAST_NAME, person.getLastName());
        assertEquals(APPLICATION_TWO_EMAIL, person.getEmail());
        assertEquals(getEnvProperty(ENV_CAR_PROPERTY), person.getCar());
    }
}