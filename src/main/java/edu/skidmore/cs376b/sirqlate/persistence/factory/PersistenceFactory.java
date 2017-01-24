package edu.skidmore.cs376b.sirqlate.persistence.factory;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.persistence.implementation.DbAccess;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * This is the factory of Persistence.
 * 
 * @author clin, xzheng
 */
public final class PersistenceFactory {
  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(PersistenceFactory.class);

  /**
   * Instance of the class.
   */
  private static PersistenceFactory instance;

  /**
   * The private constructor of the class.
   */
  private PersistenceFactory() {

  }

  /**
   * Returns singleton instance of the class.
   * 
   * @return PersistenceFactory
   */
  public static synchronized PersistenceFactory getInstance() {
    if (instance == null) {
      instance = new PersistenceFactory();
    }
    return instance;
  }

  /**
   * This is the method to persist the player bean.
   * 
   * @param player the player Object that contain the info that wants to be persisted
   */
  public void persistPlayer(Player player) {
    DbAccess.getInstance().getAccessObject().persistPlayer(player);
  }

  /**
   * This is the method to persist game state. TODO Implement game state persistence
   * 
   * @param game current game
   */
  public void persistGameState(final Game game) {
    // LOG.warn("Game state persistence is not supported: " + game);
    // DSR: Async update of the database
    new Thread(new Runnable() {
      public void run() {
        LOG.debug("Begin persisting game state");
        Player[] players = game.getPlayers();
        for (int x = 0; x < players.length; x++) {
          LOG.trace("Begin persisting player: " + players[x]);
          persistPlayer(players[x]);
          LOG.trace("End persisting player: " + players[x]);
        }
      }
    }).start();

    LOG.debug("End persisting game state");
  }

  /**
   * This is the method to retrieve game state. TODO Implement game state persistence
   * 
   * @param player the player
   * @return the game state of the player, or null if the email(player) not found
   */
  public Game retrieveGameState(Player player) {
    LOG.warn("Game state persistence is not supported: " + player);
    return null;
  }

  /**
   * This is the method to retrieve player details.
   * 
   * @param email the unique identification of the player
   * @return Player object of the player
   */
  public Player retrievePlayer(String email) {
    return DbAccess.getInstance().getAccessObject().getPlayer(email);
  }

  /**
   * This is a method to retrieve item pool from database.
   * 
   * @return an array of all items in the database
   */
  public Item[] retrieveItemPool() {
    return DbAccess.getInstance().getAccessObject().getItems();
  }
  
  public ArrayList<Player> getPlayerList() {
    return DbAccess.getInstance().getAccessObject().getPlayers();
  }
}
