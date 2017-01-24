package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean responsible for containing a list of all of a player's achievements. Note: instead of
 * setAchievements, addAchievement is to be called for each item by Professor Read's direction.
 * 
 * @author cenglish
 */
public class Achievements {
  /**
   * ArrayList containing achievements.
   */
  private ArrayList<Achievement> achievements = new ArrayList<Achievement>();

  /**
   * 
   * @param achievements.
   */
  public Achievements(ArrayList<Achievement> achievements) {
    this.achievements = achievements;
  }

  /**
   * @return a copy of the achievement list(returned as a copy to prevent tampering).
   */
  public List<Achievement> getAchievements() {
    return new ArrayList<Achievement>(achievements);
  }

  /**
   * @param achievement to add.
   */
  public void addAchievement(Achievement achievement) {
    achievements.add(achievement);
  }


}
