package edu.skidmore.cs376b.sirqlate.logic.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;

/**
 * Interface for makeMove method. This method is the only method that should every modify the state
 * of the game bean.
 * 
 * @author jstekete
 *
 */
public interface MakeMove {
  /**
   * This method will provide the logic to update the game for a player making a move into a slot
   * with one unoccupied slot.
   * 
   * @param game The game being played
   * @param piece The GamePiece that is being moved.
   * @param destinationCellIdx the index of the cell that the piece is being moved into
   */
  void makeMove(Game game, GamePiece piece, int destinationCellIdx);

  /**
   * This method will provide the logic to update the game for when a player is attempting to move
   * their piece to a occupied cell, resulting in a battle.
   * 
   * @param game The game bean
   * @param attacker The GamePiece that is being moved.
   * @param defender The GamePiece that is being attacked.
   */
  void makeMove(Game game, GamePiece attacker, GamePiece defender);
  
  /**
   * Makes a null move. ie. nothing happens but the turn and roll get updated as if a full move did.
   * 
   * @param game The game bean
   */
  void makeMove(Game game);
}
