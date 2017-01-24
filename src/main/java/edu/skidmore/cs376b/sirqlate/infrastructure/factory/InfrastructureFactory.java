package edu.skidmore.cs376b.sirqlate.infrastructure.factory;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;
import edu.skidmore.cs376b.sirqlate.infrastructure.implementation.PasswordHash;
import edu.skidmore.cs376b.sirqlate.infrastructure.implementation.PropertiesAccessor;

/**
 * Factory class infrastructure methods.
 * 
 * @author gjin
 */

public final class InfrastructureFactory {
  /**
   * instance of the class.
   */
  private static InfrastructureFactory instance;

  /**
   * The private constructor of the class.
   */
  private InfrastructureFactory() {

  }

  /**
   * @return Returns the singleton instance of the class.
   * 
   * 
   */
  public static synchronized InfrastructureFactory getInstance() {
    if (instance == null) {
      instance = new InfrastructureFactory();
    }

    return instance;
  }

  /**
   * This method create the hashed password for a player.
   * 
   * @param player the player who is creating password
   * @param pass the string password the player created
   */
  public void createHashedPassword(Player player, String pass) {
    PasswordHash passhash = new PasswordHash();
    passhash.createHashedPassword(player, pass);
  }

  /**
   * This method create the hashed password for a player.
   * 
   * @param player the player who is logging in
   * @param pass the string password the player typed
   * 
   * @return Returns the boolean whether the password matches the one in the database
   * 
   */
  public boolean validatePassword(Player player, String pass) {
    PasswordHash passhash = new PasswordHash();
    return passhash.validatePassword(player, pass);
  }

  /**
   * Get the property value.
   * 
   * @param key The key associated with the value
   * @return The value. This will be null if the key is not present
   */
  public String getPropertyValue(PropertyKey key) {
    return PropertiesAccessor.getInstance().getPropertyValue(key);
  }

  /**
   * Get the property value. The default value is returned if the requested property is not defined.
   * 
   * @param key The key associated with the value
   * @param defaultValue A default value to return if the key is not defined
   * @return The value. This will be the defaultValue if the key is not present
   */
  public String getPropertyValue(PropertyKey key, String defaultValue) {
    return PropertiesAccessor.getInstance().getPropertyValue(key, defaultValue);
  }

  /**
   * Get the property value as an integer.
   * 
   * @param key The key associated with the value
   * @return The value. This will be null if the key is not present
   */
  public Integer getPropertyValueInt(PropertyKey key) {
    return PropertiesAccessor.getInstance().getPropertyValueInt(key);
  }

  /**
   * Get the property value as an integer. The default value is returned if the requsted property is
   * not defined.
   * 
   * @param key The key associated with the value
   * @param defaultValue A default value to return if the key is not defined
   * @return The value. This will be the defaultValue if the key is not present
   */
  public Integer getPropertyValueInt(PropertyKey key, Integer defaultValue) {
    return PropertiesAccessor.getInstance().getPropertyValueInt(key, defaultValue);
  }

  /**
   * Set a property value. Note that all property values are stored as strings. If the string is a
   * set of digits it can be retrieved as a number.
   * 
   * @param key The key associated with the value
   * @param value The value to be associated with the key
   */
  public void setProperty(PropertyKey key, String value) {
    PropertiesAccessor.getInstance().setPropertyValue(key, value);
  }

  /**
   * Remove a key/value pair from the properties collection.
   * 
   * @param key The key to remove
   */
  public void removeProperty(PropertyKey key) {
    PropertiesAccessor.getInstance().removeProperty(key);
  }
}
