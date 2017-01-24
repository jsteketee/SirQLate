package edu.skidmore.cs376b.sirqlate.logic.factory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;
import edu.skidmore.cs376b.sirqlate.logic.implementation.MoveChoices;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.Color;
import java.util.ArrayList;


/**
 * This class is for the unit test of logic factory.
 * 
 * @author yliu
 */

public class LogicFactoryTest {



  /**
   * Helper function to test if roll was in valid range.
   * 
   * @param roll number generated
   * @return boolean true if roll was in valid range, false otherwise
   */
  private boolean checkInRange(int roll) {
    if (roll >= -2 && roll != 0 && roll <= 8) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Helper function to test if player was in the game.
   * 
   * @param players list
   * @param email address
   * @return boolean true if email was in the player list, false otherwise
   */
  private boolean checkIfExist(Player[] players, String email) {
    boolean exist = false;
    for (int i = 0; i < players.length; i++) {
      if (players[i].getEmail().equals(email)) {
        exist = true;
        break;
      }
    }
    return exist;
  }

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */

  private static LogicFactory instance;

  /**
   * set up the environment.
   */
  @Before
  public void setup() {
    instance = LogicFactory.getInstance();

  }

  
  @Test
  public void rollNumberTest() {
    int rollNumber = instance.rollNumber();
    assertEquals("number not returned correctly", true, checkInRange(rollNumber));
  }

  @Test
  public void startNewGameTest1() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    assertEquals("game not updated correctly", game.getGameBoard().getCellList().length,
        new GameBoard(3).getCellList().length);

  }

  @Test
  public void startNewGameTest2() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    assertEquals("game not updated correctly", true,
        checkIfExist(game.getPlayers(), "ann@skidmore.edu"));

  }

  @Test
  public void startNewGameTest3() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    assertEquals("game not updated correctly", false,
        checkIfExist(game.getPlayers(), "mxn@gmail.com"));

  }

  @Test
  public void userPasswordMatchedTest1() {
    Player player = new Player("123@skidmore.edu", 3, null, null, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    PersistenceFactory.getInstance().persistPlayer(player);
    boolean match = instance.userPasswordMatched("123@skidmore.edu", "password");
    assertEquals("password not matched correctly", true, match);

  }

  @Test
  public void userPasswordMatchedTest2() {
    Player player = new Player("123@skidmore.edu", 3, null, null, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    PersistenceFactory.getInstance().persistPlayer(player);
    boolean match = instance.userPasswordMatched("123@skidmore.edu", "1234");
    assertEquals("password not matched correctly", false, match);

  }

  @Test
  public void signUpNeededTest1() {
    boolean match = instance.signUpNeeded("123@gmail.com");
    assertEquals("sign up state not retrieved correctly", true, match);

  }

  @Test
  public void signUpNeededTest2() {
    boolean match = instance.signUpNeeded("ann@skidmore.edu");
    assertEquals("sign up state not retrieved correctly", false, match);

  }

  @Test
  public void makeMoveTest1() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    GamePiece piece = game.getGameBoard().getCell(3).getGamePieceSpace()[0];
    game.setCurrentPlayer(piece.getOwner());
    game.setRoll(4);
    instance.makeMove(piece, 7);
    assertNotEquals("The game bean was not maintained correctly", game.getRoll(), 0);
    assertNotEquals("The game bean was not maintained correctly", game.getCurrentPlayer(),
        piece.getOwner());
  }

 
  @Test
  public void makeMoveTest3() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    ArrayList<GamePiece> piecelist = game.getGamePieceList();
    int[] locationbmove = new int[piecelist.size()];
    int[] locationamove = new int[piecelist.size()];
    for (int i = 0; i < piecelist.size(); i++) {
      locationbmove[i] = piecelist.get(i).getLocation();
    }
    instance.makeMove();
    for (int i = 0; i < piecelist.size(); i++) {
      locationamove[i] = piecelist.get(i).getLocation();
    }
    for (int i = 0; i < piecelist.size(); i++) {
      assertEquals("Incorrect move made", locationbmove[i],locationamove[i]);
    }
  }
  
  @Test
  public void possibleMoveTest1() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    Player[] list = game.getPlayers();
    game.setCurrentPlayer(list[0]);
    game.setRoll(8);
    int[][] choices = instance.possibleMove(3);

    assertEquals("standard move forward failed for position", 4, choices[0][0]);
    assertEquals("standard move forward failed for status of move", 0, choices[0][1]);

  }

  @Test
  public void possibleMoveTest2() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    Player[] list = game.getPlayers();
    game.setCurrentPlayer(list[0]);
    GamePiece piece = new GamePiece(1, Color.black, list[0], 3);
    game.setRoll(8);
    int[][] choices = instance.possibleMove(piece);
    assertEquals("standard move forward failed for position", 4, choices[0][0]);
    assertEquals("standard move forward failed for status of move", 0, choices[0][1]);

  }
  
  @Test
  public void getMovablePiecesTest1() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    game.setCurrentPlayer(game.getPlayers()[0]);
    game.setRoll(1);
    ArrayList<GamePiece> movablePieces = instance.getMovablePieces();
    assertEquals("movable pieces not retrieved correctly", game.getMoveablePieces().size(),
        movablePieces.size());
  }

  @Test
  public void getMovablePiecesTest2() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    game.setCurrentPlayer(game.getPlayers()[0]);
    for (int index = 0; index < game.getPlayers().length; index++) {
      game.getGamePieceList().get(index).setLocation(2);
      game.getGamePieceList().get(index + 3).setLocation(14);
      game.getGamePieceList().get(index + 6).setLocation(26);
    }
    ArrayList<GamePiece> movablePieces = instance.getMovablePieces();
    assertEquals("movable pieces not retrieved correctly", 0,
        movablePieces.size());
  }
  
  @Test
  public void getMovablePiecesTest3() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    Game game = instance.startNewGame(playerEmailList);
    game.setRoll(-2);
    ArrayList<GamePiece> movablePieces = instance.getMovablePieces();
    assertEquals("movable pieces not retrieved correctly", 0,
        movablePieces.size());
  }
  
  
 



}
