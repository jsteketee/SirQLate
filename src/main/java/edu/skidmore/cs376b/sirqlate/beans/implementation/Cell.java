package edu.skidmore.cs376b.sirqlate.beans.implementation;

/**
 * Class Cell is a bean which creates an array of game pieces, then allows you to set and get
 * GamePieces in array set and get methods also made for points, item, and a boolean which says if
 * the points are considered permanent or not.
 * 
 * @author chaegley
 */
public class Cell {

  /**
   * Maximum pieces per cell.
   */
  static final int NUM_PIECES_PER_CELL = 2;

  /**
   * The collection of gamepieces on the cell.
   */
  private GamePiece[] gamePieceSpace;

  /**
   * The cell's item.
   */
  private Item itemInCell;

  /**
   * The cell's points.
   */
  private Points pointCell;

  /**
   * Whether the cell's points are permanent.
   */
  private boolean permPoints;

  /**
   * constructor class for cell.
   */
  public Cell() {
    this.gamePieceSpace = new GamePiece[NUM_PIECES_PER_CELL];
    pointCell = new Points(0);
  }

  /**
   * Get the collection of game pieces on the cell.
   * 
   * @return the array of gamePieces in the cell
   */
  public GamePiece[] getGamePieceSpace() {
    return gamePieceSpace;
  }

  /**
   * Setter for the array of game.
   * 
   * @param pieces Collection of game pieces
   */
  public void setGamePieceSpace(GamePiece[] pieces) {
    this.gamePieceSpace = pieces;
  }

  /**
   * Set the points on the cell.
   * 
   * @param points is a Points that is set to the cell
   */
  public void setPoints(Points points) {

    pointCell = points;
  }

  /**
   * Get the points on the cell.
   * 
   * @return the Points associated with the cell
   */
  public Points getPoints() {

    return pointCell;
  }

  /**
   * Set an item on the cell.
   * 
   * @param item in an Item that is set to the cell
   */
  public void setItem(Item item) {

    itemInCell = item;
  }

  /**
   * Get the item on the cell. This will be null if there is no item.
   * 
   * @return the Item associated with the cell
   */
  public Item getItem() {

    return itemInCell;
  }

  /**
   * Set whether the points on the cell are permanent.
   * 
   * @param isPermanent sets a boolean which designates if the point value is permanent
   */
  public void setPermPoint(boolean isPermanent) {

    permPoints = isPermanent;
  }

  /**
   * Determine whether the points on the cell are permanent.
   * 
   * @return Whether the points are permanent
   */
  public boolean isPermPoint() {

    return permPoints;
  }

  /**
   * Determine whether or not the cell contains an item.
   * 
   * @return True if the cell contains an item
   */
  public boolean hasItem() {
    return (itemInCell != null);
  }

  @Override
  public String toString() {
    // TODO flesh out with more interesting data

    return "Cell: perm points?= " + isPermPoint() + " Point value= " + getPoints().getPoints()
        + " Items?" + hasItem();
  }
}
