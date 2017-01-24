package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

/**
 * This class is for the unit test of Inventory bean.
 * 
 * @author cenglish
 */
public class InventoryTest {
  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Inventory iinventory;

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    Item testItem = new Item("party hat");
    ArrayList<Item> list = new ArrayList<Item>();
    list.add(testItem);
    iinventory = new Inventory(list);
  }

  @Test
  public void getInventory() {

    assertEquals("Inventory not returned correctly", "party hat",
        iinventory.getInventory().get(0).getName());
  }

  @Test
  public void addItem() {
    Item testItem = new Item("party hat");
    Item testItem2 = new Item("dds");
    ArrayList<Item> list = new ArrayList<Item>();
    list.add(testItem);
    list.add(testItem2);

    iinventory.addItem(testItem2);

    assertEquals("Add item not working correctly", "dds",
        iinventory.getInventory().get(1).getName());
  }
}
