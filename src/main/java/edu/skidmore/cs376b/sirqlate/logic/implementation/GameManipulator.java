package edu.skidmore.cs376b.sirqlate.logic.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.HomeCell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.ManipulateGame;

import org.apache.log4j.Logger;

public class GameManipulator implements ManipulateGame {

  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(GameManipulator.class);

  /**
   * The last index of the CellList which is 35.
   */
  private static final int LAST_INDEX_OF_CELLLIST = 35;

  /**
   * The first index of the CellList which is 35.
   */
  private static final int FIRST_INDEX_OF_CELLLIST = 0;

  @Override
  public void movePiece(Game game, GamePiece piece, int destinationCellIdx) {
    LOG.trace("Current piece and its location is " + piece.toString());
    GamePiece[] gamePieces = game.getGameBoard().getCell(piece.getLocation()).getGamePieceSpace();
    boolean found = false;

    for (int i = 0; i < gamePieces.length; i++) {
      if (gamePieces[i] != null && gamePieces[i].equals(piece)) {
        found = true;
        gamePieces[i] = null;
        break;
      }
    }

    if (found == false) {
      throw new IllegalStateException(
          "The given game piece could not be found at its location on the game board");
    }

    gamePieces = game.getGameBoard().getCell(destinationCellIdx).getGamePieceSpace();
    boolean isSpace = false;

    for (int i = 0; i < gamePieces.length; i++) {
      if (gamePieces[i] == null) {
        isSpace = true;
        piece.setLocation(destinationCellIdx);
        gamePieces[i] = piece;
        break;
      }
    }

    if (isSpace == false) {
      throw new IllegalStateException(
          "The destination cell has no room for an additional game piece");
    }
    LOG.trace("Current piece and its location after move is " + piece.toString());
  }

  @Override
  public int getHomeCellIdx(Game game, Player player) {

    HomeCell cell = null;

    for (int i = 2; i < game.getPlayers().length * 12; i = i + 12) {
      cell = (HomeCell) game.getGameBoard().getCell(i);
      if (cell.getPlayer().equals(player)) {
        LOG.trace("the home cell index of " + player.toString() + " is " + i);
        return i;
      }
    }
    LOG.trace("the home cell index of " + player.toString() + " is not found");
    return -1;

  }

  @Override
  public int getStartCellIdx(Game game, Player player) {
    StartCell cell = null;

    for (int i = 3; i < game.getPlayers().length * 12; i = i + 12) {
      cell = (StartCell) game.getGameBoard().getCell(i);
      if (cell.getPlayer().equals(player)) {
        LOG.trace("the start cell index of " + player.toString() + " is " + i);
        return i;
      }
    }
    LOG.trace("the start cell index of " + player.toString() + " is not found");
    return -1;
  }

  @Override
  public void recordPieceMoved(Game game, GamePiece piece) {
    LOG.trace("Current moveablePieces list: " + game.getMoveablePieces());
    game.getMoveablePieces().remove(piece);
    LOG.trace("moveablePieces list after remove: " + game.getMoveablePieces());
  }

  @Override
  public void resetMoveAblePieces(Game game) {
    LOG.trace("Current moveablePieces list: " + game.getMoveablePieces());
    game.getMoveablePieces().clear();
    Player curPlayer = game.getCurrentPlayer();
    for (GamePiece p : game.getGamePieceList()) {
      if (p.getOwner().equals(curPlayer)) {
        game.getMoveablePieces().add(p);
      }
    }
    LOG.debug("moveablePieces list after reset: " + game.getMoveablePieces());
  }

  @Override
  public int getDistance(Game game, int startIdx, int endIdx) {
    int distance = 0;
    int curIdx = startIdx;

    while (curIdx != endIdx) {
      // for positive roll
      if (game.getRoll() > 0) {
        if (curIdx + 1 == endIdx) {
          distance++;
          curIdx = endIdx;
        } else {
          if (curIdx + 1 == game.getGameBoard().getCellList().length) {
            distance++;
            curIdx = increment(curIdx);
          } else {
            if (game.getGameBoard().getCell(curIdx + 1).getClass().equals(StartCell.class)
                || game.getGameBoard().getCell(curIdx + 1).getClass().equals(HomeCell.class)) {
              curIdx = increment(curIdx);
            } else {
              curIdx = increment(curIdx);
              distance++;
            }
          }
        }
        // for negative roll
      } else {
        if (curIdx - 1 == endIdx) {
          distance++;
          curIdx = endIdx;
        } else {
          if (curIdx == 0) {
            distance++;
            curIdx = decrement(curIdx);
          } else {
            if (game.getGameBoard().getCell(curIdx - 1).getClass().equals(StartCell.class)
                || game.getGameBoard().getCell(curIdx - 1).getClass().equals(HomeCell.class)) {
              curIdx = decrement(curIdx);
            } else {
              curIdx = decrement(curIdx);
              distance++;
            }
          }
        }
      }
    }
    LOG.trace("the distance is " + distance);
    return distance;
  }

