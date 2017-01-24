package edu.skidmore.cs376b.sirqlate.beans.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievement;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Achievements;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.util.ArrayList;


/**
 * This class is for the unit test of Achievements bean.
 * 
 * @author cenglish
 */
public class AchievementsTest {
  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * An instance of the generator for testing.
   */
  private Achievements alist;

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    Achievement testAchievement = new Achievement("Lab Coat", "Write a test class");
    ArrayList<Achievement> list = new ArrayList<Achievement>();
    list.add(testAchievement);
    alist = new Achievements(list);
  }

  @Test
  public void getAchievements() {

    assertEquals("Achievements not returned correctly", "Lab Coat",
        alist.getAchievements().get(0).getName());
  }

  @Test
  public void addAchievement() {
    Achievement testA = new Achievement("abc", "def");
    Achievement testA2 = new Achievement("ghi", "jkl");
    ArrayList<Achievement> list = new ArrayList<Achievement>();
    list.add(testA);
    list.add(testA2);

    alist.addAchievement(testA2);

    assertEquals("Add item not working correctly", "ghi", alist.getAchievements().get(1).getName());
  }
}
