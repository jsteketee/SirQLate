package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;

import org.junit.Test;

import java.awt.Color;

/**
 * Test the StartCell class.
 *
 */
public class StartCellTest {
  @Test
  public void testPlayer() {
    Player player = new Player("email", 5, "name", null, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    GamePiece[] gamePiece = new GamePiece[3];
    Color color = new Color(6);

    StartCell cell = new StartCell(player, gamePiece, color);
    assertEquals("HomeCell not storing player", "name", cell.getPlayer().getName());

  }

  @Test
  public void testColor() {
    Player player = new Player("email", 5, "name", null, null, null, null,
        "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=");
    GamePiece[] gamePiece = new GamePiece[3];
    Color color = new Color(6);

    StartCell cell = new StartCell(player, gamePiece, color);
    assertEquals("HomeCell not storing Color", color, cell.getColor());
  }

}
