package edu.skidmore.cs376b.sirqlate.persistence.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

/**
 * This is the interface of persistence team.
 * 
 * @author xzheng, clin
 *
 */
public interface PersistenceInterface {
  /**
   * This is the method to persist game state.
   * 
   * @param game current game
   */
  public void persistGameState(Game game);


  /**
   * This is the method to persist the player bean.
   * 
   * @param player the player Object that contain the info that wants to be persisted
   */
  public void persistPlayer(Player player);

  /**
   * This is the method to retrieve game state.
   * 
   * @param email of the player
   * @return the game state of the player, or null if the email not found
   */
  public Game retrieveGameState(String email);

  /**
   * This is a method to retrieve item pool from database.
   * 
   * @return an array of all items in the database
   */
  public Item[] retrieveItemPool();

  /**
   * This is the method to retrieve player details.
   * 
   * @param email the unique identification of the player
   * @return Player object of the player Player player =
   *         RetrievePlayerFactory.getInstance().retrievePlayer(email);
   */
  public Player retrievePlayer(String email);

}
