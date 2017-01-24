package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.logic.implementation.Die;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DieTest {

  // TODO use config file.
  private static final int MIN_ROLL = -2;
  private static final int MAX_ROLL = 8;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void generateRollTestRange() {
    Die die = new Die();
    int minVal = die.generateRollNumber();
    int maxVal = die.generateRollNumber();
    int roll;
    boolean zeroRoll = false;
    for (int i = 0; i < 10000; i++) {
      roll = die.generateRollNumber();
      if (roll == 0) {
        zeroRoll = true;
      }
      if (roll < minVal) {
        minVal = roll;
      }
      if (roll > maxVal) {
        maxVal = roll;
      }
    }
    assertEquals("incorrect die range", MIN_ROLL, minVal);
    assertEquals("incorrect die range", MAX_ROLL, maxVal);
    assertEquals("incorrect die range", false, zeroRoll);
  }

}
