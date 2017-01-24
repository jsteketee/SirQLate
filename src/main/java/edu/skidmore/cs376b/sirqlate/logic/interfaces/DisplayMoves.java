package edu.skidmore.cs376b.sirqlate.logic.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

/**
 * In interface to be called by FrontEnd to display move choices.
 * 
 * @author xhuang1
 * 
 */

public interface DisplayMoves {
  int[][] possibleMoveChoices(GameBoard gameBoard, int startFrom, int rollValue, Player mover, 
      boolean firstMove);

}
