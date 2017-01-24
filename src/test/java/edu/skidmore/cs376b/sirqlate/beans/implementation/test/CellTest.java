package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;

import org.junit.Test;

/**
 *
 * @author chaegley.
 * 
 *         test class written for cell class
 *
 */
public class CellTest {


  /*
   * @Test public void testCell() { Cell cell = new Cell(); assertEquals(
   * "Cell not implementing array", cell.getFirstSpace() == null && cell.getSecondSpace() == null);
   * }
   */
  /*
   * @Test public void testSetSpace() { Cell cell = new Cell(); GamePiece gp1 = new GamePiece(76,
   * null, null, 0); GamePiece gp2 = new GamePiece(14, null, null, 0); GamePiece[] gArray = new
   * GamePiece[2]; cell.setGamePieceSpace(gArray);
   * 
   * assertEquals("GamePiece not set in cell", cell.getFirstSpace() == gp1 && cell.getSecondSpace()
   * == gp2); }
   */


  @Test
  public void testSetPoints() {
    Cell cell = new Cell();
    Points points = new Points(7);
    points.setPoints(9238);
    cell.setPoints(points);
    assertEquals("points unable to be implemented", 9238, cell.getPoints().getPoints());
  }


  @Test
  public void testItem() {
    Cell cell = new Cell();
    Item item = new Item("Name");
    cell.setItem(item);
    assertEquals("item unable to be implemented", "Name", cell.getItem().getName());
  }



  @Test
  public void testPointsPerm() {
    Cell cell = new Cell();
    cell.setPermPoint(true);
    assertEquals("boolean not setting correctly for true", true, cell.isPermPoint());
    cell.setPermPoint(false);
    assertEquals("boolean not setting correctly for false", false, cell.isPermPoint());
  }

}
