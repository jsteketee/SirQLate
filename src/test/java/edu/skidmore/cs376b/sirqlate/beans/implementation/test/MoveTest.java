package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Move;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test the move class.
 *
 */
public class MoveTest {
  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  /**
   * An instance of the bean for testing.
   */
  private Move move;

  @Before
  public void setup() {
    move = new Move(1, "gameId", null, null, 1, 2, null, false, false, false, 5, null);
  }

  @Test
  public void setAndGetForTurn() {
    move.setTurn(3);
    assertEquals("Incorrect turn", 3, move.getTurn());
  }

  @Test
  public void setAndGetForGameId() {
    move.setGameId("abc");
    assertEquals("Incorrect game id", "abc", move.getGameId());
  }

  @Test
  public void setAndGetForPiece() {
    GamePiece piece = new GamePiece(1, null, null, 1);
    move.setPiece(piece);
    assertEquals("Incorrect piece", piece, move.getPiece());
  }

  @Test
  public void setAndGetForPlayer() {
    Player player = new Player("a@b.c", "A", null, "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    move.setPlayer(player);
    assertEquals("Incorrect player", player, move.getPlayer());
  }

  @Test
  public void setAndGetForStartPos() {
    move.setStartPos(5);
    assertEquals("Incorrect start position", 5, move.getStartPos());
  }

  @Test
  public void setAndGetForEndPos() {
    move.setEndPos(5);
    assertEquals("Incorrect end position", 5, move.getEndPos());
  }

  @Test
  public void setAndGetForPointsRewarded() {
    Points point = new Points(10);
    move.setPointsRewarded(point);
    assertEquals("Incorrect point rewarded", point, move.getPointsRewarded());
  }

  @Test
  public void setAndGetForFight() {
    move.setFight(true);
    assertEquals("Incorrect fight", true, move.isFight());
  }

  @Test
  public void setAndGetForWin() {
    move.setWin(true);
    assertEquals("Incorrect win", true, move.isWin());
  }

  @Test
  public void setAndGetForSplit() {
    move.setSplit(true);
    assertEquals("Incorrect split", true, move.isSplit());
  }

  @Test
  public void setAndGetForRoll() {
    move.setRoll(20);
    assertEquals("Incorrect roll", 20, move.getRoll());
  }

  @Test
  public void setAndGetForItemGained() {
    Item item = new Item("A");
    move.setItemGained(item);
    assertEquals("Incorrect item gained", item, move.getItemGained());
  }
}
