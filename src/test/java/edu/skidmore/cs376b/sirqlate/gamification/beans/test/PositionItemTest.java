package edu.skidmore.cs376b.sirqlate.gamification.beans.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievements;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.beans.PositionItem;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;


public class PositionItemTest {

  /**
   * Tests the Position item bean.
   * 
   * @author ndonovan
   */

  PositionItem pai;
  String email1;
  String email2;
  String email3;
  Date birthday;
  Points points;
  Achievements achievements;
  Game game;
  GameBoard board;
  Player[] players;
  Player player1;
  Player player2;
  Player player3;
  Inventory inv;
  GamePiece gamePiece;
  int boardSize;

  /**
   * Sets up and initializes values to be used by item.
   */
  @SuppressWarnings("deprecation")
  @Before
  public void setup() {
    email1 = "dread1@skidmore.edu";
    email2 = "dread2@skidmore.edu";
    email3 = "dread3@skidmore.edu";
    inv = new Inventory(new ArrayList<Item>());
    birthday = new Date(2003, 8, 8);
    points = new Points(3);
    achievements = new Achievements(new ArrayList<Achievement>());

    player1 = new Player(email1, 0, "Davey1", birthday, points, inv, achievements,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    player2 = new Player(email2, 0, "Davey2", birthday, points, inv, achievements, 
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    player3 = new Player(email3, 0, "Davey3", birthday, points, inv, achievements, 
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    players = new Player[3];
    players[0] = player1;
    players[1] = player2;
    players[2] = player3;

    gamePiece = new GamePiece(1, Color.RED, player1, 0);

    board = new GameBoard(3);

    game = new Game(board, players);

    boardSize = game.getGameBoard().getCellList().length;

  }

  /**
   * Tests using the item in a normal case.
   */
  @Test
  public void testMovePlayer() {
    int move = 2;
    pai = new PositionItem("Staff of Computer Science", move);
    player1.getInventory().addItem(pai);
    assertEquals("Incorrect cell", pai.useItem(player1, game, gamePiece),
        game.getGameBoard().getCell(2));
  }

  /**
   * Tests using the item past the size of the board.
   */
  @Test
  public void testMovePlayerPastBoardIndex() {
    int move = 2 + game.getGameBoard().getCellList().length;
    pai = new PositionItem("Staff of Computer Science", move);
    player1.getInventory().addItem(pai);
    assertEquals("Incorrect cell", pai.useItem(player1, game, gamePiece),
        game.getGameBoard().getCell(2));
  }

  /**
   * Tests using the item past the size of the board but backwards.
   */
  @Test
  public void testMovePlayerBackwardsPastIndex() {
    int move = -(game.getGameBoard().getCellList().length + 2);
    pai = new PositionItem("Staff of Computer Science", move);
    player1.getInventory().addItem(pai);
    int newIndex = game.getGameBoard().getCellList().length - 2;


    assertEquals("Incorrect cell", pai.useItem(player1, game, gamePiece),
        game.getGameBoard().getCell(newIndex));
  }

  /**
   * Tests to make sure the item doesn't remain in inventory after use.
   */
  @Test
  public void testRemoveItemFromInventory() {
    pai = new PositionItem("Staff of Computer Science", 3);
    player1.getInventory().addItem(pai);
    pai.useItem(player1, game, gamePiece);
    boolean hasItem = false;
    for (Item i : inv.getInventory()) {
      if (i == pai) {
        hasItem = true;
      }
    }
    assertEquals("Did not remove the item from inventory", hasItem, false);
  }

}
