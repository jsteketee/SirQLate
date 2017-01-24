package edu.skidmore.cs376b.sirqlate.gamification.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.factory.GamificationFactory;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Date;

public class ProcessMovesLogicTest {

  /**
   * @author ndonovan.
   */

  /**
   * Tests function of ProcessMovesLogic class.
   */

  private Game testGame;
  private GameBoard gameboard;
  private GamePiece gamePiece;
  private Player[] players;
  private Cell cell;
  private Item item;
  GamificationFactory gamFactory;

  /**
   * sets up.
   */

  @SuppressWarnings("deprecation")
  @Before
  public void setup() {
    gameboard = new GameBoard(3);
    players = new Player[3];
    players[1] = new Player();
    players[1].setBirthday(new Date(1865, 9, 30));
    players[1].setEmail("ndonovan@skidmore.edu");
    gamePiece = new GamePiece(0, Color.RED, players[1], 0);
    testGame = new Game(gameboard, players);
    testGame.setCurrentPlayer(1);
    cell = new Cell();
    item = new Item("box");
    gamFactory = GamificationFactory.getInstance();
  }

  @Test
  public void testGetPoints() {
    cell.setPermPoint(false);
    cell.setPoints(new Points(1));
    gamFactory.processMoves(testGame, cell, gamePiece, 0, true);
    assertEquals("Points still remain on board after being taken", cell.getPoints().getPoints(), 0);
    
    Cell cell2 = new Cell();
    cell2.setPoints(new Points(0));
    assertEquals("Absence of points not properly noted", cell2.getPoints().getPoints(), 0);
  }
  
  @Test
  public void testGetPermPoints() {
    cell.setPermPoint(true);
    Points permPoints = new Points(1);
    cell.setPoints(permPoints);
    gamFactory.processMoves(testGame, cell, gamePiece, 0, true);
    assertEquals("Points get taken off despite being permanent", cell.getPoints(), permPoints);
  }
  
  @Test
  public void testGetItem() {
    cell.setItem(item);
    gamFactory.processMoves(testGame, cell, gamePiece, 0, true);
    assertEquals("Item still remains on board after being taken", cell.getItem(), null);
  }
  
}
