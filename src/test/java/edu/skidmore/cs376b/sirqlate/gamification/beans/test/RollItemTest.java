package edu.skidmore.cs376b.sirqlate.gamification.beans.test;


import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.gamification.beans.RollItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RollItemTest {

  /**
   * Tests the Roll item bean.
   * 
   * @author ndonovan
   */

  RollItem rai;
  Inventory inv;
  String email;
  Player player;

  /**
   * Sets up and initializes values to be used by item.
   */
  @Before
  public void setup() {
    email = "dread@skidmore.edu";
    inv = new Inventory(new ArrayList<Item>());
    player = new Player(email, 0, "Davey", null, null, inv, null, "+RW1MFx1lm4Vn6mCLc/HTw==",
        "5voLxa4=");
  }

  /**
   * Tests adding a roll normally.
   */
  @Test
  public void testAddRoll() {
    int roll = 3;
    int addition = 6;
    rai = new RollItem("Staff of Computer Science", addition);
    player.getInventory().addItem(rai);
    assertEquals("Incorrect roll value", rai.useItem(player, roll), roll + addition);
  }

  /**
   * Tests adding a roll for big numbers.
   */
  @Test
  public void testHighAddRoll() {
    int roll = 3;
    int addition = 200;
    rai = new RollItem("Staff of Computer Science", addition);
    player.getInventory().addItem(rai);
    assertEquals("Incorrect roll value", rai.useItem(player, roll), roll + addition);
  }

  /**
   * Tests adding a roll for negative numbers.
   */
  @Test
  public void testNegativeAddRoll() {
    int roll = 3;
    int addition = -5;
    rai = new RollItem("Staff of Computer Science", addition);
    player.getInventory().addItem(rai);
    assertEquals("Incorrect roll value", rai.useItem(player, roll), roll + addition);
  }

  /**
   * Tests adding a roll cancelling out a roll.
   */
  @Test
  public void testCancelAddRoll() {
    int roll = 3;
    int addition = -3;
    rai = new RollItem("Staff of Computer Science", addition);
    player.getInventory().addItem(rai);
    assertEquals("Incorrect roll value", rai.useItem(player, roll), roll + addition);
  }

  /**
   * Tests adding a roll a very big negative roll.
   */
  @Test
  public void testNegativeBigAddRoll() {
    int roll = 3;
    int addition = -200;
    rai = new RollItem("Staff of Computer Science", addition);
    player.getInventory().addItem(rai);
    assertEquals("Incorrect roll value", rai.useItem(player, roll), roll + addition);
  }

  /**
   * Tests to make sure the item doesn't remain in inventory after use.
   */
  @Test
  public void testRemoveItemFromInventory() {
    rai = new RollItem("Staff of Computer Science", 3);
    player.getInventory().addItem(rai);
    rai.useItem(player, 6);
    boolean hasItem = false;
    for (Item i : inv.getInventory()) {
      if (i == rai) {
        hasItem = true;
      }
    }
    assertEquals("Did not remove the item from inventory", hasItem, false);
  }

}
