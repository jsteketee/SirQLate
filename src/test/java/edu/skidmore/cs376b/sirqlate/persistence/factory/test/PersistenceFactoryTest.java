package edu.skidmore.cs376b.sirqlate.persistence.factory.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievements;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This is the unit test for PersistenceFactory.
 * 
 * @author clin
 *
 */
public class PersistenceFactoryTest {
  private static final Logger LOG = Logger.getLogger(PersistenceFactoryTest.class);

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Date date1;
  private Date date2;
  private Points point;
  private Points point2;
  private Inventory inventory;
  private Player player1;
  private Player player2;
  private Game game;
  private GameBoard gameboard;
  private PersistenceFactory persistenceFactory;

  /**
   * Set up.
   */
  @Before
  public void setup() {
    persistenceFactory = PersistenceFactory.getInstance();
    SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
    String dateStr = "20161114";
    String dateStr2 = "19910101";
    try {
      date1 = ft.parse(dateStr);
      date2 = ft.parse(dateStr2);
    } catch (ParseException pe) {
      System.out.println("ERROR: Cannot parse \"" + dateStr + "\"");
    }
    point = new Points((long) 5);
    point2 = new Points((long) 101);
    ArrayList<Item> items = new ArrayList<Item>();
    items.add(new Item("One"));
    inventory = new Inventory(items);
    ArrayList<Achievement> achievement = new ArrayList<Achievement>();
    achievement.add(new Achievement("a@b.c", "A"));
    Achievements achievements = new Achievements(achievement);
    // Creates a player with the hashed password of "password"
    player1 = new Player("a@b.c", 5, "A", date1, point, inventory, achievements,
        "0", "0");
    player2 = new Player("ann@skidmore.edu", 99, "Ann", date2, point2, inventory, achievements,
            "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    Player[] playerList = new Player[]{player1};
    game = new Game(gameboard, playerList);
    
  }

  @Test
  public void persistAndRetrievePlayer() {
    PersistenceFactory.getInstance().persistPlayer(player1);
    PersistenceFactory.getInstance().persistPlayer(player2);
    assertEquals("Incorrect player", player1,
        PersistenceFactory.getInstance().retrievePlayer(player1.getEmail()));
    
  }

  @Test
  public void retrieveItemPool() {
    Item[] itemList = new Item[] {new Item("Acorn"), new Item("Apple"), new Item("Avocado"), 
        new Item("Banana"), new Item("Banana2"), new Item("Bread"),
        new Item("Carrots"), new Item("Cherries"), new Item("Cherry"), 
        new Item("Corn"), new Item("Grapes-green"), new Item("Grapes-purple"), new Item("Kiwi"), 
        new Item("Mushroom"), new Item("Onion"), new Item("Orange"), new Item("Orange2"), 
        new Item("Pepper"), new Item("Strawberry"), new Item("Watermelon")};
    assertArrayEquals("Items do not match", itemList, persistenceFactory.retrieveItemPool());

  }
  
  @Test
  public void persistAndRetrieveGameState() {
    PersistenceFactory.getInstance().persistGameState(game);
    assertEquals(null, PersistenceFactory.getInstance().retrieveGameState(player1));
  }
  
  @Test 
  public void getPlayerList() {
    ArrayList<Player> players = PersistenceFactory.getInstance().getPlayerList();
    for (Player x : players) {
      LOG.debug("ponts and then games played : " 
          + x.getPoints().getPoints() + ", " + x.getGamePlayed());
    }
  }
}
