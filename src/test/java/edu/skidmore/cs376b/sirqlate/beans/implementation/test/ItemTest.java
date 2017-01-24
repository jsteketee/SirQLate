package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This class is for the unit test of Item bean.
 * 
 * @author cenglish
 */
public class ItemTest {

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Item item;

  @Before
  public void setup() {
    item = new Item("blue partyhat");
  }

  @Test
  public void getName() {

    assertEquals("Item not returned correctly", "blue partyhat", item.getName());
  }

  @Test
  public void setItem() {
    item.setName("dfs");
    assertEquals("Item not returned correctly", "dfs", item.getName());
  }
}
