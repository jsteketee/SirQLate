package edu.skidmore.cs376b.sirqlate.beans.implementation;


/**
 * Class for the item. Functionality currently minimal.
 * 
 * @author cenglish
 */
public class Item {
  /**
   * the name of the item.
   */
  private String name;

  /**
   * 
   * @param name.
   */
  public Item(String name) {
    this.name = name;
  }

  /**
   * @return item's name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name to set of item.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * item uses are implemented in specific subclasses of item (gamification beans).
   */
  public void useItem() {}

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public boolean equals(Object compare) {
    return compare.toString().equals(toString());
  }

  @Override
  public String toString() {
    return "Item: " + getName();
  }

  // in initial build items don't actually do anything; more information will be added

}
