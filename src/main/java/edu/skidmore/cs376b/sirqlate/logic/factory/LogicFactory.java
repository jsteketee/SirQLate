package edu.skidmore.cs376b.sirqlate.logic.factory;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.logic.implementation.Die;
import edu.skidmore.cs376b.sirqlate.logic.implementation.MoveChoices;
import edu.skidmore.cs376b.sirqlate.logic.implementation.NewGameLogic;
import edu.skidmore.cs376b.sirqlate.logic.implementation.PlayerMove;
import edu.skidmore.cs376b.sirqlate.logic.implementation.UserLoginLogic;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.DisplayMoves;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.InitiateRoll;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.MakeMove;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.StartNewGame;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.UserLogin;

import java.util.ArrayList;


/**
 * This is an factory class for logic tier.
 * 
 * @author yliu
 */

public class LogicFactory {

  /**
   * instance of the class.
   */
  private static LogicFactory instance;
  Game game;

  /**
   * the private constructor of the class.
   */
  private LogicFactory() {
    game = null;
  }

  /**
   * Returns the singleton instance of the class.
   * 
   * @return LogicFactory
   */
  public static synchronized LogicFactory getInstance() {
    if (instance == null) {
      instance = new LogicFactory();
    }

    return instance;
  }

  /**
   * method to retrieve roll number.
   * 
   * @return roll number
   */

  public int rollNumber() {
    InitiateRoll initDie = new Die();
    return initDie.generateRollNumber();
  }

  /**
   * This is a method that generate a new game and returns Game.
   * 
   * @param playerEmails an array of player emails stored as Strings
   * @return Game returns a Game object
   */
  public Game startNewGame(String[] playerEmails) {
    StartNewGame newGame = new NewGameLogic();
    game = newGame.newGame(playerEmails);
    return game;
  }


  /**
   * This is a method that returns a 2d array of int of all possible move choices.
   * 
   * @param piece the gamepiece to move.
   * @return the possible move choices in a 2D int array
   */
  public int[][] possibleMove(GamePiece piece) {
    Game check = this.game;
    GameBoard gameboard = check.getGameBoard();
    int rollValue = check.getRoll();
    Player mover = check.getCurrentPlayer();
    boolean firstMove;
    if (check.getMoveablePieces().size() == 3) {
      firstMove = true;
    } else {
      firstMove = false;
    }
    DisplayMoves movechoice = new MoveChoices();
    return movechoice.possibleMoveChoices(gameboard, piece.getLocation(), rollValue, mover,
        firstMove);
  }

  /**
   * This is a method that returns a 2d array of int of all possible move choices.
   * 
   * @param gamePiecePosition the position of the piece to move on the gameBoard
   * @return the possible move choices in a 2D int array
   */
  public int[][] possibleMove(int gamePiecePosition) {
    Game check = this.game;
    GameBoard gameboard = check.getGameBoard();
    int rollValue = check.getRoll();
    Player mover = check.getCurrentPlayer();
    boolean firstMove;
    if (check.getMoveablePieces().size() == 3) {
      firstMove = true;
    } else {
      firstMove = false;
    }

    DisplayMoves movechoice = new MoveChoices();
    return movechoice.possibleMoveChoices(gameboard, gamePiecePosition, rollValue, mover,
        firstMove);
  }

  /**
   * This method checks if the password is correct.
   * 
   * @param email email of user
   * @param password password of user
   * @return true if the email and the password match
   */
  public boolean userPasswordMatched(String email, String password) {
    UserLogin userlogin = new UserLoginLogic();
    return userlogin.userPasswordMatched(email, password);
  }

  /**
   * Updates the state of the game for a player making a standard move.
   * 
   * @param piece The piece being moved
   * @param destinationCellIdx The index of the destination cell
   * @return an instance of the game
   */
  public Game makeMove(GamePiece piece, int destinationCellIdx) {
    MakeMove playerMove = new PlayerMove();
    playerMove.makeMove(this.game, piece, destinationCellIdx);
    return this.game;
  }

  /**
   * Updates the state of the game for a player making a battle move.
   * 
   * @param attacker The piece that is being moved
   * @param defender the piece that is being attacked
   * @param destinationCellIdx The index of the battle cell
   * @return an instance of the game
   */
  public Game makeMove(GamePiece attacker, GamePiece defender, int destinationCellIdx) {
    MakeMove playerMove = new PlayerMove();
    playerMove.makeMove(this.game, attacker, defender);
    return this.game;
  }

  /**
   * Updates the state of the game for a player with no move.
   * 
   * @return an instance of the game
   */
  public Game makeMove() {
    PlayerMove playerMove = new PlayerMove();
    playerMove.makeMove(game);
    return this.game;
  }

  /**
   * This method checks if the user have an account.
   * 
   * @param email email of user
   * @return true if the user need to create an account
   */
  public boolean signUpNeeded(String email) {
    UserLogin userlogin = new UserLoginLogic();
    return userlogin.signUpNeeded(email);
  }

  /**
   * This method is to retrieve what has been removed.
   * 
   * @return the list of pieces that have already been moved.
   */
  public ArrayList<GamePiece> getMovedPieces() {
    return game.getMoveablePieces();
  }


  /**
   * This method is to retrieve what can be moved.
   * 
   * @return the list of pieces that can be moved.
   */
  public ArrayList<GamePiece> getMovablePieces() {
    ArrayList<GamePiece> moveablePieces = new ArrayList<GamePiece>();
    for (int index = 0; index < game.getMoveablePieces().size(); index++) {
      if (possibleMove(game.getMoveablePieces().get(index)) != null
          && game.getMoveablePieces().get(index).getLocation() % 12 != 2) {
        // If a piece is in the home cell, it is not movable     
        moveablePieces.add(game.getMoveablePieces().get(index));
      }
    }
    return moveablePieces;
  }
}
