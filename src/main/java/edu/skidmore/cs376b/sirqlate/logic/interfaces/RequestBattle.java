package edu.skidmore.cs376b.sirqlate.logic.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;

/**
 * interface to request battle.
 * 
 * @author yliu
 */

public interface RequestBattle {

  public boolean requestBattle(GamePiece piece);

}
