package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.logic.implementation.GameManipulator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Unit tests for recordPieceMoved and resetMovablePieces in GameManipulator.
 * 
 * @author gjin
 */
public class RecordPieceMovedandResetMovablePieceTest {
  
  private Game game;
  private GameBoard board;
  private Player player1;
  private Player player2;
  private Player player3;
  private GamePiece piece1;
  private GamePiece piece2;
  private GamePiece piece3;
  private GamePiece piece4;
  private ArrayList<GamePiece> gamepiecelist = new ArrayList<GamePiece>();
  private GameManipulator gamemanip;
  
  
  /**
   * setup.
   */
  @Before
  public void setup() {
    player1 = new Player(null,"lin",null,null,null);
    player2 = new Player(null,"tom",null,null,null);
    player3 = new Player(null,"ada",null,null,null);
    Player[] playerlist = {player1, player2, player3};
    board = new GameBoard(3);
    game = new Game(board, playerlist);
    piece1 = new GamePiece(0, null, player1, 0);
    piece2 = new GamePiece(0, null, player2, 3);
    piece3 = new GamePiece(0, null, player3, 5);
    piece4 = new GamePiece(0, null, player1, 8);
    gamepiecelist.add(piece1);
    gamepiecelist.add(piece2);
    gamepiecelist.add(piece3);
    gamepiecelist.add(piece4);
    game.setGamePieceList(gamepiecelist);
    game.setCurrentPlayer(0);
    gamemanip = new GameManipulator();
  }
  
  /**
   * Unit test of recordPieceMoved recorded.
   */
  @Test
  public void testRecordPieceMovedY() {
    gamemanip.resetMoveAblePieces(game);
    //System.out.println("1. " + game.getMoveablePieces());
    gamemanip.recordPieceMoved(game, piece1);
    //System.out.println("1. " + game.getMoveablePieces());
    assertNotEquals("Did not record piece moved.", game.getMoveablePieces().get(0), piece1);   
  }
  
  /**
   * Unit test of recordPieceMoved not recorded.
   */
  @Test
  public void testRecordPieceMovedN() {
    gamemanip.resetMoveAblePieces(game);
    //System.out.println("1. " + game.getMoveablePieces());
    gamemanip.recordPieceMoved(game, piece2);
    //System.out.println("1. " + game.getMoveablePieces());
    assertEquals("Did not record piece moved.", game.getMoveablePieces().get(0), piece1);    
  }
  
  /**
   * Unit test of resetMovablePieces reset.
   */
  @Test
  public void testResetMovablePiecesY() {
    gamemanip.resetMoveAblePieces(game);
    assertEquals("Did not reset movable pieces.", game.getMoveablePieces().size(), 2); 
    assertNotEquals("Did not reset movable pieces.", game.getMoveablePieces().size(), 1);
  }
  
  /**
   * Unit test of resetMovablePieces not reset.
   */
  @Test
  public void testResetMovablePiecesN() {
    game.setCurrentPlayer(1);
    gamemanip.resetMoveAblePieces(game);
    assertEquals("Did not reset movable pieces.", game.getMoveablePieces().size(), 1);
    assertNotEquals("Did not reset movable pieces.", game.getMoveablePieces().size(), 2);    
  }
  
  

}
