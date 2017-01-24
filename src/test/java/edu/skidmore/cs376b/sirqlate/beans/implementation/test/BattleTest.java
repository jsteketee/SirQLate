package edu.skidmore.cs376b.sirqlate.beans.implementation.test;


import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Battle;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This class is for the unit test of Battle bean.
 * 
 * @author gjin
 */
public class BattleTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Battle battle;
  private GamePiece attacker;
  private GamePiece defender;
  private int battleindex;

  @Before
  public void setup() {
    battle = new Battle(attacker, defender, battleindex);
  }

  @Test
  public void getWinner() {
    battle.setWinner(attacker);
    assertEquals("Incorrect winner", attacker, battle.getWinner());
  }

  @Test
  public void getWinner1() {
    battle.setWinner(null);
    assertEquals("Incorrect winner", null, battle.getWinner());
  }

  @Test
  public void getAttacker() {
    assertEquals("Incorrect winner", attacker, battle.getAttacker());
  }

  @Test
  public void getDefender() {
    assertEquals("Incorrect winner", defender, battle.getDefender());
  }

  @Test
  public void getLocation() {
    assertEquals("Incorrect winner", 0, battle.getLocation());
  }

}
