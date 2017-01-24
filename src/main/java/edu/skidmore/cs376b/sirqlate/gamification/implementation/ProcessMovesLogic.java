package edu.skidmore.cs376b.sirqlate.gamification.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Inventory;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.ProcessMovesInterface;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Logic to process what happens when a player lands on a cell.
 * 
 * @author ndonovan
 */

public class ProcessMovesLogic implements ProcessMovesInterface {

  private static final Logger LOG = Logger.getLogger(ProcessMovesLogic.class);

  /**
   * Empty constructor to hold the methods.
   */
  public ProcessMovesLogic() {

  }

  /**
   * Logic to process move when a player lands on a cell.
   * 
   * @param game is the Game state currently
   * @param cell is the Cell that the player's piece landed on
   */
  public void processMoves(Game game, Cell cell, GamePiece gamePiece, int distanceMoved, boolean positiveRoll) {
    LOG.debug("game: " + game + ", cell: " + cell + ", gamePiece: " + gamePiece + ""
        + ", distanceMoved: " + distanceMoved);
    getPointsFromCell(game.getCurrentPlayer(), cell);
    getItemFromCell(game.getCurrentPlayer(), cell);
    checkIfPassedHome(game, cell, gamePiece, distanceMoved, positiveRoll);
    //addItemsDuringGame(game);

    PersistenceFactory persistFactory = PersistenceFactory.getInstance();
    persistFactory.persistPlayer(game.getCurrentPlayer());
  }

  /**
   * Gets an item from the cell that the player landed on.
   * 
   * @param p is the current Player
   * @param c is the Cell that the player's piece landed on
   */
  private void getItemFromCell(Player player, Cell cell) {
    LOG.debug("player: " + player + ", cell: " + cell);
    if (cell.getItem() != null) {
      if (player.getInventory() != null) {
        player.getInventory().addItem(cell.getItem());
        cell.setItem(null);
      } else {
        player.setInventory(new Inventory(new ArrayList<Item>()));
        player.getInventory().addItem(cell.getItem());
        cell.setItem(null);
      }
    }
  }

  /**
   * Gets points from the cell that the player landed on.
   * 
   * @param p is the current Player
   * @param c is the Cell that the player's piece landed on
   */
  private void getPointsFromCell(Player player, Cell cell) {
    LOG.debug("player: " + player + ", cell: " + cell);
    Points noPoints = new Points(0);

    if (cell.getPoints().getPoints() != noPoints.getPoints()) {
      Points totalPoints = new Points(0);
      if (player.getPoints() != null) {
        totalPoints.setPoints(player.getPoints().getPoints() + cell.getPoints().getPoints());
      } else {
        totalPoints.setPoints(cell.getPoints().getPoints());
      }
      player.setPoints(totalPoints);

      if (!cell.isPermPoint()) {
        cell.setPoints(noPoints);
      }
    }
  }

  /**
   * Checks if player passed home and deducts points if so.
   * 
   * @param game is the current Game state
   * @param cell is the Cell that the player's piece landed on
   * @param gamePiece is the game piece that is in question
   * @param distanceMoved is the distance that the game piece moved
   */
  private void checkIfPassedHome(Game game, Cell cell, GamePiece gamePiece, int distanceMoved, boolean positiveRoll) {
    LOG.debug("game: " + game + ", cell: " + cell + ", gamePiece: " + gamePiece
        + ", distanceMoved: " + distanceMoved);
    Points penalty = new Points(-5);
    int roll = distanceMoved;
    int numPlayers = game.getPlayers().length;
    int currPlayerNum = -1;
    int numCells = game.getGameBoard().getCellList().length;
    int numCellsPerEdge = numCells / numPlayers;
    int currCellIndex = -1;

    for (int i = 0; i < numPlayers; i++) {
      if (game.getCurrentPlayer() == game.getPlayers()[i]) {
        currPlayerNum = i;
      }
    }

    currCellIndex = gamePiece.getLocation();

    if (positiveRoll) {
      for (int i = 1; i <= roll; i++) {
            if (currCellIndex - i == (currPlayerNum * numCellsPerEdge) + 2) {
              game.getCurrentPlayer().getPoints()
                  .setPoints(game.getCurrentPlayer().getPoints().getPoints() + penalty.getPoints());
              break;
            }
          }
    } else {
      for (int i = 1; i <= roll; i++) {
        if (currCellIndex + i == (currPlayerNum * numCellsPerEdge) + 2) {
          game.getCurrentPlayer().getPoints()
              .setPoints(game.getCurrentPlayer().getPoints().getPoints() + penalty.getPoints());
          break;
        }
      }
    }
    
  }
  
//  private void addItemsDuringGame(Game game) {
//    Cell[] celllist = game.getGameBoard().getCellList();
//    ArrayList<GamePiece> piecelist = game.getGamePieceList();
//    ArrayList<Item> arrayItems = new ArrayList<Item>();
//    arrayItems.addAll(Arrays.asList(PersistenceFactory.getInstance().retrieveItemPool()));
//    int itemcount = 0;
//    int piece;
//    int celltoadditem = (int)Math.random() * arrayItems.size() - 1;
//    for (int i = 0; i < celllist.length; i++) {
//      if (celllist[i].getItem() != null) {
//        itemcount++;
//      }
//      if (itemcount < 3 && celllist[celltoadditem].getItem() == null) {
//        for (piece = 0; piece < piecelist.size();piece++) {
//          if (piecelist.get(piece).getLocation() != celltoadditem) {
//            int tempi = (int) (Math.random() * arrayItems.size() - 1);
//            celllist[i].setItem(arrayItems.get(tempi));
//            
//          }
//        }
//      }
//    }
//  }

}
