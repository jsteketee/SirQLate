package edu.skidmore.cs376b.sirqlate.gamification.factory;


import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.gamification.implementation.Leaderboard;
import edu.skidmore.cs376b.sirqlate.gamification.implementation.PopulateGameBoardLogic;
import edu.skidmore.cs376b.sirqlate.gamification.implementation.ProcessMovesLogic;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.PopulateGameBoardInterface;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.ProcessMovesInterface;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.assignWinnerPointsInterface;
import edu.skidmore.cs376b.sirqlate.gamification.implementation.assignWinnerPoints;

/**
 * Factory class for logic to call gamification methods.
 * 
 * @author gjin
 */

public final class GamificationFactory {
  /**
   * instance of the class.
   */
  private static GamificationFactory instance;

  /**
   * The private constructor of the class.
   */
  private GamificationFactory() {

  }

  /**
   * Returns the singleton instance of the class.
   * 
   * @return instance
   */
  public static synchronized GamificationFactory getInstance() {
    if (instance == null) {
      instance = new GamificationFactory();
    }

    return instance;
  }

  /**
   * This is a method called by logic team to process information of points and items on the cell a
   * piece landed on to update information of the player whether he/she gets points or items. Also,
   * if the player passes home, 5 points should be deducted.
   * 
   * @param game the game which moves need to be processed
   * @param cell the cell which the move landed on
   */
  public void processMoves(Game game, Cell cell, GamePiece gamePiece, int distanceMoved,
      boolean positiveRoll) {
    ProcessMovesInterface pmoves = new ProcessMovesLogic();
    pmoves.processMoves(game, cell, gamePiece, distanceMoved, positiveRoll);
  }
  
  /**
   * Called by logic when a game is over to assign points to the winner. 
   * @param game bean
   * 
   */
  public void assignWinnerPoints(Game game){
    assignWinnerPointsInterface wPoints = new assignWinnerPoints();
    wPoints.assignWinnerPoints(game);
  }

  /**
   * This is a method called by logic to populate a game board with points and items information on
   * cells.
   * 
   * @param gameboard the game board to be populated with points and items
   */
  public void populateGameBoard(GameBoard gameboard) {
    PopulateGameBoardInterface pboard = new PopulateGameBoardLogic();
    pboard.populateGameBoard(gameboard);
  }

  /**
   * This is a method called by frontend display the top five ranked players.
   * 
   * @param players the list of all players in database.
   */
  public void getLeaderboard(Player[] players) {
    Leaderboard lboard = new Leaderboard();
    lboard.getLeaderboard(players);
  }
}
