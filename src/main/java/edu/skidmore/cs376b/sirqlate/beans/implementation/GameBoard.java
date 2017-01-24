package edu.skidmore.cs376b.sirqlate.beans.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;

/**
 * Gameboard bean containing and setting cells at given index(es).
 * 
 * @author mbanksmo
 *
 */
public class GameBoard {
  /**
   * The number of cells along an edge.
   */
  private static final int EDGE_LENGTH = 10;

  /**
   * The collection of cells on the board.
   */
  private Cell[] cellList;

  /**
   * Create a game board for the supplied number of players. We create EDGE_LENGTH + 2 cells per
   * edge because this includes 2 special cells
   * 
   * 
   * @param players The number of players playing the game
   */
  public GameBoard(int players) {
    cellList = new Cell[players * (EDGE_LENGTH + 2)];
    for (int cellNum = 0; cellNum < players * (EDGE_LENGTH + 2); ++cellNum) {
      setCell(new Cell(), cellNum);
    }
  }

  /**
   * Get the cell associated with the supplied index value.
   * 
   * @returns The cell at the given index
   */
  public Cell getCell(int cellNumber) {
    return cellList[cellNumber];
  }

  /**
   * Sets the cell at the given index.
   * 
   * @param cell The cell
   * @param cellNumber The index of the cell
   */
  public void setCell(Cell cell, int cellNumber) {
    cellList[cellNumber] = cell;
  }

  /**
   * Get the list of cells for the game board.
   * 
   * @returns The collection of cells
   */
  public Cell[] getCellList() {
    return cellList;
  }

  @Override
  public String toString() {
    String toReturn = "";
    for (int i = 0; i < this.getCellList().length; i++) {
      toReturn += "cell" + i + " >  " + this.getCell(i) + "\n";
    }
    return toReturn;
  }
}
