package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.awt.Color;

/**
 * Class HomeCell is a bean which extends Cell. Differences are number of pieces in array, the
 * ability to get and set the new space, and the ability to get and set a player for ownership of
 * the cell
 * 
 * @author chaegley
 *
 */
public class HomeCell extends Cell {

  /**
   * The number of pieces a player has.
   */
  private static final int NUM_PIECES_PER_HOME_CELL = 3;

  /**
   * The owner of this cell.
   */
  private Player owner;

  /**
   * The color of this cell.
   */
  private Color cellColor;

  /**
   * Constructor for HomeCell.
   * 
   * @param player The player who owns the cell
   * @param color The color of the cell
   */
  public HomeCell(Player player, Color color) {
    this.setGamePieceSpace(new GamePiece[NUM_PIECES_PER_HOME_CELL]);
    setPlayer(player);
    setColor(color);
  }

  /**
   * Set the player who owns the cell.
   * 
   * @param player A Player which is set to the HomeCell
   */
  public void setPlayer(Player player) {
    owner = player;
  }

  /**
   * Get the player who owns the cell.
   * 
   * @return The Player associated with the HomeCell
   */
  public Player getPlayer() {
    return owner;
  }

  /**
   * Set the color of the cell.
   * 
   * @param color Is a Color which is set to the HomeCell
   */
  public void setColor(Color color) {
    cellColor = color;
  }

  /**
   * Get the color of the cell.
   * 
   * @return The Color associated with the HomeCell
   */
  public Color getColor() {
    return cellColor;
  }

  @Override
  public String toString() {
    return "HomeCell: perm points?= " + isPermPoint() + " Point value= " + getPoints().getPoints()
        + " Owner= " + this.getPlayer() + " Color: " + this.getColor();
  }
}
