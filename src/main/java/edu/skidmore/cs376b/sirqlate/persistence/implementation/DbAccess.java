package edu.skidmore.cs376b.sirqlate.persistence.implementation;

import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * This is the class that handles the communication between the factory classes and the database
 * access objects.
 * 
 * @author gpetkaki
 */
public class DbAccess {
  /**
   * The Singleton instance.
   */
  private static DbAccess instance;

  /**
   * The DAO for database interactions.
   */
  private DbAccessObject dbAccessObject;

  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(DbAccess.class);

  private DbAccess() {
    try {
      dbAccessObject = new DbAccessObject();
    } catch (SQLException sqle) {
      LOG.error("Unable to obtain a database connection", sqle);
      throw new IllegalStateException("Unable to obtain a database connection", sqle);
    }
  }

  /**
   * Get the Singleton instance.
   * 
   * @return The instance
   */
  public static final synchronized DbAccess getInstance() {
    if (instance == null) {
      instance = new DbAccess();
    }

    return instance;
  }

  public DbAccessObject getAccessObject() {
    return dbAccessObject;
  }
}
