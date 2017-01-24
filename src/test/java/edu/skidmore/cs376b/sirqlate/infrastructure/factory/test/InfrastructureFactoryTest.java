package edu.skidmore.cs376b.sirqlate.infrastructure.factory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;
import edu.skidmore.cs376b.sirqlate.infrastructure.factory.InfrastructureFactory;
import edu.skidmore.cs376b.sirqlate.infrastructure.implementation.PropertiesAccessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for InfrastructureFactory.
 * 
 * @author gjin
 */

public class InfrastructureFactoryTest {
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
   * An instance of the generator for testing.
   */
  private InfrastructureFactory instance;

  /**
   * A test player.
   */
  private Player player;

  /**
   * setup.
   */
  @Before
  public void setup() {
    instance = InfrastructureFactory.getInstance();
    player = new Player("gabe@gmail.com", "Gabe", null, "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");

    // Store the original values before changing them
    password =
        PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_PASSWORD);
    driver = PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER);
    frameWidth =
        PropertiesAccessor.getInstance().getPropertyValue(PropertyKey.FRONTEND_FRAME_WIDTH);

    instance.removeProperty(PropertyKey.PERSISTENCE_DB_PASSWORD);
    instance.setProperty(PropertyKey.PERSISTENCE_DB_DRIVER, "TestDriver");
    instance.setProperty(PropertyKey.FRONTEND_FRAME_WIDTH, "100");

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
   * Unit test of createHashedPassword.
   */
  @Test
  public void testCreateHashedPaswordNotNull() {
    instance.createHashedPassword(player, "password");
    assertNotNull("Password hash is null", player.getPasswordHash());
    assertNotNull("Salt is null", player.getSalt());
  }

  /**
   * Unit test of validatePassword true.
   */
  @Test
  public void testValidatePasswordMatch() {
    instance.createHashedPassword(player, "password");
    assertTrue("Hash did not match", instance.validatePassword(player, "password"));

  }

  /**
   * Unit test of validatePassword false.
   */
  @Test
  public void testValidatePasswordNotMatch() {
    instance.createHashedPassword(player, "pasword");
    assertFalse("Hash did not match", instance.validatePassword(player, "password"));

  }

  /**
   * Unit test for getProperty(PropertyKey).
   */
  @Test
  public void testGetPropertyValue() {
    assertEquals("Property not correct", "TestDriver",
        instance.getPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER));
  }

  /**
   * Unit test for getProperty(PropertyKey, String).
   */
  @Test
  public void testGetPropertyValueWithDefault() {
    assertEquals("Default not used", "Hello",
        instance.getPropertyValue(PropertyKey.PERSISTENCE_DB_PASSWORD, "Hello"));
  }

  /**
   * Unit test for getPropertyValueInt(PropertyKey).
   */
  @Test
  public void testGetPropertyValueInt() {
    assertEquals("Property not correct", 100,
        (int) instance.getPropertyValueInt(PropertyKey.FRONTEND_FRAME_WIDTH));
  }

  /**
   * Unit test for getProperty(ProperyKey, Integer).
   */
  @Test
  public void testGetPropertyValueIntWithDefault() {
    assertEquals("Default not used", 150,
        (int) instance.getPropertyValueInt(PropertyKey.PERSISTENCE_DB_PASSWORD, 150));
  }

  /**
   * Unit test for removeProperty().
   */
  @Test
  public void testRemoveProperty() {
    instance.removeProperty(PropertyKey.FRONTEND_FRAME_WIDTH);
    assertEquals("Property not removed", 150,
        (int) instance.getPropertyValueInt(PropertyKey.FRONTEND_FRAME_WIDTH, 150));

  }

}
