package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;
import edu.skidmore.cs376b.sirqlate.logic.implementation.GameManipulator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

public class GameManipulatorTest {

  GameManipulator gameManip = new GameManipulator();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  Game game;

  @Before
  public void setup() {
    String[] playerEmailList = {"ann@skidmore.edu", "bob@skidmore.edu", "carly@skidmore.edu"};
    game = LogicFactory.getInstance().startNewGame(playerEmailList);
  }

  @Test
  public void movePieceTest1() {
    gameManip.movePiece(game, game.getGamePieceList().get(0), 5);
    assertEquals("piece not being move to correct position", 5,
        game.getGamePieceList().get(0).getLocation());
  }

  @Test
  public void movePieceTest2() {
    gameManip.movePiece(game, game.getGamePieceList().get(0), 5);
    gameManip.movePiece(game, game.getGamePieceList().get(1), 5);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(startsWith("The destination cell has"));
    gameManip.movePiece(game, game.getGamePieceList().get(2), 5);
  }

  @Test
  public void movePieceTest3() {
    gameManip.movePiece(game, game.getGamePieceList().get(0), 2);
    gameManip.movePiece(game, game.getGamePieceList().get(1), 2);
    gameManip.movePiece(game, game.getGamePieceList().get(2), 2);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(startsWith("The destination cell has"));
    gameManip.movePiece(game, game.getGamePieceList().get(3), 2);
  }

  @Test
  public void movePieceTest4() {
    gameManip.movePiece(game, game.getGamePieceList().get(0), 5);
    gameManip.movePiece(game, game.getGamePieceList().get(1), 5);
    assertEquals("piece not being move to correct position", 5,
        game.getGamePieceList().get(1).getLocation());
  }

  @Test
  public void movePieceTest5() {
    GamePiece piece = game.getGamePieceList().get(0);
    piece.setLocation(9);

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage(startsWith("The given game piece"));
    gameManip.movePiece(game, piece, 2);
  }

  @Test
  public void getHomeCellIdxTest1() {
    game.setCurrentPlayer(0);
    int homeCellIdx = gameManip.getHomeCellIdx(game, game.getCurrentPlayer());
    assertEquals("not correct HomeCell Player1", 2, homeCellIdx);
  }

  @Test
  public void getHomeCellIdxTest2() {
    game.setCurrentPlayer(1);
    int homeCellIdx = gameManip.getHomeCellIdx(game, game.getCurrentPlayer());
    assertEquals("not correct HomeCell Player 2", 14, homeCellIdx);
  }

  @Test
  public void getHomeCellIdxTest3() {
    game.setCurrentPlayer(2);
    int homeCellIdx = gameManip.getHomeCellIdx(game, game.getCurrentPlayer());
    assertEquals("not correct HomeCell Player 3", 26, homeCellIdx);
  }

  @Test
  public void getStartCellIdxTest1() {
    game.setCurrentPlayer(0);
    int startCellIdx = gameManip.getStartCellIdx(game, game.getCurrentPlayer());
    assertEquals("not correct StartCell Player 1", 3, startCellIdx);
  }

  @Test
  public void getStartCellIdxTest2() {
    game.setCurrentPlayer(1);
    int startCellIdx = gameManip.getStartCellIdx(game, game.getCurrentPlayer());
    assertEquals("not correct StartCell Player 2", 15, startCellIdx);
  }

  @Test
  public void getStartCellIdxTest3() {
    game.setCurrentPlayer(2);
    int startCellIdx = gameManip.getStartCellIdx(game, game.getCurrentPlayer());
    assertEquals("not correct StartCell Player 3", 27, startCellIdx);
  }

  @Test
  public void recordPieceMovedTest() {
    game.setCurrentPlayer(0);
    ArrayList<GamePiece> moveablePieces = new ArrayList<GamePiece>();
    moveablePieces.addAll(game.getMoveablePieces());
    gameManip.recordPieceMoved(game, game.getMoveablePieces().get(0));
    assertEquals("wrong movablePieces", 2, game.getMoveablePieces().size());
  }

  @Test
  public void resetMoveAblePieces() {
    game.setCurrentPlayer(0);
    ArrayList<GamePiece> moveablePieces = new ArrayList<GamePiece>();
    moveablePieces.addAll(game.getMoveablePieces());
    gameManip.nextPlayersTurn(game);
    gameManip.resetMoveAblePieces(game);
    assertNotEquals(game.getMoveablePieces(), moveablePieces);
  }

  @Test
  public void getDistancegameManip1() {
    game.setRoll(5);
    int distance = gameManip.getDistance(game, 0, 7);
    assertEquals("number not returned correctly", 5, distance);
  }

  @Test
  public void getDistancegameManip2() {
    game.setRoll(7);
    int distance = gameManip.getDistance(game, 3, 10);
    assertEquals("number not returned correctly", 7, distance);
  }

  @Test
  public void getDistancegameManip3() {
    game.setRoll(7);
    int distance = gameManip.getDistance(game, 29, 0);
    assertEquals("number not returned correctly", 7, distance);
  }

