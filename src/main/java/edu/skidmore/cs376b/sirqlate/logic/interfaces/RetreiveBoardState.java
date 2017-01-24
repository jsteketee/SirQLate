package edu.skidmore.cs376b.sirqlate.logic.interfaces;


import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;

/**
 * Allow front end to retrieve board state.
 * 
 * @author yliu
 */

public interface RetreiveBoardState {
  public Game getState();
}
