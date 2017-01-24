package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievements;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is for the unit test of Player bean.
 * 
 * @author xzheng
 */
public class PlayerTest {

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the Player for testing.
   */
  private Player player;
  /**
   * Second instance of the Player for testing.
   */
  private Player player2;
  /**
   * Third instance of the Player for testing.
   */
  private Player player3;

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    Date date = new Date();
    player = new Player("xzheng@skidmore.edu", 5, "D", date, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    player2 = new Player("123@skidmore.edu", "A", date, "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    player3 = new Player();
  }

  @Test
  public void getEmail() {
    assertEquals("Incorrect email", "xzheng@skidmore.edu", player.getEmail());
  }

  @Test
  public void getEmail2() {
    assertEquals("Incorrect email", "123@skidmore.edu", player2.getEmail());
  }

  @Test
  public void getEmail3() {
    assertEquals("Incorrect email", null, player3.getEmail());
  }

  @Test
  public void setEmail() {
    player.setEmail("abc@skidmore.edu");
    assertEquals("Incorrect email", "abc@skidmore.edu", player.getEmail());
  }

  @Test
  public void getGamePlayed() {
    assertEquals("Incorrect number of game played", 5, player.getGamePlayed());
  }

  @Test
  public void getGamePlayed2() {
    assertEquals("Incorrect number of game played", 0, player2.getGamePlayed());
  }

  @Test
  public void getGamePlayed3() {
    assertEquals("Incorrect number of game played", 0, player3.getGamePlayed());
  }

  @Test
  public void setGamePlayed() {
    player.setGamePlayed(6);
    assertEquals("Incorrect number of game played", 6, player.getGamePlayed());
  }

  @Test
  public void getName() {
    assertEquals("Incorrect name", "D", player.getName());
  }

  @Test
  public void getName2() {
    assertEquals("Incorrect name", "A", player2.getName());
  }

  @Test
  public void getName3() {
    assertEquals("Incorrect name", null, player3.getName());
  }

  @Test
  public void setName() {
    player.setName("A");
    assertEquals("Incorrect name", "A", player.getName());
  }

  @Test
  public void getBirthday() {
    Date date = new Date();
    assertEquals("Incorrect birthday", date, player.getBirthday());
  }

  @Test
  public void getBirthday2() {
    Date date = new Date();
    assertEquals("Incorrect birthday", date, player2.getBirthday());
  }

  @Test
  public void getBirthday3() {
    assertEquals("Incorrect birthday", null, player3.getBirthday());
  }

  @Test
  public void setBirthday() {
    Date date = new Date();
    player.setBirthday(date);
    assertEquals("Incorrect birthday", date, player.getBirthday());

  }

  @Test
  public void getPoint() {
    assertEquals("Incorrect Point", null, player.getPoints());
  }

  @Test
  public void getPoint2() {
    assertEquals("Incorrect Point", null, player2.getPoints());
  }

  @Test
  public void getPoint3() {
    assertEquals("Incorrect Point", null, player3.getPoints());
  }

  @Test
  public void setPoint() {
    Points point = new Points((long) 6);
    player.setPoints(point);
    assertEquals("Incorrect Point", point, player.getPoints());
  }


  @Test
  public void getInventory() {
    assertEquals("Incorrect Inventory", null, player.getInventory());
  }

  @Test
  public void getInventory2() {
    assertEquals("Incorrect Inventory", null, player2.getInventory());
  }

  @Test
  public void getInventory3() {
    assertEquals("Incorrect Inventory", null, player3.getInventory());
  }

  @Test
  public void setInventory() {
    Item item = new Item("C");
    ArrayList<Item> items = new ArrayList<Item>();
    items.add(item);
    Inventory inventory = new Inventory(items);
    player.setInventory(inventory);
    assertEquals("Incorrect Inventory", inventory, player.getInventory());
  }

  @Test
  public void getAchievements() {
    assertEquals("Incorrect achievements", null, player.getAchievements());
  }

  @Test
  public void getAchievements2() {
    assertEquals("Incorrect achievements", null, player2.getAchievements());
  }

  @Test
  public void getAchievements3() {
    assertEquals("Incorrect achievements", null, player3.getAchievements());
  }

  @Test
  public void setAchievements() {
    ArrayList<Achievement> achieve = new ArrayList<Achievement>();
    Achievements achievements = new Achievements(achieve);
    player.setAchievements(achievements);
    assertEquals("Incorrect achievements", achievements, player.getAchievements());
  }

  @Test
  public void getPasswordHash() {
    assertEquals("Incorrect password", "+RW1MFx1lm4Vn6mCLc/HTw==", player.getPasswordHash());
  }

  @Test
  public void getPasswordHash2() {
    assertEquals("Incorrect password", "+RW1MFx1lm4Vn6mCLc/HTw==", player2.getPasswordHash());
  }

  @Test
  public void getPasswordHash3() {
    assertEquals("Incorrect password", null, player3.getPasswordHash());
  }

  @Test
  public void setPasswordHash() {
    player.setPasswordHash("password2");
    assertEquals("Incorrect password", "password2", player.getPasswordHash());
  }

  @Test
  public void setSalt() {
    player.setSalt("salt");
    assertEquals("Incorrect password", "salt", player.getSalt());
  }

  @Test
  public void hashCodeTest() {
    String playerString = "Player: xzheng@skidmore.edu (D)";
    int playerHash = playerString.hashCode();
    assertEquals("Incorrect hashCode", playerHash, player.hashCode());

  }
}
