package edu.skidmore.cs376b.sirqlate.beans.implementation;


/**
 * bean for achievements; gamerscore type int may be added.
 * 
 * @author cenglish
 *
 */
public class Achievement {
  /**
   * name of the achievement.
   */
  private String name;

  /**
   * the description of the achievement.
   */

  private String description;


  /**
   * @param name of the achievement.
   * @param description of the achievement.
   */
  public Achievement(String name, String description) {
    this.name = name;
    this.description = description;

  }

  /**
   * 
   * @param name to set for achievement.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @return name.
   */
  public String getName() {
    return name;
  }

  /**
   * 
   * @param description to set for achievement.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 
   * @return description.
   */
  public String getDescription() {

    return description;

  }
}
