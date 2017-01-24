package edu.skidmore.cs376b.sirqlate.logic.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;

/**
 * This interface provides an interface for FrontEnd to start a new Game.
 * 
 * @author xhuang1
 */
public interface StartNewGame {
  /**
   * Start a new game.
   * 
   * @param playerEmail The set of players to play the game
   * @return The new game
   */
  Game newGame(String[] playerEmail);
}
