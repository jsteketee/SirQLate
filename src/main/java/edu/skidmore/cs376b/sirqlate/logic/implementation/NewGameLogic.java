package edu.skidmore.cs376b.sirqlate.logic.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.HomeCell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;
import edu.skidmore.cs376b.sirqlate.gamification.factory.GamificationFactory;
import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.StartNewGame;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 * Class containing newGame method. This method is called by front end and results in a Game bean
 * being persisted.
 * 
 * @author jstekete
 * 
 */
public class NewGameLogic implements StartNewGame {
  /**
   * Logger instance.
   */
  private static final Logger LOG = Logger.getLogger(NewGameLogic.class);

  // TODO move magic nums to config file
  private static final int MIN_PLAYERS = 3;
  private static final int MAX_PLAYERS = 6;
  // TODO allow colors to be configurable.
  private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.MAGENTA};
  private static final Random rand = new Random();
  private GameManipulator gameManip = new GameManipulator();
  

  /**
   * Uses a list of emails to create and persist a game object.
   * 
   * @param playerEmails the list of player emails represented by an array of strings
   * @return game the Game object
   */
  public Game newGame(String[] playerEmails) {
    Player[] playerList = getShuffledPlayerList(playerEmails);
    Game game = newGame(playerList);
    return game;
  }

  /**
   * Uses a list of players to create and persist a game object.
   * 
   * @param players the list of players for the game
   * @return game the Game object
   */
  public Game newGame(Player[] players) {
    LOG.trace("players that will be in this game: " + players.toString());
    validatePlayerNum(players.length);
    Collections.shuffle(Arrays.asList(players));
    GameBoard gameBoard = new GameBoard(players.length);
    GamificationFactory.getInstance().populateGameBoard(gameBoard);
    populateSpecialCells(gameBoard, players);
    Game game = new Game(gameBoard, players);
    createGamePieces(game);
    game.setCurrentPlayer(rand.nextInt(players.length));
    game.setRoll(LogicFactory.getInstance().rollNumber());
    gameManip.resetMoveAblePieces(game);
    PersistenceFactory.getInstance().persistGameState(game);
    LOG.trace("starting new game. Game id is " + game.getId());
    return game;
  }

  /**
   * Populates the gamePieces list in game with game pieces for each player. Additionally these
   * pieces are added to their respective startCells on the gameBoard and each gamePieces location
   * is updated.
   * 
   * @param game is the game that is currently being played
   */
  private void createGamePieces(Game game) {
    LOG.debug("About to create and place game pieces");

    ArrayList<GamePiece> pieceList = game.getGamePieceList();
    for (int playerNum = 0; playerNum < 3; playerNum++) {
      LOG.debug("Create pieces for player number: " + playerNum);
      Player player = game.getPlayers()[playerNum];
      LOG.debug("Player receiving pieces: " + player);
      Color color = COLORS[playerNum];
      for (int i = 0; i < 3; i++) {
        LOG.debug("Adding piece: " + i);
        pieceList.add(new GamePiece(i + 1, color, player, gameManip.getStartCellIdx(game, player)));
      }
    }

    LOG.debug("Completed creation of game pieces");

    GameBoard board = game.getGameBoard();
    for (GamePiece piece : pieceList) {
      LOG.debug("Placing piece: " + piece);
      board.getCell(gameManip.getStartCellIdx(game, piece.getOwner()))
          .getGamePieceSpace()[piece.getNumber() - 1] = piece;
    }
    LOG.trace("creating pieces for players " + game.getGamePieceList());

    LOG.debug("Completed placement of game pieces");
  }

  /**
   * Creates start and home cells, and then adds them to their respective positions on the game
   * board.
   * 
   * @param gameBoard GameBoard object
   * @param playerList an array of Player objects
   */
  private void populateSpecialCells(GameBoard gameBoard, Player[] playerList) {
    LOG.debug("About to populate special cells");

    Collections.shuffle(Arrays.asList(COLORS));

    int homeCellIdx = 2;
    int startCellIdx = 3;
    Player player;

    for (int i = 0; i < playerList.length; i++) {
      player = playerList[i];
      HomeCell home = new HomeCell(player, COLORS[i]);
      StartCell start = new StartCell(player, COLORS[i]);
      gameBoard.setCell(home, homeCellIdx);
      gameBoard.setCell(start, startCellIdx);
      homeCellIdx += 12;
      startCellIdx += 12;
    }
    /*
     * 
     * 
     * ArrayList<GamePiece> allPieces = new ArrayList<GamePiece>(); GamePiece[] tempPieces = new
     * GamePiece[3]; // TODO magic number
     * 
     * int homeCellIdx = 2; // TODO magic number - or constant
     * 
     * for (int playerNum = 1; playerNum <= playerList.length; playerNum++) { for (int pieceNum = 1;
     * pieceNum <= 3; pieceNum++) { GamePiece curPiece = new GamePiece(pieceNum, COLORS[playerNum -
     * 1], playerList[playerNum - 1], homeCellIdx + 1); tempPieces[pieceNum - 1] = curPiece;
     * allPieces.add(curPiece); } game.setGamePieceList(allPieces); gameBoard.setCell( new
     * StartCell(tempPieces[0].getOwner(), tempPieces, tempPieces[0].getColor()), homeCellIdx + 1);
     * gameBoard.setCell(new HomeCell(tempPieces[0].getOwner(), tempPieces[0].getColor()),
     * homeCellIdx); homeCellIdx += 12; // TODO magic number - or constant - 10 per edges and 2
     * specials
     * 
     * }
     */
    /*
     * ======= Collections.shuffle(Arrays.asList(COLORS)); GameBoard gameBoard =
     * game.getGameBoard();
     * 
     * ArrayList<GamePiece> allPieces = new ArrayList<GamePiece>(); GamePiece[] tempPieces = new
     * GamePiece[3]; // TODO magic number
     * 
     * int homeCellIdx = 2; // TODO magic number - or constant
     * 
     * for (int playerNum = 1; playerNum <= playerList.length; playerNum++) { for (int pieceNum = 1;
     * pieceNum <= 3; pieceNum++) { GamePiece curPiece = new GamePiece(pieceNum, COLORS[playerNum -
     * 1], playerList[playerNum - 1], homeCellIdx + 1); tempPieces[pieceNum - 1] = curPiece;
     * allPieces.add(curPiece); } game.setGamePieceList(allPieces); gameBoard.setCell( new
     * StartCell(tempPieces[0].getOwner(), tempPieces, tempPieces[0].getColor()), homeCellIdx + 1);
     * gameBoard.setCell(new HomeCell(tempPieces[0].getOwner(), tempPieces[0].getColor()),
     * homeCellIdx); homeCellIdx += 12; // TODO magic number - or constant - 10 per edges and 2
     * specials
     * 
     * }
     */

    LOG.debug("Completed populating special cells");

  }

  /**
   * Retrieves Player objects from persistance using an array of player emails. Player objects are
   * then added to an array and their order is randomized.
   * 
   * @param playerEmails A list of player emails that are reperented by an array of Strings
   * @return playerList A list of Player objects
   */
  private Player[] getShuffledPlayerList(String[] playerEmails) {
    Player[] playerList = new Player[playerEmails.length];
    for (int i = 0; i < playerEmails.length; i++) {
      playerList[i] = PersistenceFactory.getInstance().retrievePlayer(playerEmails[i]);
    }
    Collections.shuffle(Arrays.asList(playerList));
    return playerList;
  }

  /**
   * Makes sure that the number of players is valid.
   * 
   * @param playerNum the number of players.
   */
  private void validatePlayerNum(int playerNum) {
    if (playerNum < MIN_PLAYERS || playerNum > MAX_PLAYERS) {
      LOG.trace("The given number of players does not match the allowed range.");
      throw new IllegalArgumentException(
          "The given number of players does not match the allowed range.");
    }
  }
}
