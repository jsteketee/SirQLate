package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This class is for the unit test of Points bean.
 * 
 * @author cenglish
 */
public class PointsTest {
  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Points points;

  @Before
  public void setup() {
    points = new Points(126);
  }

  @Test
  public void getName() {

    assertEquals("Points not returned correctly", 126, points.getPoints());
  }

  @Test
  public void setItem() {
    points.setPoints(99);
    assertEquals("Points not set correctly", 99, points.getPoints());
  }
}
