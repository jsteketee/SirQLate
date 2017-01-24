package edu.skidmore.cs376b.sirqlate.logic.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.HomeCell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.DisplayMoves;

public class MoveChoices implements DisplayMoves {


  /**
   * The number of cells on a board.
   */
  private int boardSize;
  private int sideLength = 12;
  private int homeCellInit = 2;
  private int startCellInit = 3;
  private int standardMove = 0;
  private int battleMove = 1;
  private int homeMove = 2;
  private int invalidMove = -1;
  private int arraySizeLength = 12;
  private int arraySizeWidth = 2;

  /**
   * returns a Cell[] list of all possible movable cells for a given roll and roll. first dimension
   * is position of possible move destination second dimension is details about what occurs if cell
   * is landed on, 0 normal 1 battle 2 home
   * 
   * @param gameBoard = takes in current gameBoard
   * @param startFrom = takes in position the piece is moving from
   * @param rollValue = takes in the value of the roll
   * @param mover = takes in which player is currently doing the move
   */
  public int[][] possibleMoveChoices(GameBoard gameBoard, int startFrom, int rollValue,
      Player mover, boolean firstMove) {
    int[][] possibleMoves = new int[arraySizeLength][arraySizeWidth];
    boardSize = gameBoard.getCellList().length;

    for (int i = 0; i < arraySizeLength; i++) {
      possibleMoves[i][0] = invalidMove;
      possibleMoves[i][1] = invalidMove;
    }

    int homeCellPos = getHomeCellPosition(gameBoard, mover);
    int startCellPos = getStartCellPosition(gameBoard, mover);


    if (rollValue > 0) {
      for (int i = 1; i <= rollValue; i++) {
        if (i + startFrom <= boardSize - 1) {
          GamePiece[] spaces = gameBoard.getCell((startFrom + i)).getGamePieceSpace();
          if (i + startFrom != homeCellInit - sideLength && i + startFrom != homeCellInit
              && i + startFrom != homeCellInit + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit - sideLength && i + startFrom != startCellInit
              && i + startFrom != startCellInit + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
                  + sideLength) {
            if (spaces[0] == null || spaces[1] == null) {
              possibleMoves[i][0] = i + startFrom;
              possibleMoves[i][1] = standardMove;
            } else {

              possibleMoves[i][0] = i + startFrom;
              possibleMoves[i][1] = battleMove;

            }
          } else {
            if (i + startFrom == homeCellPos) {
              possibleMoves[i][0] = i + startFrom;
              possibleMoves[i][1] = homeMove;
            }
            rollValue++;

          }
        } else {
          GamePiece[] spaces = gameBoard.getCell((startFrom + i - boardSize)).getGamePieceSpace();
          if (i + startFrom != homeCellInit - sideLength && i + startFrom != homeCellInit
              && i + startFrom != homeCellInit + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit - sideLength && i + startFrom != startCellInit
              && i + startFrom != startCellInit + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
                  + sideLength) {
            if (spaces[0] == null || spaces[1] == null) {
              possibleMoves[i][0] = i + startFrom - boardSize;
              possibleMoves[i][1] = standardMove;
            } else {

              possibleMoves[i][0] = i + startFrom - boardSize;
              possibleMoves[i][1] = battleMove;

            }
          } else {
            if (i + startFrom - boardSize == homeCellPos) {
              possibleMoves[i][0] = i + startFrom - boardSize;
              possibleMoves[i][1] = homeMove;
            }
            rollValue++;

          }
        }
      }
    } else {
      if (startFrom == startCellPos) {
        return null;
      }
      for (int i = -1; i >= rollValue; i--) {
        if (i + startFrom >= 0) {
          GamePiece[] spaces = gameBoard.getCell((startFrom + i)).getGamePieceSpace();
          if (i + startFrom != homeCellInit - sideLength && i + startFrom != homeCellInit
              && i + startFrom != homeCellInit + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit - sideLength && i + startFrom != startCellInit
              && i + startFrom != startCellInit + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
                  + sideLength) {
            if (spaces[0] == null || spaces[1] == null) {
              possibleMoves[-i][0] = i + startFrom;
              possibleMoves[-i][1] = standardMove;
            } else {

              possibleMoves[-i][0] = i + startFrom;
              possibleMoves[-i][1] = battleMove;

            }
          } else {
            rollValue--;
          }
        } else {
          GamePiece[] spaces = gameBoard.getCell((startFrom + i + boardSize)).getGamePieceSpace();
          if (i + startFrom != homeCellInit - sideLength && i + startFrom != homeCellInit
              && i + startFrom != homeCellInit + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength
              && i + startFrom != homeCellInit + sideLength + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit - sideLength && i + startFrom != startCellInit
              && i + startFrom != startCellInit + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
              && i + startFrom != startCellInit + sideLength + sideLength + sideLength
                  + sideLength) {
            if (spaces[0] == null || spaces[1] == null) {
              possibleMoves[-i][0] = i + startFrom + boardSize;
              possibleMoves[-i][1] = standardMove;
            } else {

              possibleMoves[-i][0] = i + startFrom + boardSize;
              possibleMoves[-i][1] = battleMove;

            }
          } else {
            rollValue--;
          }
        }
      }
    }

    boolean finalPiece = finalMoveablePiece(gameBoard, homeCellPos);
    return convert(possibleMoves, firstMove, finalPiece);


  }

