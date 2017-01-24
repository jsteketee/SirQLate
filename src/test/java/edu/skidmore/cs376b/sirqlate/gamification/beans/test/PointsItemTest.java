package edu.skidmore.cs376b.sirqlate.gamification.beans.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievements;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.beans.PointsItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;



public class PointsItemTest {

  /**
   * Tests the Points item bean.
   * 
   * @author ndonovan
   */

  PointsItem pai;
  String email;
  int gamesPlayed;
  String name;
  Date birthday;
  Points points;
  Inventory inv;
  Achievements achievements;
  String pw;
  String salt;
  Player player;

  /**
   * Sets up and initializes values to be used by item.
   */
  @SuppressWarnings("deprecation")
  @Before
  public void setup() {
    email = "dread@skidmore.edu";
    gamesPlayed = 1;
    name = "Davey";
    birthday = new Date(2003, 8, 8);
    points = new Points(3);
    inv = new Inventory(new ArrayList<Item>());
    achievements = new Achievements(new ArrayList<Achievement>());
    pw = "+RW1MFx1lm4Vn6mCLc/HTw==";
    salt = "5voLxa4=";

    player = new Player(email, gamesPlayed, name, birthday, points, inv, achievements, pw, salt);
  }

  /**
   * Adds points in normal case.
   */
  @Test
  public void testAddPoints() {
    pai = new PointsItem("Staff of Computer Science", 3);
    player.getInventory().addItem(pai);
    pai.useItem(player, player);
    assertEquals("Incorrect Point Value", player.getPoints().getPoints(), 6);
  }

  /**
   * Adds points in negative case.
   */
  @Test
  public void testSubtractPoints() {
    pai = new PointsItem("Staff of Computer Science", -2);
    player.getInventory().addItem(pai);
    pai.useItem(player, player);
    assertEquals("Incorrect Point Value", player.getPoints().getPoints(), 1);
  }

  /**
   * Adds points in very negative case, so players points end up negative.
   */
  @Test
  public void testSubtractMorePoints() {
    pai = new PointsItem("Staff of Computer Science", -20);
    player.getInventory().addItem(pai);
    pai.useItem(player, player);
    assertEquals("Incorrect Point Value", player.getPoints().getPoints(), -17);
  }

  /**
   * Tests to make sure the item doesn't remain in inventory after use.
   */
  @Test
  public void testRemoveItemFromInventory() {
    pai = new PointsItem("Staff of Computer Science", 3);
    player.getInventory().addItem(pai);
    pai.useItem(player, player);
    boolean hasItem = false;
    for (Item i : inv.getInventory()) {
      if (i == pai) {
        hasItem = true;
      }
    }
    assertEquals("Did not remove the item from inventory", hasItem, false);
  }

}
