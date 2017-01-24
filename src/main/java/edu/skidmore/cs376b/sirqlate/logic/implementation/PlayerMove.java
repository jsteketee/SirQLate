package edu.skidmore.cs376b.sirqlate.logic.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Battle;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.gamification.factory.GamificationFactory;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.MakeMove;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

import org.apache.log4j.Logger;

/**
 * Class that is responsible for updating the Game every time an action is taken. This includes
 * ordinary moves as well as battle moves. The given moves are assumed to be legal.
 * 
 * @author jstekete
 *
 */
public class PlayerMove implements MakeMove {


  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(PlayerMove.class);
  
  private Die die = new Die();
  private GameManipulator gameManip = new GameManipulator();

  /**
   * Method handles a standard move updating all aspects of the game.
   */
  @Override
  public void makeMove(Game game, GamePiece piece, int destinationCellIdx) {
    LOG.trace("before makeMove: " + piece.toString());
    boolean positiveRoll = game.getRoll() > 0;
    int moveLength = decrementRoll(game, piece, destinationCellIdx);
    gameManip.recordPieceMoved(game, piece);
    gameManip.movePiece(game, piece, destinationCellIdx);
    GamificationFactory.getInstance().processMoves(game,
        game.getGameBoard().getCell(destinationCellIdx), piece, moveLength, positiveRoll);

    gameManip.checkWinCondition(game);
    if (game.getRoll() == 0) {
      updateTurnState(game);
    }
   
    //If there is a winner
    if (game.getWinner() != null) {
      GamificationFactory.getInstance().assignWinnerPoints(game);
      PersistenceFactory.getInstance().persistGameState(game);
    }
    
    PersistenceFactory.getInstance().persistPlayer(game.getCurrentPlayer());
    LOG.trace(
        "after makeMove: " + piece.toString() + ". Destination Cell index: " + destinationCellIdx);
  }

  /**
   * Method handles a battle move updating all aspects of the game.
   */
  @Override
  public void makeMove(Game game, GamePiece attacker, GamePiece defender) {
    LOG.trace(
        "before battle, attacker: " + attacker.toString() + ". defender: " + defender.toString());
    boolean positiveRoll = game.getRoll() > 0;
    int moveLength = decrementRoll(game, attacker, defender.getLocation());
    gameManip.recordPieceMoved(game, attacker);
    Battle curBattle = new Battle(attacker, defender, moveLength);
    game.setBattle(curBattle);

    if (BattleLogic.roll()) {
      // battle is won
      int battleLocation = defender.getLocation();
      gameManip.movePiece(game, defender, gameManip.getStartCellIdx(game, defender.getOwner()));
      gameManip.movePiece(game, attacker, battleLocation);
      GamificationFactory.getInstance().processMoves(game,
          game.getGameBoard().getCell(attacker.getLocation()), attacker, moveLength, positiveRoll);
      game.getBattle().setWinner(attacker);
    } else {
      // battle is lost
      gameManip.movePiece(game, attacker, gameManip.getStartCellIdx(game, attacker.getOwner()));
      game.getBattle().setWinner(defender);
    }

    if (game.getRoll() == 0) {
      updateTurnState(game);
    }
    PersistenceFactory.getInstance().persistPlayer(game.getCurrentPlayer());
    LOG.trace(
        "after battle, attacker: " + attacker.toString() + ". defender: " + defender.toString());
  }

  
  /**
   * Makes a null move. ie. nothing happens but the turn and roll get updated as if a full move did.
   * 
   * @param game is the game currently being played
   */
  @Override
  public void makeMove(Game game) {
    LOG.trace("before null move, roll: " + game.getRoll() + ", turn: " + game.getTurnNum());
    game.setRoll(0);
    updateTurnState(game);
    LOG.trace("after null move, roll: " + game.getRoll() + ", turn: " + game.getTurnNum());
  }

  /**
   * This is a helper method which decrements the roll by the correct amount.
   * 
   * @param game The game bean
   * @param piece The piece being moved
   * @param destinationCellIdx The location that the piece is being moved to
   * @return moveLength the length of the move
   */
  private int decrementRoll(Game game, GamePiece piece, int destinationCellIdx) {
    LOG.trace("before decrement roll, roll num: " + game.getRoll());
    int moveLength = gameManip.getDistance(game, piece.getLocation(), destinationCellIdx);
    if (Math.abs(game.getRoll()) < moveLength) {
      throw new IllegalStateException("Not enough roll for the given move");
    }
    if (game.getRoll() > 0) {
      game.setRoll(game.getRoll() - moveLength);
    } else {
      game.setRoll(game.getRoll() + moveLength);
    }
    LOG.trace("after decrement roll, roll num: " + game.getRoll());
    return moveLength;
  }

  /**
   * This helper method updates the game bean when a players turn is over.
   * 
   * @param game The game bean
   */
  private void updateTurnState(Game game) {
    LOG.trace("before update turn:" + game.getTurnNum());
    gameManip.nextPlayersTurn(game);
    gameManip.resetMoveAblePieces(game);
    game.setRoll(die.generateRollNumber());
    LOG.trace("after update turn:" + game.getTurnNum());
  }
}


