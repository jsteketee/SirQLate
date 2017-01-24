package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.util.Date;

/**
 * The java bean that contain the information of player.
 * 
 * @author xzheng
 */
public class Player {
  /**
   * each player should have an unique email that will serve as their identification.
   */
  private String email;

  /**
   * the number of game that this player had played.
   */
  private int gamePlayed;

  /**
   * the name of this player.
   */
  private String name;

  /**
   * the birthday of this player.
   */
  private Date birthday;

  /**
   * the totally points that this player has.
   */
  private Points points;

  /**
   * the items that this player holds.
   */
  private Inventory inventory;

  /**
   * The achievement a player accomplished.
   */
  private Achievements achievements;

  /**
   * The hashed password of the player.
   */
  private String passwordHash;

  /**
   * The salt for password.
   */
  private String passwordSalt;

  /**
   * The default constructor, everything null.
   */
  public Player() {
    this((String) null, 0, (String) null, (Date) null, (Points) null, (Inventory) null,
        (Achievements) null, (String) null, (String) null);
  }

  /**
   * The constructor.
   * 
   * @param email the email of player, it should be unique to each player
   * @param name the name of the player
   * @param birthday the birthday of the player
   * @param passwordHash the hashed password of player
   * @param passwordSalt the salt for the password
   */
  public Player(String email, String name, Date birthday, String passwordHash,
      String passwordSalt) {
    this(email, 0, name, birthday, (Points) null, (Inventory) null, (Achievements) null,
        passwordHash, passwordSalt);

  }

  /**
   * The constructor.
   * 
   * @param email the email of player, it should be unique to each player
   * @param gamePlayed the number of game that this player had played
   * @param name the name of the player
   * @param birthday the birthday of the player
   * @param points the totally point of the player
   * @param inventory the inventory of the player
   * @param achievements the achievements of the player
   * @param passwordHash the hashed password of player
   * @param passwordSalt the salt for the password
   */
  public Player(String email, int gamePlayed, String name, Date birthday, Points points,
      Inventory inventory, Achievements achievements, String passwordHash, String passwordSalt) {
    this.email = email;
    this.gamePlayed = gamePlayed;
    this.name = name;
    this.birthday = birthday;
    this.points = points;
    this.inventory = inventory;
    this.achievements = achievements;
    this.passwordHash = passwordHash;
    this.passwordSalt = passwordSalt;

  }

  /**
   * getter for email.
   * 
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * setter for email.
   * 
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * getter for gamePlayed.
   * 
   * @return the id
   */
  public int getGamePlayed() {
    return gamePlayed;
  }

  /**
   * setter for gamePlayed.
   * 
   * @param gamePlayed the # of the game a player played
   */
  public void setGamePlayed(int gamePlayed) {
    this.gamePlayed = gamePlayed;
  }

  /**
   * getter for name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * setter for name.
   * 
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * getter for birthday.
   * 
   * @return the birthday
   */
  public Date getBirthday() {
    return birthday;
  }

  /**
   * setter for birthday.
   * 
   * @param birthday the birthday to set
   */
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  /**
   * getter for points.
   * 
   * @return the points
   */
  public Points getPoints() {
    return points;
  }

  /**
   * setter for points.
   * 
   * @param points the points to set
   */
  public void setPoints(Points points) {
    this.points = points;
  }

  /**
   * getter for inventory.
   * 
   * @return the Inventory
   */
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * setter for inventory.
   * 
   * @param inventory the inventory to set
   */
  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  /**
   * getter for achievements.
   * 
   * @return the achievements
   */
  public Achievements getAchievements() {
    return achievements;
  }

  /**
   * setter for achievements.
   * 
   * @param achievements the achievements to set
   */
  public void setAchievements(Achievements achievements) {
    this.achievements = achievements;
  }

  /**
   * Get the password hash.
   * 
   * @return the password hash
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Set the password hash.
   * 
   * @param passwordHash the password hash to set
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  /**
   * getter for salt.
   * 
   * @return the salt
   */
  public String getSalt() {
    return passwordSalt;
  }

  /**
   * setter for salt.
   */
  public void setSalt(String passwordSalt) {
    this.passwordSalt = passwordSalt;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object compare) {
    return compare.toString().equals(toString());
  }

  @Override
  public String toString() {
    return "Player: " + getEmail() + " (" + getName() + ")";
  }
}