  @Test
  public void getDistancegameManip4() {
    game.setRoll(8);
    int distance = gameManip.getDistance(game, 31, 4);
    assertEquals("number not returned correctly", 7, distance);
  }

  @Test
  public void getDistancegameManip5() {
    game.setRoll(1);
    int distance = gameManip.getDistance(game, 13, 16);
    assertEquals("number not returned correctly", 1, distance);
  }

  @Test
  public void getDistancegameManip6() {
    game.setRoll(8);
    int distance = gameManip.getDistance(game, 12, 22);
    assertEquals("number not returned correctly", 8, distance);
  }

  @Test
  public void getDistancegameManip7() {
    game.setRoll(4);
    int distance = gameManip.getDistance(game, 25, 29);
    assertEquals("number not returned correctly", 2, distance);
  }

  @Test
  public void getDistancegameManip8() {
    game.setRoll(2);
    int distance = gameManip.getDistance(game, 5, 7);
    assertEquals("number not returned correctly", 2, distance);
  }

  @Test
  public void getDistancegameManip9() {
    game.setRoll(5);
    int distance = gameManip.getDistance(game, 16, 22);
    assertEquals("number not returned correctly", 6, distance);
  }

  @Test
  public void getDistancegameManip10() {
    game.setRoll(2);
    int distance = gameManip.getDistance(game, 33, 35);
    assertEquals("number not returned correctly", 2, distance);
  }

  @Test
  public void getDistancegameManip11() {
    game.setRoll(-2);
    int distance = gameManip.getDistance(game, 0, 34);
    assertEquals("number not returned correctly", 2, distance);
  }

  @Test
  public void getDistancegameManip12() {
    game.setRoll(-2);
    int distance = gameManip.getDistance(game, 1, 35);
    assertEquals("number not returned correctly", 2, distance);
  }

  @Test
  public void getDistancegameManip13() {
    game.setRoll(3);
    int distance = gameManip.getDistance(game, 3, 6);
    assertEquals("number not returned correctly", 3, distance);
  }

  @Test
  public void getDistancegameManip14() {
    game.setRoll(4);
    int distance = gameManip.getDistance(game, 15, 20);
    assertEquals("number not returned correctly", 5, distance);
  }

  @Test
  public void nextPlayersTurnTest() {
    game.setCurrentPlayer(1);
    gameManip.nextPlayersTurn(game);
    assertEquals("wrong current player", game.getPlayers()[2], game.getCurrentPlayer());
  }

  @Test
  public void nextPlayersTurnTest2() {
    game.setCurrentPlayer(2);
    gameManip.nextPlayersTurn(game);
    assertEquals("wrong current player", game.getPlayers()[0], game.getCurrentPlayer());
  }


  @Test
  public void checkWinConditionFalse() {
    assertEquals("wrong end condition", false, gameManip.checkWinCondition(game));
  }


  @Test
  public void checkWinConditionTrue() {
    gameManip.movePiece(game, game.getGamePieceList().get(3), 2);
    gameManip.movePiece(game, game.getGamePieceList().get(4), 2);
    gameManip.movePiece(game, game.getGamePieceList().get(5), 2);
    assertEquals("wrong end condition", true, gameManip.checkWinCondition(game));
  }

  @Test
  public void checkWinConditionFalse2() {
    gameManip.movePiece(game, game.getGamePieceList().get(3), 2);
    gameManip.movePiece(game, game.getGamePieceList().get(4), 16);
    gameManip.movePiece(game, game.getGamePieceList().get(5), 2);
    assertEquals("wrong end condition", false, gameManip.checkWinCondition(game));
  }

  @Test
  public void forcedMoveTest1() {
    game.setCurrentPlayer(0);
    game.setRoll(-2);
    assertEquals("The return value is incorrect for this game state", true,
        gameManip.isForcedMove(game));
  }

  @Test
  public void forcedMoveTest2() {
    game.setCurrentPlayer(0);
    game.setRoll(3);
    assertEquals("The return value is incorrect for this game state", false,
        gameManip.isForcedMove(game));
  }

  @Test
  public void forcedMoveTest3() {
    game.setCurrentPlayer(game.getGameBoard().getCell(3).getGamePieceSpace()[0].getOwner());
    game.setRoll(-2);
    gameManip.movePiece(game, game.getGameBoard().getCell(3).getGamePieceSpace()[0], 2);
    gameManip.movePiece(game, game.getGameBoard().getCell(3).getGamePieceSpace()[1], 2);
    assertEquals("The return value is incorrect for this game state", false,
        gameManip.isForcedMove(game));
  }

  @Test
  public void forcedMoveTest4() {
    game.setCurrentPlayer(game.getGameBoard().getCell(3).getGamePieceSpace()[0].getOwner());
    game.setRoll(2);
    gameManip.movePiece(game, game.getGameBoard().getCell(3).getGamePieceSpace()[0], 2);
    gameManip.movePiece(game, game.getGameBoard().getCell(3).getGamePieceSpace()[1], 2);
    assertEquals("The return value is incorrect for this game state", true,
        gameManip.isForcedMove(game));
  }
}
