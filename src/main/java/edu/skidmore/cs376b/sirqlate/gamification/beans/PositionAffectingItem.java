package edu.skidmore.cs376b.sirqlate.gamification.beans;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

/**
 * Interface for branches that need to deal with position effects of items.
 * 
 * @author ndonovan
 *
 */

public interface PositionAffectingItem {

  public Cell useItem(Player itemOwner, Game game, GamePiece gamePiece);

}
