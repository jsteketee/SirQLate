package edu.skidmore.cs376b.sirqlate.gamification.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;

/**
 * Interface called by logic to update moves with points and items information.
 * 
 * @author gjin
 */

public interface ProcessMovesInterface {
  public void processMoves(Game game, Cell cell, GamePiece gamePiece, int distanceMoved, boolean positiveRoll);

}
