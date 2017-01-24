package edu.skidmore.cs376b.sirqlate.infrastructure.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;
import edu.skidmore.cs376b.sirqlate.infrastructure.implementation.PropertiesAccessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the PropertyAccessor class.
 * 
 * @author readda
 *
 */
public class PropertyAccessorTest {
  /**
   * The original password value.
   */
  private String password;

  /**
   * The original driver value.
   */
  private String driver;

  /**
   * The original frame width value.
   */
  private String frameWidth;

  /**
   * Setup the properties for testing. Stores the original values so that they can be reset after
   * the test.
   */
  @Before
  public void setup() {
    // Store the original values before changing them
    password =
        PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_PASSWORD);
    driver = PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER);
    frameWidth =
        PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.FRONTEND_FRAME_WIDTH);

    PropertiesAccessor.getInstance().removeProperty(PropertyKey.PERSISTENCE_DB_PASSWORD);
    PropertiesAccessor.getInstance().setPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER,
        "TestDriver");
    PropertiesAccessor.getInstance().setPropertyValue(PropertyKey.FRONTEND_FRAME_WIDTH, "100");
  }

  /**
   * Restore the original properties values.
   */
  @After
  public void tearDown() {
    PropertiesAccessor.getInstance().removeProperty(PropertyKey.PERSISTENCE_DB_PASSWORD);
    PropertiesAccessor.getInstance().removeProperty(PropertyKey.PERSISTENCE_DB_DRIVER);
    PropertiesAccessor.getInstance().removeProperty(PropertyKey.FRONTEND_FRAME_WIDTH);

    if (password != null) {
      PropertiesAccessor.getInstance().setPropertyValue(PropertyKey.PERSISTENCE_DB_PASSWORD,
          password);
    }

    if (driver != null) {
      PropertiesAccessor.getInstance().setPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER, driver);
    }

    if (frameWidth != null) {
      PropertiesAccessor.getInstance().setPropertyValue(PropertyKey.FRONTEND_FRAME_WIDTH,
          frameWidth);
    }

  }

  /**
   * Unit test for getProperty(PropertyKey).
   */
  @Test
  public void testGetPropertyValue() {
    assertEquals("Property not correct", "TestDriver",
        PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER));
  }

  /**
   * Unit test for getProperty(PropertyKey, String).
   */
  @Test
  public void testGetPropertyValueWithDefault() {
    assertEquals("Default not used", "Hello", PropertiesAccessor.getInstance()
        .getPropertyValue(PropertyKey.PERSISTENCE_DB_PASSWORD, "Hello"));
  }

  /**
   * Unit test for getPropertyValueInt(PropertyKey).
   */
  @Test
  public void testGetPropertyValueInt() {
    assertEquals("Property not correct", 100, (int) PropertiesAccessor.getInstance()
        .getPropertyValueInt(PropertyKey.FRONTEND_FRAME_WIDTH));
  }

  /**
   * Unit test for getProperty(ProperyKey, Integer).
   */
  @Test
  public void testGetPropertyValueIntWithDefault() {
    assertEquals("Default not used", 150, (int) PropertiesAccessor.getInstance()
        .getPropertyValueInt(PropertyKey.PERSISTENCE_DB_PASSWORD, 150));

    assertEquals("Default used", 100, (int) PropertiesAccessor.getInstance()
        .getPropertyValueInt(PropertyKey.FRONTEND_FRAME_WIDTH, 150));
  }

  /**
   * Unit test for removeProperty().
   */
  @Test
  public void testRemoveProperty() {
    PropertiesAccessor.getInstance().removeProperty(PropertyKey.FRONTEND_FRAME_WIDTH);
    assertEquals("Property not removed", 150, (int) PropertiesAccessor.getInstance()
        .getPropertyValueInt(PropertyKey.FRONTEND_FRAME_WIDTH, 150));

  }
}
