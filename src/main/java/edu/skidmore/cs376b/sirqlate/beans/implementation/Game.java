package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.util.ArrayList;

/**
 * Game bean containing the GameBoard, Player Array and Current Player index.
 * 
 * @author gpetkaki
 */

public class Game {
  /**
   * A unique ID maintained by the persistence tier.
   */
  private long id;

  /**
   * Container for the GameBoard.
   */
  private GameBoard board;

  /**
   * Container for Players in sequence.
   */
  private Player[] players;

  /**
   * The index of the current player.
   */
  private int currPlayerIdx = 0;

  /**
   * The list of all gamePieces.
   */
  private ArrayList<GamePiece> gamePieceList = new ArrayList<GamePiece>();

  /**
   * This list keeps track of which game pieces are moveable in a given turn.
   */
  private ArrayList<GamePiece> moveAblePieces = new ArrayList<GamePiece>();

  /**
   * The most recent roll that happened in the game.
   */
  private int roll = 0;
  /**
   * the turn number.
   */
  private int turnNum;
  /**
   * keeps track of all attributes associated with a battle.
   */
  private Battle battle;

  /**
   * The minimum roll value.
   */
  private static final int MIN_ROLL = -2;

  /**
   * The maximum roll value.
   */
  private static final int MAX_ROLL = 8;

  /**
   * The winner of the game. If null, the game is still running.
   */
  private Player winner;

  /**
   * Constructor for Game bean, requires a GameBoard and Player array.
   * 
   * @param gameBoard The gameboard
   * @param players The players
   */
  public Game(GameBoard gameBoard, Player[] players) {
    setGameBoard(gameBoard);
    setPlayers(players);
    incrementPlayerGameCount(players);
    setTurnNum(1);
  }

  /**
   * Add this new game to each player's count of games played.
   * 
   * @param players The collection of players playing this game
   */
  private void incrementPlayerGameCount(Player[] players) {
    for (Player player : players) {
      player.setGamePlayed(player.getGamePlayed() + 1);
    }
  }

  /**
   * Returns the Game's unique long id.
   * 
   * @return The game id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the Game's unique long id.
   * 
   * @param id The game id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the Game's GameBoard.
   * 
   * @return The gameboard
   */
  public GameBoard getGameBoard() {
    return board;
  }

  /**
   * Sets the Game's GameBoard.
   * 
   * @param gameboard The gameboard
   */
  public void setGameBoard(GameBoard gameboard) {
    board = gameboard;
  }

  /**
   * Returns the Game's Player list.
   * 
   * @return The collection of players
   */
  public Player[] getPlayers() {
    return players;
  }

  /**
   * Set's the Game's Player list.
   * 
   * @param players The game players collection
   */
  public void setPlayers(Player[] players) {
    this.players = players;
  }

  /**
   * Return the current Player Object.
   * 
   * @return The current player
   */
  public Player getCurrentPlayer() {
    return players[currPlayerIdx];
  }

  /**
   * Sets the current Player index in the collection of players.
   * 
   * @param currPlayer the current player index
   */
  public void setCurrentPlayer(int currPlayer) {
    if (currPlayer >= 0 && currPlayer < players.length) {
      currPlayerIdx = currPlayer;
    }
  }

  /**
   * sets the current Player index in the collection of players.
   * 
   * @param player is the player to be set as current player
   */
  public void setCurrentPlayer(Player player) {
    for (int i = 0; i < this.players.length; i++) {
      if (this.players[i].equals(player)) {
        this.currPlayerIdx = i;
      }
    }
  }

  /**
   * Returns the most recent roll.
   * 
   * @return The roll value
   */
  public int getRoll() {
    return roll;
  }

  /**
   * Sets the most recent roll to a new integer.
   * 
   * @param roll The roll value
   */
  public void setRoll(int roll) {
    if (roll >= MIN_ROLL && roll <= MAX_ROLL) {
      this.roll = roll;
    }
  }

  /**
   * Returns the maximum roll value.
   * 
   * @return Maximum allowed roll value
   */
  public int getMaxRoll() {
    return MAX_ROLL;
  }

  /**
   * Returns the minimum roll value.
   * 
   * @return Minimum allowed roll value
   */
  public int getMinRoll() {
    return MIN_ROLL;
  }

  /**
   * Returns the list of pieces that has been moved during the current turn.
   * 
   * @return The pieces moved
   */
  public ArrayList<GamePiece> getMoveablePieces() {
    return this.moveAblePieces;
  }

  /**
   * Getter for battle bean.
   * 
   * @return The battle
   */
  public Battle getBattle() {
    return battle;
  }

  /**
   * Setter for battle bean.
   * 
   * @param battle The battle
   */
  public void setBattle(Battle battle) {
    this.battle = battle;
  }

  /**
   * Getter for the list of game pieces.
   * 
   * @return gamePieceList
   */
  public ArrayList<GamePiece> getGamePieceList() {
    return this.gamePieceList;
  }

  /**
   * Setter for the game piece list.
   * 
   * @param gamePiece A collection of game pieces
   */
  public void setGamePieceList(ArrayList<GamePiece> gamePiece) {
    this.gamePieceList = gamePiece;
  }

  /**
   * Get the turn number.
   * 
   * @return the turn number
   */
  public int getTurnNum() {
    return turnNum;
  }

  /**
   * Set the turn number.
   * 
   * @param turnNum The turn number
   */
  public void setTurnNum(int turnNum) {
    this.turnNum = turnNum;
  }

  /**
   * Get the winning player, if any.
   * 
   * @return the winner
   */
  public Player getWinner() {
    return winner;
  }

  /**
   * Set the winning player.
   * 
   */
  public void setWinner(Player winnerp) {
    if (winnerp != null) {
      winner = winnerp;
    }
  }
}
