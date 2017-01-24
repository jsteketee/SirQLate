package edu.skidmore.cs376b.sirqlate.gamification.beans;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;
import org.apache.log4j.Logger;

/**
 * Bean for items that specifically affect points of a player.
 * 
 * @author ndonovan
 *
 */

public class PointsItem extends Item implements PointAffectingItem {

  private long points;
  private static final Logger LOG = Logger.getLogger(PointsItem.class);

  /**
   * Calls the item constructor with name.
   * 
   * @param name is the name of the item
   */
  public PointsItem(String name, long additionalPoints) {
    super(name);
    points = additionalPoints;
  }

  /**
   * Adds points to a player's total.
   * 
   * @param itemOwner is the owner of the item
   * @param targetPlayer is the player being affected
   */
  @Override
  public void useItem(Player itemOwner, Player targetPlayer) {
    LOG.debug("itemOwner: " + itemOwner + ", targetPlayer: " + targetPlayer);
    long totalPoints = targetPlayer.getPoints().getPoints() + points;
    targetPlayer.getPoints().setPoints(totalPoints);
    itemOwner.getInventory().removeItem(this);
    PersistenceFactory.getInstance().persistPlayer(itemOwner);
  }

}
