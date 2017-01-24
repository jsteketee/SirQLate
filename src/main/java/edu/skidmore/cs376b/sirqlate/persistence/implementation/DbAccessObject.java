package edu.skidmore.cs376b.sirqlate.persistence.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;
import edu.skidmore.cs376b.sirqlate.infrastructure.factory.InfrastructureFactory;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a class that handles communicating with the mySQL database.
 * 
 * @author mleong, lsweezy
 */
public class DbAccessObject {
  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(DbAccessObject.class);

  /**
   * The underlying database connection object.
   */
  private QueryProcessor databaseAccess;

  /**
   * Setup the database connection.
   * 
   * @throws SQLException If the database cannot be accessed
   */
  public DbAccessObject() throws SQLException {
    String driver =
        InfrastructureFactory.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_DRIVER);
    String url =
        InfrastructureFactory.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_URL);
    String user =
        InfrastructureFactory.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_USER);
    String password =
        InfrastructureFactory.getInstance().getPropertyValue(PropertyKey.PERSISTENCE_DB_PASSWORD);

    LOG.info("Connect to database using driver(" + driver + "), url(" + url + "), username(" + user
        + ") using password?(" + (password != null && password.length() > 0) + ")");

    databaseAccess = new QueryProcessor(driver, url, user, password);
  }

  /**
   * This method returns a list of all the players.
   * 
   * @return returns the list of player beans
   * 
   */
  public ArrayList<Player> getPlayers() {
    String query;
    PreparedStatement stmt;
    ResultSet rs = null;
    Inventory inventory = null;
    Points score = null;
    Player record = null;
    ArrayList<Player> playerList = new ArrayList<Player>();

    query = "SELECT player.email, player.name, player.birthday, "
        + "player.passwordHash, player.passwordSalt, player.gamesPlayed, player.score FROM player";

    try {
      stmt = databaseAccess.setupQuery(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        Player player = new Player(rs.getString("email"), rs.getString("name"),
            rs.getDate("birthday"), rs.getString("passWordHash"), rs.getString("passwordSalt"));
        player.setGamePlayed(rs.getInt("gamesPlayed"));
        score = new Points(rs.getLong("score"));
        player.setPoints(score);
        playerList.add(player);
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to retrieve player" + throwable);
      throw new IllegalStateException("Failed to get data " + throwable);
    } finally {
      databaseAccess.closeResultSet(rs);
    }
    return playerList;
  }

  /**
   * This method takes in a player's email and queries the mySQL database for rest of that player's
   * information.
   * 
   * @param email The player's email used as a key to find the player in the database.
   * @return Returns the player bean related to the email.
   */
  public Player getPlayer(String email) {
    String query;
    PreparedStatement stmt;
    ResultSet rs = null;
    Inventory inventory = getInventory(email);
    Points score = null;
    Player record = null;

    query = "SELECT player.gamesPlayed, player.name,"
        + " player.birthday, player.score, player.passwordHash, player.passwordSalt"
        + " FROM player " + " WHERE player.email = ?";

    try {
      stmt = databaseAccess.setupQuery(query);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      while (rs.next()) {
        record = new Player(null, null, null, null, null);
        record.setEmail(email);
        record.setGamePlayed(rs.getInt("gamesPlayed"));
        record.setName(rs.getString("name"));
        record.setBirthday(rs.getDate("birthday"));
        record.setPasswordHash(rs.getString("passwordHash"));
        record.setSalt(rs.getString("passwordSalt"));
        score = new Points(rs.getLong("score"));
        record.setPoints(score);
        record.setInventory(inventory);
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to retrieve player data for email: " + email, throwable);
      throw new IllegalStateException("Failed to get data for email: " + email, throwable);
    } finally {
      databaseAccess.closeResultSet(rs);
    }
    return record;
  }

  /**
   * Returns the list of possible items used in game from the database.
   * 
   * @return Returns an array of items representing the possible list of items attainable in game
   */
  public Item[] getItems() {
    String query;
    PreparedStatement stmt;
    ResultSet rs = null;
    List<Item> data = new ArrayList<Item>();

    query = "select * from items";

    try {
      stmt = databaseAccess.setupQuery(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        Item record = new Item(rs.getString("name"));
        data.add(record);
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to retrieve items", throwable);
      throw new IllegalStateException("Failed to get items", throwable);
    } finally {
      databaseAccess.closeResultSet(rs);
    }

    return data.toArray(new Item[data.size()]);
  }

  /**
   * This method takes in a player's email and queries mySQL with the email to return the player's
   * inventory.
   * 
   * @param email The player's email used as a key to find the player in the database.
   * @return Returns an inventory bean for a specific player.
   */
  public Inventory getInventory(String email) {
    String query;
    PreparedStatement stmt;
    ResultSet rs = null;
    Inventory inventory = new Inventory(new ArrayList<Item>());

    query = "SELECT items.name, inventory.quantity FROM player"
        + " INNER JOIN inventory on player.id = inventory.player_id"
        + " INNER JOIN items on items.id = inventory.item_id" + " WHERE player.email = ?";

    try {
      stmt = databaseAccess.setupQuery(query);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      while (rs.next()) {
        Item item = new Item(rs.getString("name"));
        int quantity = rs.getInt("quantity");
        for (int i = 0; i < quantity; i++) {
          inventory.addItem(item);
        }
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to get inventory for email: " + email, throwable);
      throw new IllegalStateException("Failed to get inventory for email: " + email, throwable);
    } finally {
      databaseAccess.closeResultSet(rs);
    }

    return inventory;
  }

  /**
   * Save a player to the database.
   * 
   * @param player The player to persist
   */
  public void persistPlayer(Player player) {
    // this code returns the player's id for persisting their inventory and achievements and
    // checking if the player already exists;
    // The default is -1 in the case of the player not existing in the database
    ResultSet rs = null;
    PreparedStatement stmt = null;;
    int playerId = -1;
    int column;
    String idQuery = "SELECT id FROM player WHERE email = ?";
    try {
      stmt = databaseAccess.setupQuery(idQuery);
      stmt.setString(1, player.getEmail());
      rs = stmt.executeQuery();
      while (rs.next()) {
        playerId = rs.getInt("id");
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to retrive data for player: " + player, throwable);
      throw new IllegalStateException("Failed to get data", throwable);
    } finally {
      databaseAccess.closeResultSet(rs);
      stmt = null;
    }

    // meaning the player already exists in the database so we just update their information
    if (playerId != -1) {
      String query;
      query = "UPDATE player" + " SET birthday=?,score=?,name=?,"
          + " gamesPlayed=?,passwordHash=?,passwordSalt=?" + " WHERE email=?";

      try {
        stmt = databaseAccess.setupQuery(query);
        column = 1;
        if (player.getBirthday() != null) {
          stmt.setDate(column++, new java.sql.Date(player.getBirthday().getTime()));
        } else {
          stmt.setDate(column++, new java.sql.Date(0));
        }
        if (player.getPoints() != null) {
          stmt.setLong(column++, player.getPoints().getPoints());
        } else {
          stmt.setLong(column++, 0);
        }
        stmt.setString(column++, player.getName());

        stmt.setInt(column++, player.getGamePlayed());
        stmt.setString(column++, player.getPasswordHash());
        stmt.setString(column++, player.getSalt());
        stmt.setString(column++, player.getEmail());

        stmt.execute();
      } catch (SQLException sqle) {
        LOG.error("Unable to save updated player details for email: " + player.getEmail(), sqle);
        throw new IllegalStateException(
            "Unable to save updated player details for email: " + player.getEmail(), sqle);
      } finally {
        databaseAccess.closeStatement(stmt);
        stmt = null;
      }

      if (player.getInventory() != null) {
        persistInventory(player.getInventory().getInventory(), playerId);
      }

      if (player.getAchievements() != null) {
        persistAchievements(player.getAchievements().getAchievements(), playerId);
      }
    } else {
      // in the case the player being persisted is a new player, the player is added to the database
      String query = "INSERT INTO player"
          + " (birthday, email, score, name, gamesPlayed, passwordHash, passwordSalt)" + " VALUES"
          + " (?,?,?,?,?,?,?)";

      try {
        stmt = databaseAccess.setupQuery(query);
        column = 1;
        if (player.getBirthday() != null) {
          stmt.setDate(column++, new java.sql.Date(player.getBirthday().getTime()));
        } else {
          stmt.setDate(column++, new java.sql.Date(0));
        }
        stmt.setString(column++, player.getEmail());

        if (player.getPoints() != null) {
          stmt.setLong(column++, player.getPoints().getPoints());
        } else {
          stmt.setLong(column++, 0);
        }
        stmt.setString(column++, player.getName());
        stmt.setInt(column++, player.getGamePlayed());
        stmt.setString(column++, player.getPasswordHash());
        stmt.setString(column++, player.getSalt());

        stmt.execute();
      } catch (SQLException sqle) {
        LOG.error("Unable to insert new player details for email: " + player.getEmail(), sqle);
        throw new IllegalStateException(
            "Unable to insert new player details for email: " + player.getEmail(), sqle);
      } finally {
        databaseAccess.closeStatement(stmt);
        stmt = null;
      }

    }

  }

  private void persistInventory(List<Item> items, int playerId) {
    Map<Integer, Integer> itemCounts = new HashMap<Integer, Integer>();
    String query;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Integer count;
    String persistQuery;

    // removes inventory to recreate it
    String deleteQuery = "DELETE FROM inventory WHERE player_id = ?";
    try {
      stmt = databaseAccess.setupQuery(deleteQuery);
      stmt.setInt(1, playerId);
      stmt.execute();
    } catch (Throwable throwable) {
      LOG.error("Failed to delete inventory for player id: " + playerId, throwable);
      throw new IllegalStateException("Failed to delete inventory for player id: " + playerId,
          throwable);
    } finally {
      databaseAccess.closeStatement(stmt);
    }

    // recreates inventory in database with current items

    for (Item item : items) {
      int id = -1; // default case where no ids are present

      query = "SELECT id FROM items WHERE name = ?";
      try {
        stmt = databaseAccess.setupQuery(query);
        stmt.setString(1, item.getName());
        rs = stmt.executeQuery();
        while (rs.next()) {
          id = rs.getInt("id");

          // LOG.debug("item name : " + item.getName() + "id: " + id);

        }

      } catch (Throwable throwable) {
        LOG.error("Failed to get item id for item: " + item, throwable);
        throw new IllegalStateException("Failed to get item id for item: " + item, throwable);
      } finally {
        databaseAccess.closeResultSet(rs);
        stmt = null;
      }
      if (id != -1) { // this if statement checks that
        count = itemCounts.get(id);
        if (count == null) {
          itemCounts.put(id, 1);
          // LOG.debug("NEWITEMADD itemCounts @ " + id + "is " + count);
        } else {
          itemCounts.put(id, count + 1);

          // LOG.debug("itemCounts @ " + id + " is " + count);
        }
      }
    }

    persistQuery = "INSERT INTO inventory (player_id, item_id, quantity)" + " VALUES (?, ?, ?)";
    try {
      stmt = databaseAccess.setupQuery(persistQuery);
      for (Map.Entry<Integer, Integer> entry : itemCounts.entrySet()) {
        stmt.setInt(1, playerId);
        stmt.setInt(2, entry.getKey());
        stmt.setInt(3, entry.getValue());
        stmt.execute();
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to insert inventory item for player id: " + playerId, throwable);
      throw new IllegalStateException("Failed to insert inventory item for player id: " + playerId,
          throwable);
    } finally {
      databaseAccess.closeStatement(stmt);
    }
  }

  // method for persisting a players achievements in player_achievements
  private void persistAchievements(List<Achievement> achievements, int playerId) {
    String query;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String persistQuery;
    List<Integer> achievementIds = new ArrayList<Integer>();

    // deletes achievements to recreate it
    String deleteQuery = "DELETE FROM player_achievements WHERE player_id = ?";
    try {
      stmt = databaseAccess.setupQuery(deleteQuery);
      stmt.setInt(1, playerId);
      stmt.execute();
    } catch (Throwable throwable) {
      LOG.error("Failed to delete achievements for player id: " + playerId, throwable);
      throw new IllegalStateException("Failed to delete achievements for player id: " + playerId,
          throwable);
    } finally {
      databaseAccess.closeStatement(stmt);
    }

    // this loop creates a list of the ids for the achievements the player has
    for (Achievement achievement : achievements) {
      int id = -1;
      query = "SELECT id FROM achievements_list WHERE name = ?";
      try {
        stmt = databaseAccess.setupQuery(query);
        stmt.setString(1, achievement.getName());
        rs = stmt.executeQuery();
        while (rs.next()) {
          id = rs.getInt("id");
        }
      } catch (Throwable throwable) {
        throw new IllegalStateException("Failed to get data", throwable);
      } finally {
        databaseAccess.closeResultSet(rs);
      }
      if (id != -1) {
        achievementIds.add(id);
      }
    }

    // recreates player_achievements with their current achievements
    persistQuery = "INSERT INTO player_achievements (player_id, achievement_id)" + " VALUES (?, ?)";

    try {
      stmt = databaseAccess.setupQuery(persistQuery);
      // this loop adds to the sql query that will update player_achievements with achievement ids
      // they possess
      for (Integer id : achievementIds) {
        stmt.setInt(1, playerId);
        stmt.setInt(2, id);
        stmt.execute();
      }
    } catch (Throwable throwable) {
      LOG.error("Failed to insert achievement for player id: " + playerId, throwable);
      throw new IllegalStateException("Failed to insert achievement for player id: " + playerId,
          throwable);
    } finally {
      databaseAccess.closeStatement(stmt);
    }
  }
}
