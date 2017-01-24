package edu.skidmore.cs376b.sirqlate.gamification.beans;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Item;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;
import org.apache.log4j.Logger;

/**
 * Bean for items that specifically affect position of a player.
 * 
 * @author ndonovan
 *
 */

public class PositionItem extends Item implements PositionAffectingItem {

  private int cells;
  private static final Logger LOG = Logger.getLogger(PositionItem.class);

  /**
   * Calls the item constructor with name.
   * 
   * @param name is the name of the item.
   * @param move is the number of cells to move forward or backwards.
   */
  public PositionItem(String name, int move) {
    super(name);
    cells = move;
  }

  /**
   * Gets the location of the game piece and moves it accordingly.
   * 
   * @param itemOwner is the owner of the item
   * @param game is the state of the game
   * @param gamePiece is the game piece being affected
   * @return the new cell after moving the appropriate distance
   */
  @Override
  public Cell useItem(Player itemOwner, Game game, GamePiece gamePiece) {
    LOG.debug("itemOwner: " + itemOwner + ", game: " + game + ", gamePiece: " + gamePiece);

    int cellIndexOfPiece = gamePiece.getLocation();

    int newCellIndex;
    Cell newCell;

    if (cellIndexOfPiece + cells < 0) {
      newCellIndex = (game.getGameBoard().getCellList().length) + (cellIndexOfPiece + cells);
      while (newCellIndex < 0) {
        newCellIndex += game.getGameBoard().getCellList().length;
      }
    } else if (cellIndexOfPiece + cells > game.getGameBoard().getCellList().length) {
      newCellIndex = (cellIndexOfPiece + cells) - (game.getGameBoard().getCellList().length);
      while (newCellIndex > game.getGameBoard().getCellList().length) {
        newCellIndex -= game.getGameBoard().getCellList().length;
      }
    } else {
      newCellIndex = cellIndexOfPiece + cells;
    }

    newCell = game.getGameBoard().getCellList()[newCellIndex];
    itemOwner.getInventory().removeItem(this);
    PersistenceFactory.getInstance().persistPlayer(itemOwner);
    PersistenceFactory.getInstance().persistGameState(game);
    return newCell;
  }

}