  private int increment(int idx) {
    LOG.trace("the current index is " + idx);
    if (idx == LAST_INDEX_OF_CELLLIST) {
      return FIRST_INDEX_OF_CELLLIST;
    } else {
      LOG.trace("the index after increment is " + (idx + 1));
      return ++idx;
    }
  }

  private int decrement(int idx) {
    LOG.trace("the current index is " + idx);
    if (idx == FIRST_INDEX_OF_CELLLIST) {
      return LAST_INDEX_OF_CELLLIST;
    } else {
      LOG.trace("the index after increment is " + (idx - 1));
      return --idx;
    }
  }

  @Override
  public void nextPlayersTurn(Game game) {
    LOG.trace(
        "the current player for the game: " + game.getId() + ", is " + game.getCurrentPlayer());
    Player[] players = game.getPlayers();
    Player curPlayer = game.getCurrentPlayer();
    int curPlayerIdx = -1;

    for (int i = 0; i < players.length; i++) {
      if (players[i].equals(curPlayer)) {
        curPlayerIdx = i;
      }
    }

    if (curPlayerIdx == -1) {
      throw new IllegalStateException("the curplayer was not found in the list of players!?!?");
    }

    if (curPlayerIdx == players.length - 1) {
      game.setCurrentPlayer(0);
    } else {
      game.setCurrentPlayer(curPlayerIdx + 1);
    }
    game.setTurnNum(game.getTurnNum() + 1);
    LOG.trace("the next player for the game: " + game.getId() + ", is " + game.getCurrentPlayer());
  }

  @Override
  public boolean checkWinCondition(Game game) {
    // check and set the winner in the game bean if there is a winner
    setWinner(game);

    // if the winner is not empty, it means there is a winner
    return !(game.getWinner() == null);
  }

  /**
   * This method will check if there is a winner and then set it if there the winner exist.
   * 
   * @param game the current game
   */
  private static void setWinner(Game game) {
    // get the list of all cell in current game
    Cell[] cellList = game.getGameBoard().getCellList();
    // find out all the home cell, then check if all of its GamePieceSpace is not null, if that is
    // true then set the winner
    LOG.trace("the current winner for game: " + game.getId() + ", is " + game.getWinner());
    for (int i = 0; i < cellList.length; i++) {
      if (cellList[i] instanceof HomeCell) {
        GamePiece[] gamePieceSpot = cellList[i].getGamePieceSpace();
        boolean isWinner = true;
        for (int j = 0; j < gamePieceSpot.length; j++) {
          if (gamePieceSpot[j] == null) {
            isWinner = false;
          }
        }
        if (isWinner) {
          game.setWinner(((HomeCell) cellList[i]).getPlayer());
          LOG.trace("set the winner to be " + ((HomeCell) cellList[i]).getPlayer()
              + " for the game: " + game.getId());
        }
      }
    }
    LOG.trace("the current winner after setWinner for game: " + game.getId() + ", is "
        + game.getWinner());
  }

  @Override
  public boolean isForcedMove(Game game) {
    int numInCell = 0;
    if (game.getRoll() > 0) {
      for (GamePiece p : game.getGameBoard().getCell(getHomeCellIdx(game, game.getCurrentPlayer()))
          .getGamePieceSpace()) {
        if (p != null) {
          numInCell++;
        }
      }
    } else {
      for (GamePiece p : game.getGameBoard().getCell(getStartCellIdx(game, game.getCurrentPlayer()))
          .getGamePieceSpace()) {
        if (p != null) {
          numInCell++;
        }
      }
    }
    return (numInCell > 1);
  }
}
