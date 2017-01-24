package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.awt.Color;

/**
 * position on board player it belongs to number piece.
 * 
 * @author cenglish
 *
 */
public class GamePiece {
  /**
   * what number this piece is (of the player's pieces).
   */
  private int number;

  /**
   * color for piece for the game.
   */
  private Color color;

  /**
   * the owner of the piece.
   */
  private Player owner;

  /**
   * The index location of the gamePiece on the gameBoard.
   */
  private int location;

  /**
   * 
   * @param number.
   * @param color.
   * @param owner.
   * @param location.
   */
  public GamePiece(int number, Color color, Player owner, int location) {
    this.number = number;
    this.color = color;
    this.owner = owner;
    this.location = location;
  }

  /**
   * @param number to set the number of the piece to.
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * @return int of the number of the piece.
   */
  public int getNumber() {
    return this.number;
  }

  /**
   * @param color color to set the piece to.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * @return color of the piece.
   */
  public Color getColor() {
    return color;
  }

  /**
   * 
   * @param player to set as owner of the piece.
   */
  public void setOwner(Player player) {
    this.owner = player;
  }

  /**
   * @return owner of the piece.
   */
  public Player getOwner() {
    return this.owner;
  }



  /**
   * @return location.
   */
  public int getLocation() {
    return location;
  }

  /**
   * @param location the location to set.
   */
  public void setLocation(int location) {
    this.location = location;
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object compare) {
    return compare.toString().equals(toString());
  }
  
  @Override
  public String toString() {
    return "GamePiece Attributes(owner/color/number/location):" + getOwner() + "_" + getColor()
        + "_" + getNumber() + "_" + getLocation();
  }


}
