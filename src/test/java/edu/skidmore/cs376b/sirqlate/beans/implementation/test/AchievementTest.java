package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This class is for the unit test of Achievement bean.
 * 
 * @author cenglish
 */
public class AchievementTest {

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Achievement achievement;

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    achievement =
        new Achievement("A Journey of a Thousand Miles", "Win your first game of SirQlate");
  }

  @Test
  public void getName() {

    assertEquals("Name not returned correctly", "A Journey of a Thousand Miles",
        achievement.getName());
  }

  @Test
  public void setName() {
    achievement.setDescription("dfs");
    assertEquals("Name not set correctly", "dfs", achievement.getDescription());
  }

  @Test
  public void getDescription() {

    assertEquals("Description not returned correctly", "Win your first game of SirQlate",
        achievement.getDescription());
  }

  @Test
  public void setDescription() {
    achievement.setDescription("dfs");
    assertEquals("Name not set correctly", "dfs", achievement.getDescription());
  }
}

