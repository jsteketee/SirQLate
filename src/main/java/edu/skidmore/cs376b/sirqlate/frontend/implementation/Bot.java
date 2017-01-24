package edu.skidmore.cs376b.sirqlate.frontend.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Prototype for the AI.
 * 
 * @author An Phi (aphi)
 */
public class Bot {

  /**
   * Wait time to keep between moves (in milliseconds).
   */
  private static int MOVE_WAIT_TIME = 1000;

  /**
   * Number of cells per side.
   */
  private static int NUM_CELLS_PER_SIDE = 10;

  /**
   * Index of the home cell.
   */
  private static final int HOME_CELL_INDEX = 2;

  /**
   * Random number generator.
   */
  private static Random rand = new Random();

  /**
   * This method allows the computer to make move.
   * 
   * @param game the current game.
   */
  public void makeMove(Game game, Board board) {
    try {
      Thread.sleep(MOVE_WAIT_TIME);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      Player currentPlayer = game.getCurrentPlayer();
      int currentPlayerIndex = 0;
      for (int i = 0; i < game.getPlayers().length; ++i) {
        if (currentPlayer.equals(game.getPlayers()[i])) {
          currentPlayerIndex = i;
          break;
        }
      }
      ArrayList<GamePiece> movablePieces = LogicFactory.getInstance().getMovablePieces();
      GamePiece chosenPiece =
          movablePieces.get(rand.nextInt((movablePieces.size() - 1 - 0) + 1) + 0);
      int[][] possibleMoves = LogicFactory.getInstance().possibleMove(chosenPiece);
      // Since the possible moves are sorted in order from close to far, we can scan through to see
      // if the home cell can be reached, if not, we move the piece to the max range
      int chosenLocation = chosenPiece.getLocation();
      for (int i = 0; i < possibleMoves.length; ++i) {
        chosenLocation = i;
        if (possibleMoves[i][0] == currentPlayerIndex * (NUM_CELLS_PER_SIDE + 2)
            + HOME_CELL_INDEX) {
          break;
        }
      }
      if (possibleMoves[chosenLocation][1] == 1) {
        // Battle happens
        GamePiece[] opponentPieces = board.getOpponent(game, possibleMoves[chosenLocation][0]);
        ArrayList<GamePiece> strictOpponentPieces = new ArrayList<GamePiece>();
        for (GamePiece piece : opponentPieces) {
          if (!piece.getOwner().equals(currentPlayer)) {
            strictOpponentPieces.add(piece);
          }
        }
        if (strictOpponentPieces.isEmpty()) {
          // If both pieces in the battle cell belong to the current player, take on the first
          LogicFactory.getInstance().makeMove(chosenPiece, opponentPieces[0],
              possibleMoves[chosenLocation][0]);
        } else {
          LogicFactory.getInstance().makeMove(chosenPiece,
              strictOpponentPieces.get(rand.nextInt((strictOpponentPieces.size() - 1 - 0) + 1) + 0),
              possibleMoves[chosenLocation][0]);
        }
      } else {
        LogicFactory.getInstance().makeMove(chosenPiece, possibleMoves[chosenLocation][0]);
      }
    }
  }
}