  /**
   * converts possibleMoves to one which does not contain invalid spaces. If no possible choices,
   * returns null;
   * 
   * @param convertMe supposed to be possible moves array created in the possibleMoveChoices method
   * @param firstMove used to check if its the firstmove, with all possible moveChoices, or not with
   *        only the largest move available.
   * 
   */

  public int[][] convert(int[][] convertMe, boolean firstMove, boolean finalPiece) {
    int validNum = 0;
    boolean homecellViable = false;
    int homecellPosInArray = 0;
    for (int i = 0; i < arraySizeLength; i++) {
      if (convertMe[i][0] != -1) {
        validNum++;
      }

    }

    if (validNum != 0) {
      int[][] convertedChoices = new int[validNum][arraySizeWidth];
      int numSkipped = 0;
      for (int i = 0; i < arraySizeLength; i++) {
        if (convertMe[i][0] != -1) {
          convertedChoices[i - numSkipped][0] = convertMe[i][0];
          convertedChoices[i - numSkipped][1] = convertMe[i][1];
          if (convertMe[i][1] == 2) {
            homecellViable = true;
            homecellPosInArray = i - numSkipped;
          }
        } else {
          numSkipped++;
        }
      }
      if (firstMove && !finalPiece) {
        return convertedChoices;
      } else {
        if (!homecellViable || validNum - 1 == homecellPosInArray) {
          int[][] secondChoice = new int[1][2];
          secondChoice[0][0] = convertedChoices[validNum - 1][0];
          secondChoice[0][1] = convertedChoices[validNum - 1][1];
          return secondChoice;
        } else {
          if (finalPiece) {
            int[][] secondChoice = new int[2][2];
            secondChoice[0][0] = convertedChoices[homecellPosInArray][0];
            secondChoice[0][1] = convertedChoices[homecellPosInArray][1];
            secondChoice[1][0] = convertedChoices[validNum - 1][0];
            secondChoice[1][1] = convertedChoices[validNum - 1][1];
            return secondChoice;
          } else {
            int[][] secondChoice = new int[1][2];
            secondChoice[0][0] = convertedChoices[validNum - 1][0];
            secondChoice[0][1] = convertedChoices[validNum - 1][1];
            return secondChoice;
          }
        }

      }
    } else {
      return null;
    }
  }

  /**
   * 
   * 
   * checks whether or not there are two pieces in the homecell, and if there are, it returns true.
   * Otherwise it returns false
   * 
   * @param gameBoard the gameboard
   * @param homeCellPos the homecell pos int for the current player
   * @return
   */
  public boolean finalMoveablePiece(GameBoard gameBoard, int homeCellPos) {
    HomeCell test = (HomeCell) gameBoard.getCell(homeCellPos);
    GamePiece[] homeTest = test.getGamePieceSpace();
    int numOfCellsIn = 0;

    if (homeTest[0] != null) {
      numOfCellsIn++;
    }
    if (homeTest[1] != null) {
      numOfCellsIn++;
    }
    if (homeTest[2] != null) {
      numOfCellsIn++;
    }

    if (numOfCellsIn == 2) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * Gets the position for a given player's homecell.
   * 
   * @param gameBoard the current gameboard
   * @param player the current player
   * @return the position int or -1 if it's not found
   */

  public int getHomeCellPosition(GameBoard gameBoard, Player player) {
    HomeCell cell = null;
    boardSize = gameBoard.getCellList().length;

    for (int i = homeCellInit; i < boardSize; i = i + sideLength) {
      cell = (HomeCell) gameBoard.getCell(i);
      if (cell.getPlayer().equals(player)) {
        return i;
      }
    }

    return -1;

  }

  /**
   * Gets the position for a given player's startcell.
   * 
   * @param gameBoard the current gameboard
   * @param player the current player
   * @return the position int or -1 if it's not found
   */
  public int getStartCellPosition(GameBoard gameBoard, Player player) {
    StartCell cell = null;
    boardSize = gameBoard.getCellList().length;

    for (int i = startCellInit; i < boardSize; i = i + sideLength) {
      cell = (StartCell) gameBoard.getCell(i);
      if (cell.getPlayer().equals(player)) {
        return i;
      }
    }

    return -1;

  }



}
