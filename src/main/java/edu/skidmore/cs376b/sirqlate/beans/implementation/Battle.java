package edu.skidmore.cs376b.sirqlate.beans.implementation;

/**
 * Battle bean containing the battle status, the attacking piece, the defending piece and the
 * location of the battle.
 * 
 * @author jstekete
 *
 */
public class Battle {

  private int battleLocationIdx;
  private GamePiece attacker;
  private GamePiece defender;
  private GamePiece winner;
  
  /**
   * Starts a battle with all of the necessary attributes.
   * 
   * @param attacker.
   * @param defender.
   * @param battleLocationIdx.
   */
  public Battle(GamePiece attacker, GamePiece defender,  int battleLocationIdx) {
    this.attacker = attacker;
    this.defender = defender;
    this.battleLocationIdx = battleLocationIdx;
  }
  
  /**
   * Sets the winner of the battle.
   * @param winner is the winner of the battle
   */
  public void setWinner(GamePiece winner) {
    this.winner = winner;
  }
  

  /**
   * returns the index of the battle location.
   * 
   * @return battleLocationIdx
   */
  public int getLocation() {
    return this.battleLocationIdx;
  }

  /**
   * gets the defender.
   * 
   * @return the defending gamePiece.
   */
  public GamePiece getDefender() {
    return this.defender;
  }

  /**
   * gets the attacker.
   * 
   * @return the attacking GamePiece.
   */
  public GamePiece getAttacker() {
    return this.attacker;
  }

  /**
   * gets the winner.
   * 
   * @return the winning GamePiece
   */
  public GamePiece getWinner() {
    return this.winner;
  }
}
