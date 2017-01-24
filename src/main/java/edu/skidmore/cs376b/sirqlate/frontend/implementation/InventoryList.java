package edu.skidmore.cs376b.sirqlate.frontend.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import us.daveread.edu.graphics.surface.DrawingSurface;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Java class that sets up and displays an inventory/points window when prompted.
 * 
 * @author Andrew Riggs (ariggs), An Phi (aphi)
 *
 */
public class InventoryList {
  /**
   * The inventory.
   */
  private HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();

  /**
   * Constructor for InvList. Takes in a Player and builds inventory window.
   * 
   * @param surface the current drawing surface.
   * @param player Type Player. This is the player whose inventory is being shown.
   */
  public void show(DrawingSurface surface, Player player) {
    List<Item> inventoryList = player.getInventory().getInventory();
    for (Item item : inventoryList) {
      addItem(item);
    }
    String output = "Current point: " + player.getPoints().getPoints() + "\n";
    for (Entry<Item, Integer> item : inventory.entrySet()) {
      output += item.getKey().getName() + ":\t\t" + item.getValue().intValue() + "\n";
    }
    surface.showErrorMessage(player.getName() + "'s inventory", output);
  }

  private void addItem(Item item) {
    if (inventory.get(item) == null) {
      inventory.put(item, 1);
    } else {
      inventory.put(item, inventory.get(item) + 1);
    }
  }
}
