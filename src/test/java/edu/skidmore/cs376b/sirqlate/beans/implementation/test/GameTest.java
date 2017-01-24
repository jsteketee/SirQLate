package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;



public class GameTest {

  private Game game;
  private GameBoard gamebordt;
  private Player[] players;
  private ArrayList<GamePiece> pieceList;
  private static final int NUM_PLAYERS = 2;

  /**
   * Setup state for tests.
   */
  @Before
  public void init() {
    players = new Player[NUM_PLAYERS];

    for (int i = 0; i < NUM_PLAYERS; i++) {
      players[i] = new Player();
    }

    gamebordt = new GameBoard(NUM_PLAYERS);
    game = new Game(gamebordt, players);
  }

  @Test
  public void testGetGameBoard() {
    assertEquals("GameBoard object inconsistency", gamebordt, game.getGameBoard());
  }

  @Test
  public void testSetGameBoard() {
    GameBoard gb1 = new GameBoard(NUM_PLAYERS + 1);
    game.setGameBoard(gb1);
    assertEquals("GameBoard set inconsistency", gb1, game.getGameBoard());
  }

  @Test
  public void testSetPlayers() {
    Player[] p2 = new Player[NUM_PLAYERS + 1];
    game.setPlayers(p2);
    assertArrayEquals("Player set inconsistency", p2, game.getPlayers());
  }

  @Test
  public void testGetPlayers() {
    assertArrayEquals("Player list inconsistency", players, game.getPlayers());
  }

  @Test
  public void testGetRoll() {
    assertEquals("Roll get inconsistency", 0, game.getRoll());
  }

  @Test
  public void testSetRoll() {
    // Check for max bounds
    game.setRoll(game.getMaxRoll() + 1);
    assertEquals("Roll set inconsistency", 0, game.getRoll());

    // Check for min bounds
    game.setRoll(game.getMinRoll() - 1);
    assertEquals("Roll set inconsistency", 0, game.getRoll());

    // Check for valid input
    game.setRoll(game.getMaxRoll());
    assertEquals("Roll set inconsistency", game.getMaxRoll(), game.getRoll());

    // Roll cannot be zero
    game.setRoll(0);
    assertEquals("Roll set inconsistency", 0, game.getRoll());
  }

  @Test
  public void getMovablePiecesTest() {
    game.setGamePieceList(pieceList);
    assertEquals("GamePiece ArrayList inconsistency", pieceList, game.getGamePieceList());
  }

  @Test
  public void getTurnNumTest() {
    game.setTurnNum(1);
    assertEquals("Turn Number inconsistency", 1, game.getTurnNum());
  }

  @Test
  public void getWinnerTest() {
    Player winner = new Player();
    game.setWinner(winner);
    assertEquals("Winner inconsistency", winner, game.getWinner());
  }
}
