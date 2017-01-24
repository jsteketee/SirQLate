package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.Color;
import java.util.Date;

/**
 * This class is for the unit test of the GamePiece bean.
 * 
 * @author cenglish
 */
public class GamePieceTest {

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private GamePiece gamePiece;

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    Date date = new Date();
    Player player = new Player("cli@skidmore.edu", 5, "D", date, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    gamePiece = new GamePiece(5, Color.black, player, 4);
  }

  @Test
  public void getColor() {
    assertEquals("Incorrect color", Color.black, gamePiece.getColor());
  }

  @Test
  public void getNumber() {
    assertEquals("Incorrect number", 5, gamePiece.getNumber());
  }

  @Test
  public void getOwner() {
    assertEquals("Incorrect number", "cli@skidmore.edu", gamePiece.getOwner().getEmail());
  }

  @Test
  public void setNumber() {
    gamePiece.setNumber(8);
    assertEquals("Incorrect number setter", 8, gamePiece.getNumber());
  }

  @Test
  public void setColor() {
    gamePiece.setColor(Color.blue);
    assertEquals("Incorrect color setter", Color.blue, gamePiece.getColor());
  }

  @Test
  public void setOwner() {
    Date date = new Date();
    Player player = new Player("cenglish@skidmore.edu", 5, "D", date, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    gamePiece.setOwner(player);
    assertEquals("Incorrect owner setter", "cenglish@skidmore.edu",
        gamePiece.getOwner().getEmail());
  }
}
