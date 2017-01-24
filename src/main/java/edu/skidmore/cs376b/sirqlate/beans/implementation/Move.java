package edu.skidmore.cs376b.sirqlate.beans.implementation;

/**
 * This is a bean for storing moves.
 * 
 * @author xzheng
 *
 */
public class Move {

  /**
   * The turn number of current game.
   */
  private int turn;

  /**
   * the unique game id of current game.
   */
  private String gameId;

  /**
   * the game piece that is moving.
   */
  private GamePiece piece;

  /**
   * the player who owns the moving piece.
   */
  private Player player;

  /**
   * the position of piece before it moves.
   */
  private int startPos;

  /**
   * the position of piece after it moved.
   */
  private int endPos;

  /**
   * the point being rewarded by this move.
   */
  private Points pointsRewarded;

  /**
   * a boolean for if a fight occurs in this move.
   */
  private boolean fight;

  /**
   * a boolean for if the player win the fight or not.
   */
  private boolean win;

  /**
   * a boolean for if the player chooses to split the roll.
   */
  private boolean split;

  /**
   * the result of a roll.
   */
  private int roll;

  /**
   * the item gained by this move.
   */
  private Item itemGained;

  /**
   * The constructor.
   * 
   * @param turn the turn number of current game
   * @param gameId the unique game id of current game
   * @param piece the gamePiece that is moving
   * @param player the player who owns the moving piece
   * @param startPos the position of piece before it moves
   * @param endPos the position of piece after it moved
   * @param pointsRewarded the point being rewarded by this move
   * @param fight a boolean for if a fight occurs in this move
   * @param win a boolean for if the player win the fight or not
   * @param split a boolean for if the player chooses to split the roll
   * @param roll the result of a roll
   * @param itemGained the item gained by this move
   */
  public Move(int turn, String gameId, GamePiece piece, Player player, int startPos, int endPos,
      Points pointsRewarded, boolean fight, boolean win, boolean split, int roll, Item itemGained) {
    this.turn = turn;
    this.gameId = gameId;
    this.piece = piece;
    this.player = player;
    this.startPos = startPos;
    this.endPos = endPos;
    this.pointsRewarded = pointsRewarded;
    this.fight = fight;
    this.setWin(win);
    this.split = split;
    this.roll = roll;
    this.itemGained = itemGained;
  }

  /**
   * getter for turn.
   * 
   * @return the turn
   */
  public int getTurn() {
    return turn;
  }

  /**
   * setter for turn.
   * 
   * @param turn the turn to set
   */
  public void setTurn(int turn) {
    this.turn = turn;
  }

  /**
   * getter for gameId.
   * 
   * @return the gameId
   */
  public String getGameId() {
    return gameId;
  }

  /**
   * setter for gameId.
   * 
   * @param gameId the gameId to set
   */
  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  /**
   * getter for piece.
   * 
   * @return the piece
   */
  public GamePiece getPiece() {
    return piece;
  }

  /**
   * setter for piece.
   * 
   * @param piece the piece to set
   */
  public void setPiece(GamePiece piece) {
    this.piece = piece;
  }

  /**
   * getter for player.
   * 
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * setter for player.
   * 
   * @param player the player to set
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * getter for start position.
   * 
   * @return the startPos
   */
  public int getStartPos() {
    return startPos;
  }

  /**
   * setter for start position.
   * 
   * @param startPos the startPos to set
   */
  public void setStartPos(int startPos) {
    this.startPos = startPos;
  }

  /**
   * getter for end position.
   * 
   * @return the endPos
   */
  public int getEndPos() {
    return endPos;
  }

  /**
   * setter for end position.
   * 
   * @param endPos the endPos to set
   */
  public void setEndPos(int endPos) {
    this.endPos = endPos;
  }

  /**
   * getter for points rewarded.
   * 
   * @return the pointsRewarded
   */
  public Points getPointsRewarded() {
    return pointsRewarded;
  }

  /**
   * setter for points rewarded.
   * 
   * @param pointsRewarded the pointsRewarded to set
   */
  public void setPointsRewarded(Points pointsRewarded) {
    this.pointsRewarded = pointsRewarded;
  }

  /**
   * getter for fight.
   * 
   * @return the fight
   */
  public boolean isFight() {
    return fight;
  }

  /**
   * setter for fight.
   * 
   * @param fight the fight to set
   */
  public void setFight(boolean fight) {
    this.fight = fight;
  }

  /**
   * getter for split.
   * 
   * @return the split
   */
  public boolean isSplit() {
    return split;
  }

  /**
   * setter for split.
   * 
   * @param split the split to set
   */
  public void setSplit(boolean split) {
    this.split = split;
  }

  /**
   * getter for roll.
   * 
   * @return the roll
   */
  public int getRoll() {
    return roll;
  }

  /**
   * setter for roll.
   * 
   * @param roll the roll to set
   */
  public void setRoll(int roll) {
    this.roll = roll;
  }

  /**
   * getter for item gained.
   * 
   * @return the itemGained
   */
  public Item getItemGained() {
    return itemGained;
  }

  /**
   * setter for item gained.
   * 
   * @param itemGained the itemGained to set
   */
  public void setItemGained(Item itemGained) {
    this.itemGained = itemGained;
  }

  /**
   * getter for win.
   * 
   * @return the win
   */
  public boolean isWin() {
    return win;
  }

  /**
   * setter for win.
   * 
   * @param win the win to set
   */
  public void setWin(boolean win) {
    this.win = win;
  }

}
