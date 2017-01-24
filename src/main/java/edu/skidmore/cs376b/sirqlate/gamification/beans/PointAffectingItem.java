package edu.skidmore.cs376b.sirqlate.gamification.beans;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

/**
 * Interface for branches that need to deal with point effects of items.
 * 
 * @author ndonovan
 *
 */

public interface PointAffectingItem {

  public void useItem(Player itemOwner, Player targetPlayer);

}
