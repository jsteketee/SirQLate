package edu.skidmore.cs376b.sirqlate.gamification.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.HomeCell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.PopulateGameBoardInterface;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopulateGameBoardLogic implements PopulateGameBoardInterface {
  /**
   * Logger instance.
   */
  private static final Logger LOG = Logger.getLogger(PopulateGameBoardLogic.class);

  /**
   * Sets up the board with random amounts of items and points. Does not place in start or home
   * cells.
   */
  @Override
  public void populateGameBoard(GameBoard gameboard) {

    // retrieves array list of items from database
    ArrayList<Item> arrayItems = new ArrayList<Item>();
    arrayItems.addAll(Arrays.asList(PersistenceFactory.getInstance().retrieveItemPool()));

    // list to store which cells we've visited
    List<Integer> visited = new ArrayList<Integer>();
    // choose how many cells are assigned items
    int cellsWithItemsTotal = (int) (Math.random() * 8 + 4);

    LOG.debug("About to assign cells for items. Num cells:" + cellsWithItemsTotal);

    // picks a random non-home non-start cell to start things off
    int cellToAssignItem = (int) (Math.random() * 36);
    if (gameboard.getCellList()[cellToAssignItem] instanceof StartCell
        || gameboard.getCellList()[cellToAssignItem] instanceof HomeCell) {
      cellToAssignItem += 2;
    }
    visited.add(cellToAssignItem);

    // choose which cells are assigned items
    for (int temp = 0; temp < cellsWithItemsTotal; temp++) {
      while (visited.contains(cellToAssignItem)) {
        cellToAssignItem = (int) (Math.random() * 36);
        if (gameboard.getCellList()[cellToAssignItem] instanceof StartCell
            || gameboard.getCellList()[cellToAssignItem] instanceof HomeCell) {
          cellToAssignItem += 2;
        }
      }
      visited.add(cellToAssignItem);
      int tempi = (int) (Math.random() * arrayItems.size() - 1);
      gameboard.getCellList()[cellToAssignItem].setItem(arrayItems.get(tempi));
      // arrayItems.remove(tempi);
    }
    LOG.debug("Completed picking cells for items");

    // choose how many cells are assigned points
    int cellsWithPointsTotal = (int) (Math.random() * 10 + 6);

    LOG.debug("About to assign cells for points. Num cells:" + cellsWithPointsTotal);

    // choose which cells are assigned points
    for (int tempp = 0; tempp < cellsWithPointsTotal; tempp++) {
      // starts cell with end of visited to get us right in the loop
      int cellToAssignPoints = visited.get(visited.size() - 1);
      while (visited.contains(cellToAssignPoints)) {
        cellToAssignPoints = (int) (Math.random() * 36);
        if (gameboard.getCellList()[cellToAssignPoints] instanceof StartCell
            || gameboard.getCellList()[cellToAssignPoints] instanceof HomeCell) {
          cellToAssignPoints += 2;
        }
      }
      visited.add(cellToAssignPoints);
      long pointsToSet = (long) (Math.random() * 100);
      double permCheck = Math.random();
      if (permCheck < .2) {
        gameboard.getCellList()[cellToAssignPoints].setPermPoint(true);
        pointsToSet = pointsToSet / 2;
      }
      gameboard.getCellList()[cellToAssignPoints].setPoints(new Points(pointsToSet));

    }
    LOG.debug("Completed picking cells for points");
  }
}
