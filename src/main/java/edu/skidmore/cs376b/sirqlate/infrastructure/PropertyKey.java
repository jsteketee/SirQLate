package edu.skidmore.cs376b.sirqlate.infrastructure;

/**
 * Property keys whose values are to be defined in the SirQlate properties file.
 * 
 * @author readda
 *
 */
public enum PropertyKey {
  /**
   * The database driver class.
   */
  PERSISTENCE_DB_DRIVER("persistence.db.driver"),

  /**
   * The database connection endpoint URL.
   */
  PERSISTENCE_DB_URL("persistence.db.url"),

  /**
   * The database connection user name.
   */
  PERSISTENCE_DB_USER("persistence.db.user"),

  /**
   * The database connection password.
   */
  PERSISTENCE_DB_PASSWORD("persistence.db.password"),

  /**
   * Optional front-end width setting.
   */
  FRONTEND_FRAME_WIDTH("frontend.frame.width"),

  /**
   * Optional front-end window automatic centering mode.
   */
  FRONTEND_WINDOW_AUTOCENTERING("frontend.window.position.auto"),

  /**
   * Optional front-end window location x.
   */
  FRONTEND_WINDOW_X("frontend.window.position.x"),

  /**
   * Optional front-end window location y.
   */
  FRONTEND_WINDOW_Y("frontend.window.position.y");


  /**
   * Create the enum.
   * 
   * @param key The value of the key as defined in the properties file
   */
  PropertyKey(String key) {
    this.key = key;
  }

  /**
   * The key string for the property.
   */
  private String key;

  /**
   * Get the key value for the property.
   * 
   * @return The key value
   */
  public String getKey() {
    return key;
  }
}
