package edu.skidmore.cs376b.sirqlate.gamification.factory.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.factory.GamificationFactory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;


/**
 * Unit tests for GamificationFactory.
 * 
 * @author gjin
 */

public class GamificationFactoryTest {
  /**
   * An instance of the generator for testing.
   */
  private GamificationFactory instance;
  private Game game;
  private Cell cell;
  private Points point;
  private Item item;
  private Player player;
  private Player player1;
  private Player player2;
  private GameBoard board;
  private GamePiece piece;

  /**
   * setup.
   */
  @Before
  public void setup() {
    instance = GamificationFactory.getInstance();
    Date bday = new Date();
    Points playerpoint = new Points(0);
    Points playerpoint1 = new Points(5);
    Points playerpoint2 = new Points(20);
    ArrayList<Item> ilist = new ArrayList<Item>();
    Inventory inventory = new Inventory(ilist);
    player = new Player("123@123.com", 3, "l", bday, playerpoint, inventory, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    player1 = new Player("123@123.com", 3, "l1", bday, playerpoint1, inventory, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    player2 = new Player("123@123.com", 3, "l2", bday, playerpoint2, inventory, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    piece = new GamePiece(0, null, player, 4);
    Player[] plist = {player,player1, player2};
    board = new GameBoard(3);
    game = new Game(board, plist);
  }
  
  /**
   * Unit test of ProcessMoves No points, and item "" on the cell. 
   */
  @Test
  public void testProcessMoves() {
    point = new Points(0);
    item = new Item("");
    cell = new Cell();
    cell.setPoints(point);
    cell.setItem(item);
    board.setCell(cell, 2);
    game.setCurrentPlayer(0);
    game.setRoll(1);
    instance.processMoves(game, cell, piece, 2, true);
    assertEquals("Incorrect value for points for the player", -5,
        game.getCurrentPlayer().getPoints().getPoints());

    assertEquals("The player got the wrong item", "",
        game.getCurrentPlayer().getInventory().getInventory().get(0).getName());
  }

  /**
   * Unit test of ProcessMoves 12 points, and item "i1" on the cell. 
   */
  @Test
  public void testProcessMoves1() {
    long l1 = 12;
    point = new Points(l1);
    item = new Item("i1");
    // item = null;
    cell = new Cell();
    cell.setPoints(point);
    cell.setItem(item);
    board.setCell(cell, 4);
    player.setPoints(point);
    // player.setInventory(null);
    game.setCurrentPlayer(0);
    //game.setRoll(7);
    
    instance.processMoves(game, cell, piece, 6, true);
    assertEquals("Incorrect value of points for the player", 19, 
        game.getCurrentPlayer().getPoints().getPoints());
    assertEquals("The player should not get item", "i1",
        game.getCurrentPlayer().getInventory().getInventory().get(0).getName());
    // assertEquals("The player should not get item",null,
    // game.getCurrentPlayer().getInventory().getInventory());
    System.out.println("Player at " + piece.getLocation());
  }
  
  /**
   * Unit test of ProcessMoves 30 points, and item "pear" on the cell. Passed home -5 points.
   */
  @Test
  public void testProcessMoves2() {
    point = new Points(30);
    item = new Item("pear");
    cell = new Cell();
    cell.setPoints(point);
    cell.setItem(item);
    board.setCell(cell, 22);
    game.setCurrentPlayer(1);
    piece.setLocation(14);
    //System.out.println("player point:"+player1.getPoints().getPoints());
    //game.setRoll(1);
    instance.processMoves(game, cell, piece, 8, true);
    assertEquals("Incorrect value for points for the player", 35,
        game.getCurrentPlayer().getPoints().getPoints());

    assertEquals("The player got the wrong item", "pear",
        game.getCurrentPlayer().getInventory().getInventory().get(0).getName());
  }
  
  /**
   * Unit test of ProcessMoves 30 points, and item "pea" on the cell. Did pass home.
   */
  @Test
  public void testProcessMoves3() {
    point = new Points(30);
    item = new Item("pea");
    cell = new Cell();
    cell.setPoints(point);
    cell.setItem(item);
    board.setCell(cell, 30);
    game.setCurrentPlayer(1);
    piece.setLocation(22);
    //System.out.println("player point:"+player1.getPoints().getPoints());
    //game.setRoll(1);
    instance.processMoves(game, cell, piece, 8, true);
    assertEquals("Incorrect value for points for the player", 30,
        game.getCurrentPlayer().getPoints().getPoints());

    assertEquals("The player got the wrong item", "pea",
        game.getCurrentPlayer().getInventory().getInventory().get(0).getName());
  }


  /**
   * Unit test of PopulateGameBoard.
   * 
   */
  @Test
  public void testPopulateGameBoard() {
    instance.populateGameBoard(board);
    System.out.println("board size: " + board.getCellList().length);
    for (int i = 0; i < board.getCellList().length; i++) {
      System.out.println("board Info: " + board.getCell(i));
    }
  }



}
