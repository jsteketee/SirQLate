package edu.skidmore.cs376b.sirqlate.persistence.implementation;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryProcessor {
  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(QueryProcessor.class);

  private String url;
  private String userId;
  private String password;

  /**
   * Create a query processor connected to the supplied database using the supplied credentials.
   * 
   * @param driver The database JDBC driver
   * @param url The connection URL
   * @param userId The user id for the connection
   * @param password The password for the connection
   * @throws IllegalStateException If the underlying database connection is broken
   */
  public QueryProcessor(String driver, String url, String userId, String password) {
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException cnfe) {
      LOG.error("Unable to load the DB driver: " + driver, cnfe);
      throw new IllegalStateException("Unable to load the DB driver: " + driver, cnfe);
    }

    setUrl(url);
    setUserId(userId);
    setPassword(password);

    try {
      testConnection();
    } catch (SQLException exception) {
      LOG.error("Failed to execute the database test query", exception);
      throw new IllegalStateException("Failed to execute the connection test query", exception);
    }
  }

  /**
   * Test the connection.
   * 
   * @throws SQLException If the query cannot be parsed or executed
   */
  private void testConnection() throws SQLException {
    setupQuery("select now()").executeQuery();
  }

  /**
   * Run a select query.
   * 
   * @param query The query to run
   * @return The results
   */
  public PreparedStatement setupQuery(String query) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      conn = DriverManager.getConnection(getUrl(), getUserId(), getPassword());
      stmt = conn.prepareStatement(query);
    } catch (SQLException sqle) {
      LOG.error("Unable to setup the query: " + query, sqle);
      throw new IllegalStateException("Unable to process the query", sqle);
    }
    return stmt;
  }

  /**
   * Close the result set and the underlying database connection.
   * 
   * @param rs The open result set
   */
  public void closeResultSet(ResultSet rs) {
    if (rs != null) {
      try {
        Statement stmt = rs.getStatement();
        rs.close();
        closeStatement(stmt);
      } catch (SQLException sqle) {
        LOG.error("Failed to close database connection", sqle);
      }
    }
  }

  /**
   * Close the statement and the underlying database connection.
   * 
   * @param stmt The open statement
   */
  public void closeStatement(Statement stmt) {
    if (stmt != null) {
      try {
        Connection conn = stmt.getConnection();
        stmt.close();
        conn.close();
      } catch (SQLException sqle) {
        LOG.error("Failed to close database connection", sqle);
      }
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
