package edu.skidmore.cs376b.sirqlate.logic.implementation;

import edu.skidmore.cs376b.sirqlate.logic.interfaces.InitiateRoll;

import org.apache.log4j.Logger;

import java.util.Random;


/**
 * This classes sole purpose is to provide a random roll value withen the die range.
 * 
 * @author jstekete
 *
 */
public class Die implements InitiateRoll {

  // TODO get from config file.

  /**
   * Minimum allowed roll value.
   */
  private static final int MIN_ROLL = -2;

  /**
   * Maximum allowed roll value.
   */
  private static final int MAX_ROLL = 8;

  /**
   * A random number generator.
   */
  private static final Random RAND = new Random();
  
  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(Die.class);

  /**
   * Returns a pseudo-random number between MIN_ROLL and MAX_ROLL inclusive.
   */
  @Override
  public int generateRollNumber() {
    int num = 0;
    num = RAND.nextInt(MAX_ROLL - MIN_ROLL) + MIN_ROLL;
    if (num >= 0) {
      num += 1;
    }
    LOG.trace("the roll is " + num);

    // while (num == 0) {
    // num = RAND.nextInt(MAX_ROLL + 1 - MIN_ROLL) + MIN_ROLL;
    // }

    return num;
  }
}
