package edu.skidmore.cs376b.sirqlate.logic.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

/**
 * Provides methods accessing and manipulating various game attributes.
 * 
 * @author jstekete
 *
 */
public interface ManipulateGame {
  /**
   * Changes the value of the gamePiece slot at the pieces current location to null. Then the piece
   * is saved to an open slot in the destination cell. The locationIdx of the gamePiece is also
   * updated. If the move can't be performed an error is thrown. This should be the only method that
   * alters the location of pieces.
   * 
   * @param game The game being played
   * @param piece The piece being moved
   * @param destinationCellIdx The destination cell index
   */
  void movePiece(Game game, GamePiece piece, int destinationCellIdx);

  /**
   * Gets the position for a given player's homecell. We create 12 cells per edge because this
   * includes 2 special cells
   * 
   * @param player the current player
   * @return the position int or -1 if it's not found
   */
  int getHomeCellIdx(Game game, Player player);

  /**
   * Gets the position for a given player's startcell. We create 12 cells per edge because this
   * includes 2 special cells
   * 
   * @param game the current game
   * @param player the current player
   * @return the position int or -1 if it's not found
   */
  int getStartCellIdx(Game game, Player player);

  /**
   * removes a game piece from the list.
   * 
   * @param game the current game
   * @param piece Remove a piece from the set of moved pieces
   */
  void recordPieceMoved(Game game, GamePiece piece);

  /**
   * Resets the list of game pieces to include all the pieces owned by a given player.
   * 
   * @param game the current game
   */
  void resetMoveAblePieces(Game game);

  /**
   * Returns the number cells a gamepiece must move into to get from the startIdx to the EndIdx.
   * @param game is the current game
   * @param startIdx The index that we start at.
   * @param endIdx The index that we end at.
   * @return Thedistance a piece must travel.

   */
  int getDistance(Game game, int startIdx, int endIdx);

  /**
   * Increments the curPlayer variable to refer to the player whose turn it is next.
   * 
   * @param game the game bean
   */
  void nextPlayersTurn(Game game);

  /**
   * determines if a player has won the game and sets the updates the player object in game bean
   * accordingly.
   * 
   * @param game the current game
   */
  public boolean checkWinCondition(Game game);

  /**
   * Determins whether or not the entire roll must be used by the first move.
   * 
   * @param game The game being played.
   * @return forced a boolean representing whether the move is fored.
   */
  public boolean isForcedMove(Game game);

}