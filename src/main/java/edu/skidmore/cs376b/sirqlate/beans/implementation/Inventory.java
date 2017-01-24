package edu.skidmore.cs376b.sirqlate.beans.implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Note: instead of setInventory addItem is to be called for each item by Professor Read's
 * direction.
 * 
 * @author cenglish
 */
public class Inventory {
  /**
   * ArrayList containing items in the inventory.
   */
  private ArrayList<Item> items = new ArrayList<Item>();

  /**
   * 
   * @param items.
   */
  public Inventory(ArrayList<Item> items) {
    this.items = items;
  }

  /**
   * @return a copy of the inventory (returned as a copy to prevent tampering).
   */
  public List<Item> getInventory() {
    return new ArrayList<Item>(items);
  }

  /**
   * @param item to add.
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * @param item to be removed.
   */
  public void removeItem(Item item) {
    for (Item eachItem : items) {
      if (item == eachItem) {
        items.remove(eachItem);
        break;
      }
    }
  }

}
