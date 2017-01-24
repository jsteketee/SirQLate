package edu.skidmore.cs376b.sirqlate.persistence.implementation.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.persistence.implementation.DbAccessObject;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Unit testing for the DbAccessObject.
 * 
 * @author mleong
 *
 */
public class DbAccessObjectTest {
  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(DbAccessObjectTest.class);

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the Access Object for testing.
   */
  private DbAccessObject accObj;
  private Inventory inven = null;

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    try {
      accObj = new DbAccessObject();
    } catch (SQLException sqle) {
      LOG.error("Unable to obtain an instance of the DbAccessObject", sqle);
    }
  }

  @Test
  public void getPlayer() {
    assertEquals("Incorrect player bean returned", "Ann",
        accObj.getPlayer("ann@skidmore.edu").getName());
  }

  @Test
  public void getPlayerDoesNotExist() {
    assertNull("The player should not exist", accObj.getPlayer("asdfasdf"));
  }

  @Test
  public void getItems() {
    // need to know what items are in the database to accurately test. wait for getItemsDoesNotExist
    // as well
    assertEquals("Incorrect item data set returned", 20, accObj.getItems().length);
  }

  @Test
  public void getInventory() {
    inven = new Inventory(new ArrayList<Item>());
    // make a test email in the database with data that will never change for testing
    assertArrayEquals("Incorrect inventory returned for player", inven.getInventory().toArray(),
        accObj.getInventory("dan@skidmore.edu").getInventory().toArray());
    // test account in database should have an inventory of just "cherry"
  }

  @Test
  public void getInventoryDoesNotExist() {
    assertEquals("No inventory should exist for this email", 0,
        accObj.getInventory("asfdafd").getInventory().size());
  }
}
