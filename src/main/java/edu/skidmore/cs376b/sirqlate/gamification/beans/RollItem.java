package edu.skidmore.cs376b.sirqlate.gamification.beans;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;
import org.apache.log4j.Logger;

/**
 * Bean for items that specifically affect roll of a player.
 * 
 * @author ndonovan
 *
 */

public class RollItem extends Item implements RollAffectingItem {

  private int addition;
  private static final Logger LOG = Logger.getLogger(RollItem.class);

  /**
   * Calls the item constructor with name.
   * 
   * @param name is the name of the item
   */
  public RollItem(String name, int additionalRoll) {
    super(name);
    addition = additionalRoll;
  }

  /**
   * Given a roll and an addition of the roll, returns the combined roll.
   * 
   * @param itemOwner is the owner of the item
   * @param roll is the roll that the player rolled
   * @return the combined roll and effect of item
   */
  @Override
  public int useItem(Player itemOwner, int roll) {
    LOG.debug("itemOwner: " + itemOwner + ", roll: " + roll);
    itemOwner.getInventory().removeItem(this);
    PersistenceFactory.getInstance().persistPlayer(itemOwner);
    return roll + addition;
  }

}
