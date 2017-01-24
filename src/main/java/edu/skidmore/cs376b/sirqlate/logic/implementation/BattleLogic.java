package edu.skidmore.cs376b.sirqlate.logic.implementation;

import java.lang.Math;

/**
 * Determine how a battle is resolved.
 * 
 * @author mbanksmo
 *
 */
public class BattleLogic {
  /**
   * Generate true or false to pick winner of battle.
   * 
   * @return a random boolean to determine if the challenging piece wins or losses
   */
  public static boolean roll() {
    return Math.random() < 0.5;
  }

}
