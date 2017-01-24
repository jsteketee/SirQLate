package edu.skidmore.cs376b.sirqlate.infrastructure.implementation.test;

import static org.junit.Assert.assertNull;

import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;
import edu.skidmore.cs376b.sirqlate.infrastructure.implementation.PropertiesAccessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

/**
 * Test class for the PropertyAccessor class.
 * 
 * @author readda
 *
 */
public class PropertyAccessorNoConfigFileTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Production properties file.
   */
  private File propFile = new File("sirqlate.properties");

  /**
   * Temporary location for properties file.
   */
  private File propFileTemp = new File("sirqlate.properties.temp");

  /**
   * Setup the properties for testing. Stores the original values so that they can be reset after
   * the test.
   */
  @Before
  public void setup() {
    propFileTemp.delete();
    propFile.renameTo(propFileTemp);
  }

  /**
   * Restore the original properties values.
   */
  @After
  public void tearDown() {
    propFile.delete();
    propFileTemp.renameTo(propFile);
  }

  /**
   * Unit test for missing properties file.
   */
  @Test
  public void testMissingPropertiesFile() {
    assertNull("Property was not null",
        PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER));
  }

}
