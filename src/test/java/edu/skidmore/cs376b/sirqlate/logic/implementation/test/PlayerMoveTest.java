package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.factory.GamificationFactory;
import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;
import edu.skidmore.cs376b.sirqlate.logic.implementation.GameManipulator;
import edu.skidmore.cs376b.sirqlate.logic.implementation.PlayerMove;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.MakeMove;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Date;


/**
 * This is a test class to test the method named makeMove.
 * 
 * @author Tameem Samawi
 * @author Gabe Snider
 */

public class PlayerMoveTest {
  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  Game game;
  PlayerMove turnMaster;
  GameManipulator gameManip;

  /**
   * Sets up necessary data for tests.
   */
  @Before
  public void setup() {
    String[] emailList = {"Bob@skidmore.edu", "Jack@skidmore.edu", "Ann@skidmore.edu"};
    game = LogicFactory.getInstance().startNewGame(emailList);
    turnMaster = new PlayerMove();
    gameManip = new GameManipulator();
  }

  /**
   * Make move out of startCell. Roll is used up. Updated roll is checked, and updated curPlayer is
   * checked.
   */
  @Test
  public void makeMoveTest1() {
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(4);
    turnMaster.makeMove(game, piece, 7);
    assertNotEquals("The game bean was not maintained correctly", game.getRoll(), 0);
    assertNotEquals("The game bean was not maintained correctly", game.getCurrentPlayer(),
        piece.getOwner());
  }

  /**
   * Make move out of startCell. Roll is split between two pieces.
   */
  @Test
  public void makeMoveTest2() {
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(4);
    turnMaster.makeMove(game, piece, 4);
    assertEquals("The game bean was not maintained correctly", game.getRoll(), 3);
    assertEquals("The game bean was not maintained correctly", game.getCurrentPlayer(),
        piece.getOwner());
    assertEquals("The game bean was not maintained correctly",
        game.getGameBoard().getCell(3).getGamePieceSpace()[0], null);
    piece = game.getGameBoard().getCell(3).getGamePieceSpace()[1];
    turnMaster.makeMove(game, piece, 6);
    assertNotEquals("The game bean was not maintained correctly", game.getRoll(), 0);
    assertNotEquals("The game bean was not maintained correctly", game.getCurrentPlayer(),
        piece.getOwner());
  }

  /**
   * First 3 lines ensures reliable game state. Order of players is random, player whose turn it
   * begins with is random Get a piece from the first start cell, set it to that pieces owner's turn
   * Set the roll number manually Once you know whose turn it is, use turn master to make the move
   * Once you do that you can ensure the piece is moved tyo the correct place If the roll is 0 it
   * should be another persons turn understand the diff between tests moving a piece past the last
   * index.
   */
  @Test
  public void makeMoveTest3() {
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(4);
    gameManip.movePiece(game, piece, 34);
    turnMaster.makeMove(game, piece, 1);
    assertEquals("The game bean was not maintained correctly", game.getRoll(), 1);
    assertEquals("The game bean was not maintained correctly", piece,
        game.getGameBoard().getCell(1).getGamePieceSpace()[0]);
  }

  
  /**
   * Move from start cell to regular cell
   * 
   */
  @Test
  public void makeMoveTest4() {
    GamePiece piece = game.getGameBoard().getCell(27).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(4);
    turnMaster.makeMove(game, piece, 31);
    assertEquals("The game bean was not maintained correctly",
        game.getGameBoard().getCell(31).getGamePieceSpace()[0], piece);
  }


  /**
   * Move from HomeCell(2) to a regular cell 10
   */
  @Test
  public void makeMoveTest5() {
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(7);
    turnMaster.makeMove(game, piece, 9);
    assertNotEquals("The game bean was not maintained correctly", game.getRoll(), 0);
    assertNotEquals("The game bean was not maintained correctly", "Ann@skidmore.edu",
        piece.getOwner());
  }

  /**
   * tests a piece not allowed to move backwards to a start cell.
   */
  @Test
  public void makeMoveTest6() {
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(-1);
    gameManip.movePiece(game, piece, 4);
    turnMaster.makeMove(game, piece, 4);
    System.out.println(gameManip.getDistance(game, 4, 4));
    assertNotEquals("The game bean was not maintained correctly", game.getRoll(), 0);
  }

  /**
   * tests a piece that moves past a home and start cell.
   */
  @Test
  public void makeMoveTest7() {
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(8);
    gameManip.movePiece(game, piece, 25);
    turnMaster.makeMove(game, piece, 29);
    System.out.println(gameManip.getDistance(game, 25, 29));
    assertNotEquals("The game bean was not maintained correctly", game.getRoll(), 0);
    assertNotEquals("The game bean was not maintained correctly", "Bob@skidmore.edu",
        piece.getOwner());
  }

}
