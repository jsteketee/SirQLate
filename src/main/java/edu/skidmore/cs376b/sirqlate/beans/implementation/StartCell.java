package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.awt.Color;

/**
 * Class StartCell is a bean which extends Cell. Differences are number of pieces in array, the
 * ability to get and set the new space, and the ability to get and set a player for ownership of
 * the cell
 * 
 * @author chaegley
 *
 */
public class StartCell extends Cell {

  /**
   * Number of pieces a player has.
   */
  private static final int NUM_PIECES_PER_START_CELL = 3;

  /**
   * The owner of the cell.
   */
  private Player owner;

  /**
   * The color of the cell.
   */
  private Color cellColor;

  /**
   * Constructor for StartCell which takes in a player and an array of GamePieces
   * 
   * @param player the player which owns the StartCell
   * @param gamePieces the array of GamePieces which will be initiated into the cell. If the array
   *        is of an incorrect size, (not NUM_PIECES_PER_START_CELL) then it will store an empty
   *        array
   * @param color The color of the cell
   */

  public StartCell(Player player, GamePiece[] gamePieces, Color color) {
    if (gamePieces.length == NUM_PIECES_PER_START_CELL) {
      setGamePieceSpace(gamePieces);
    } else {
      throw new IllegalArgumentException("Wrong number of gamepieces");
    }

    setPlayer(player);
    setColor(color);
  }

  /**
   * Constructor for StartCell.
   * 
   * @param player is the player that owns the start cell
   * @param color is the color of the start cell
   */
  public StartCell(Player player, Color color) {
    setPlayer(player);
    setColor(color);
    setGamePieceSpace(new GamePiece[NUM_PIECES_PER_START_CELL]);
  }

  /**
   * Set the player who owns the cell.
   * 
   * @param player A Player which is set to the StartCell
   */
  public void setPlayer(Player player) {
    owner = player;
  }

  /**
   * Get the player who owns the cell.
   * 
   * @return The Player associated with the StartCell
   */
  public Player getPlayer() {
    return owner;
  }

  /**
   * Set the color of the cell.
   * 
   * @param color A Color which is set to the StartCell
   */
  public void setColor(Color color) {
    cellColor = color;
  }

  /**
   * Get the cells color.
   * 
   * @return The Color associated with the StartCell
   */
  public Color getColor() {
    return cellColor;
  }

  @Override
  public String toString() {
    return "StartCell: perm points?= " + isPermPoint() + " Point value= " + getPoints().getPoints()
        + " Owner= " + this.getPlayer();
  }
}
