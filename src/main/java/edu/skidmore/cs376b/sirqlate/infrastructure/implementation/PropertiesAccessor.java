package edu.skidmore.cs376b.sirqlate.infrastructure.implementation;

import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * A class to load and access property definitions from the SirQlate properties file. This is
 * implemented as a Singleton.
 * 
 * @author readda
 *
 */
public class PropertiesAccessor {
  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(PropertiesAccessor.class);

  /**
   * The name of the properties file. It is expected to be found in the home directory of the
   * application.
   */
  private static final String PROPERTIES_FILE_NAME = "sirqlate.properties";

  /**
   * The Singleton instance.
   */
  private static PropertiesAccessor instance;

  /**
   * The properties collection.
   */
  private Properties properties;

  /**
   * Static setup.
   */
  static {
    try {
      instance = new PropertiesAccessor(PROPERTIES_FILE_NAME);
    } catch (Throwable throwable) {
      LOG.error("Unable to setup the properties from the file: " + PROPERTIES_FILE_NAME, throwable);
      instance = new PropertiesAccessor();
    }
  }

  private PropertiesAccessor() {
    this(null);
  }

  private PropertiesAccessor(String filename) {
    properties = new Properties();

    if (filename != null) {
      loadProperties(filename);
    }
  }

  /**
   * Obtain the instance.
   * 
   * @return The instance
   */
  public static final PropertiesAccessor getInstance() {
    return instance;
  }

  /**
   * Load the property keys and values from the properties file.
   * 
   * @param filename The properties file name
   */
  private void loadProperties(String filename) {
    FileInputStream in = null;

    try {
      in = new FileInputStream(PROPERTIES_FILE_NAME);
      properties.load(in);
    } catch (Throwable throwable) {
      LOG.error("Failed to load properties from file: " + filename, throwable);
      throw new IllegalStateException("Unable to load properties from: " + filename, throwable);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (Throwable throwable) {
          LOG.warn("Unable to close properties file: " + filename, throwable);
        }
      }
    }
  }

  /**
   * Get the property value.
   * 
   * @param key The key associated with the value
   * @return The value. This will be null if the key is not present
   */
  public String getPropertyValue(PropertyKey key) {
    return properties.getProperty(key.getKey());
  }

  /**
   * Get the property value. The default value is returned if the requested property is not defined.
   * 
   * @param key The key associated with the value
   * @param defaultValue A default value to return if the key is not defined
   * @return The value. This will be the defaultValue if the key is not present
   */
  public String getPropertyValue(PropertyKey key, String defaultValue) {
    return properties.getProperty(key.getKey(), defaultValue);
  }

  /**
   * Get the property value as an integer.
   * 
   * @param key The key associated with the value
   * @return The value. If the value does not exist, the return value will be null.
   */
  public Integer getPropertyValueInt(PropertyKey key) {
    String value = getPropertyValue(key);

    if (value != null) {
      return Integer.parseInt(value);
    } else {
      return null;
    }
  }

  /**
   * Get the property value as an integer. The default value is returned if the requested property
   * is not defined.
   * 
   * @param key The key associated with the value
   * @param defaultValue A default value to return if the key is not defined
   * @return The value. This will be the defaultValue if the key is not present
   */
  public Integer getPropertyValueInt(PropertyKey key, Integer defaultValue) {
    Integer value = getPropertyValueInt(key);

    if (value != null) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Set a property value.
   * 
   * @param key The key associated with the value
   * @param value The value to be associated with the key
   */
  public void setPropertyValue(PropertyKey key, String value) {
    properties.setProperty(key.getKey(), value);
  }

  /**
   * Remove a key/value pair from the properties collection.
   * 
   * @param key The key to remove
   */
  public void removeProperty(PropertyKey key) {
    properties.remove(key.getKey());
  }
}
