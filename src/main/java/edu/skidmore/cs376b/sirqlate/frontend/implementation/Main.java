package edu.skidmore.cs376b.sirqlate.frontend.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.infrastructure.PropertyKey;
import edu.skidmore.cs376b.sirqlate.infrastructure.factory.InfrastructureFactory;
import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;
import edu.skidmore.cs376b.sirqlate.logic.implementation.GameManipulator;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

import org.apache.log4j.Logger;

import us.daveread.edu.graphics.shape.Drawable;
import us.daveread.edu.graphics.shape.impl.Circle;
import us.daveread.edu.graphics.shape.impl.Image;
import us.daveread.edu.graphics.shape.impl.Rectangle;
import us.daveread.edu.graphics.shape.impl.Text;
import us.daveread.edu.graphics.surface.DrawingSurface;
import us.daveread.edu.graphics.surface.ImageType;
import us.daveread.edu.graphics.surface.MainFrame;
import us.daveread.edu.graphics.surface.WindowMode;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The Main class.
 * 
 * @author David Read (readda), James Armenta, An Phi (aphi)
 * 
 **/
public class Main extends DrawingSurface {

  /**
   * Setup the logger for the class.
   */
  private static final Logger LOG = Logger.getLogger(Main.class);

  /**
   * Serial version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The window width.
   */
  private static final int WINDOW_WIDTH = 800;

  /**
   * The window height.
   */
  private static final int WINDOW_HEIGHT = 600;

  /**
   * The delay time for skip player turn pop-up.
   */
  private static final long WAIT_TIME = 1000;

  /**
   * Number of sides on the board.
   */
  private static int NUM_SIDES = 3;

  /**
   * Number of cells per side.
   */
  private static int NUM_CELLS_PER_SIDE = 10;

  /**
   * The set of perimeter cell shapes (including start and home).
   */
  private Circle[] perimeterCells;

  /**
   * The set of start cell shapes.
   */
  private Circle[] startCells;

  /**
   * The set of home cell shapes.
   */
  private Circle[] homeCells;

  /**
   * The set of player piece shapes.
   */
  private Circle[] playerPieces;

  /**
   * The die.
   */
  private Image die;

  /**
   * The background.
   */
  private Rectangle background;

  /**
   * The Inventory button.
   */
  private Circle[] inventoryButtons;
  
  /**
   * The inventory buttons for the results page.
   */
  private Image[] resultInventoryButtons;
  
  /**
   * The array storing player rankings.
   */
  private Player[] rankings;
  
  /**
   * The button that ends the game from the results screen.
   */
  private Image endButton;

  /**
   * The set of point texts.
   */
  private ArrayList<Text> pointTexts;

  /**
   * The set of item images.
   */
  private ArrayList<Image> itemImages;

  /**
   * The player labels (intended to be set to the player's name).
   */
  private Text[] playerLabels;

  /**
   * The current game.
   */
  private Game game;

  /**
   * The current player.
   */
  private Player currentPlayer;

  /**
   * The list of players' email.
   */
  private String[] playerEmails;

  /**
   * The list of the current player's movable piece(s).
   */
  private ArrayList<GamePiece> movablePieces;

  /**
   * The 2-d array of possible move choice and the chance for battle ocurrence.
   */
  private int[][] possibleMoveChoices;

  /**
   * The currently chosen piece.
   */
  private GamePiece chosenPieceForMove;

  /**
   * The pieces that the current player may potentially battle with.
   */
  private GamePiece[] battlePieces;

  /**
   * Whether there is currently a battle.
   */
  private boolean isInBattle;

  /**
   * The main frame.
   */
  private MainFrame mainFrame;

  /**
   * The game board.
   */
  private Board board;

  /**
   * The authenticator.
   */
  private Authentication authentication;

  /**
   * The AI player.
   */
  private Bot ai;

  /**
   * The set of AI players.
   */
  private HashSet<Player> aiPlayers;

  /**
   * The lock for click action to prevent human control AI.
   */
  private static boolean isBotTurn = false;

  /**
   * Create the frame.
   */
  public Main(String[] initialPositionConfiguration) {
    mainFrame =
        new MainFrame(this, "Sir-Q-Late", WINDOW_WIDTH, WINDOW_HEIGHT, WindowMode.FIXED_SIZE);

    // Position main frame on screen
    boolean enableAutoCentering = Boolean.parseBoolean(InfrastructureFactory.getInstance()
        .getPropertyValue(PropertyKey.FRONTEND_WINDOW_AUTOCENTERING));
    if (enableAutoCentering) {
      mainFrame.setLocation(
          (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WINDOW_WIDTH) / 2,
          (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - WINDOW_HEIGHT) / 2);
    } else {
      int windowX = 0;
      int windowY = 0;
      try {
        windowX = Integer.parseInt(
            InfrastructureFactory.getInstance().getPropertyValue(PropertyKey.FRONTEND_WINDOW_X));
        windowY = Integer.parseInt(
            InfrastructureFactory.getInstance().getPropertyValue(PropertyKey.FRONTEND_WINDOW_Y));
      } catch (NumberFormatException exception) {
        LOG.error("Settings for x and/or y position of the window is not properly stated");
        windowX = 0;
        windowY = 0;
      } finally {
        mainFrame.setLocation(windowX, windowY);
      }
    }
    startGame(initialPositionConfiguration);
  }

  /**
   * This method helps to start the game.
   */
  private void startGame(String[] initialPositionConfiguration) {
    authentication = new Authentication();
    playerEmails = authentication.login(this);


    // Setup AI players
    ArrayList<String> aiPlayerEmailList = new ArrayList<String>();
    aiPlayerEmailList.addAll(Arrays.asList(new String[] {"1@bot", "2@bot", "3@bot"}));
    Collections.shuffle(aiPlayerEmailList);

    aiPlayers = new HashSet<Player>();
    ai = new Bot();
    ArrayList<String> tempEmailList = new ArrayList<String>();
    tempEmailList.addAll(Arrays.asList(playerEmails));
    for (int i = 0; i < NUM_SIDES - playerEmails.length; ++i) {
      aiPlayers.add(PersistenceFactory.getInstance().retrievePlayer(aiPlayerEmailList.get(i)));
      tempEmailList.add(aiPlayerEmailList.get(i));
    }
    playerEmails = tempEmailList.toArray(new String[NUM_SIDES]);


    // Setup the game
    board = new Board();
    game = LogicFactory.getInstance().startNewGame(playerEmails);

    // Setup intial positions - for testing purpose
    if (initialPositionConfiguration.length > 0) {
      setInitialPiecePositions(initialPositionConfiguration);
    }

    // Draw the game
    background = board.setupBackground(this);
    ArrayList<Circle[]> cellArrays = board.setupBoard(this, game);
    perimeterCells = cellArrays.get(0);
    homeCells = cellArrays.get(1);
    startCells = cellArrays.get(2);

    updateGame(true);
  }

  /**
   * This method reset temporary variable values from previous game loop.
   */
  private void flushData() {
    isBotTurn = false;
    pointTexts = new ArrayList<Text>();
    itemImages = new ArrayList<Image>();
    possibleMoveChoices = new int[0][0];
    movablePieces = LogicFactory.getInstance().getMovablePieces();
    isInBattle = false;
    chosenPieceForMove = null;
    battlePieces = null;
    if (currentPlayer == null || !currentPlayer.equals(game.getCurrentPlayer())) {
      addMessage(game.getCurrentPlayer().getName() + "'s turn with " + game.getRoll() + " move(s)");
    }
  }

  /**
   * This method renders the game every time the game bean is updated. This is considered the start
   * of the game loop.
   */
  private void updateGame(boolean enableCheckMovabilityStatus) {
    LOG.trace("Begin process of updating the game");
    board.cleanBoard(this, game, possibleMoveChoices, perimeterCells, playerLabels, playerPieces,
        pointTexts, itemImages);
    flushData();
    playerLabels = board.setupPlayerLabels(this, game, perimeterCells);
    inventoryButtons = board.setupInventory(this, playerLabels);
    die = board.setupDie(this, game);
    pointTexts = board.setupPoints(this, game, perimeterCells);
    itemImages = board.setupItems(this, game, perimeterCells);
    playerPieces = board.setupPieces(this, game, perimeterCells);
    board.setupMovablePieces(game, playerPieces, movablePieces, null);
    if (enableCheckMovabilityStatus) {
      // Only enable showing up the error message when the player is human
      checkMovabilityStatus(!aiPlayers.contains(game.getCurrentPlayer()));
    }
    while (aiPlayers.contains(game.getCurrentPlayer())) {
      computerMakeMove();

      // Re-render the board in this while loop to prevent recursive call
      board.cleanBoard(this, game, possibleMoveChoices, perimeterCells, playerLabels, playerPieces,
          pointTexts, itemImages);
      flushData();
      playerLabels = board.setupPlayerLabels(this, game, perimeterCells);
      die = board.setupDie(this, game);
      pointTexts = board.setupPoints(this, game, perimeterCells);
      itemImages = board.setupItems(this, game, perimeterCells);
      playerPieces = board.setupPieces(this, game, perimeterCells);

      // Check end game
      if (game.getWinner() != null) {
        endGame();
      }

      board.setupMovablePieces(game, playerPieces, movablePieces, null);
      checkMovabilityStatus(false);
    }
    LOG.trace("End process of updating the game");
  }

  /**
   * A method to check if there is any movable pieces. If not then call Logic to move on to next
   * player.
   */
  private void checkMovabilityStatus(boolean enableShowingErrorMessage) {
    if (movablePieces == null || movablePieces.size() == 0) {
      try {
        Thread.sleep(WAIT_TIME);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      } finally {
        if (enableShowingErrorMessage) {
          showErrorMessage("No possible move",
              "Uh ohh " + game.getCurrentPlayer().getName() + ", you are doomed, your roll is "
                  + game.getRoll() + ". We will move on to the next player then...");
        }
        LogicFactory.getInstance().makeMove();
        updateGame(true);
      }
    }
  }

  /**
   * Check for end of game and decide what to do next.
   */
  private void checkEndGame() {
    updateGame(game.getWinner() == null);
    if (game.getWinner() != null) {
      endGame();
    }
  }

  /**
   * Setup the placings array;
   */
  private Player[] setupPlacings() {
    Player[] placesArray = new Player[game.getPlayers().length];
    Player temp;
    
    for (int c = 0; c < game.getPlayers().length; c++) {
      placesArray[c] = game.getPlayers()[c];
    }
    
    for (int i = 0; i < placesArray.length - 1; i++) {
      for (int j = i + 1; j < placesArray.length; j++) {
        if (placesArray[j].getPoints().getPoints() > placesArray[i].getPoints().getPoints()) {
          temp = placesArray[i];
          placesArray[i] = placesArray[j];
          placesArray[j] = temp;
        }
      }
    }
    return placesArray;
  }
  
  /**
   * Setup the screen for end of the game. This is called when the game loop is terminated
   */
  private void endGame() {
    Player[] places = setupPlacings();
    rankings = places;
    
    int answer = askYesNoQuestion("Winner!" , game.getWinner().getName() + 
        " has finally made it! Do you want to view the results?");
    System.out.println(answer);
    if (answer == 1) {
      System.exit(0);
    } else {
      this.clearSurface();
      mainFrame.setTitle("Congratulations " + game.getWinner() + "!");
      background = board.setupBackground(this);
      
      board.setupResults(this, places, game);
      resultInventoryButtons = board.setupResultsInventories(this, places);
      endButton = board.setupEndButton(this);
      
    }
  }

  /**
   * Let computer make move instead.
   */
  private void computerMakeMove() {
    isBotTurn = true;
    ai.makeMove(game, board);
  }

  /*
   * Assign action for clicking on certain objects.
   */
  @Override
  public void drawableMouseClick(Drawable drawable) {
    if (!isBotTurn) {
      Circle chosenObject = null;
      Player chosenPlayer = null;
      GamePiece chosenPiece = null;
      int chosenPieceNum = -1;
      
      if (drawable == endButton) {
        int answer = askYesNoQuestion("Play again?" , "Would you like to play again?");
        if (answer == 1) {
          System.exit(0);
        }
        else {
          startGame(null);
        }
      }
      
      if (resultInventoryButtons != null) {
        for (int i = 0; i < resultInventoryButtons.length; ++i) {
          if (drawable == resultInventoryButtons[i]) {
            InventoryList inventoryList = new InventoryList();
            inventoryList.show(this, game.getPlayers()[i]);
          }
        }
      }

      // Check if the inventory button is clicked
      for (int index = 0; index < inventoryButtons.length; ++index) {
        if (drawable.equals(inventoryButtons[index])) {
          InventoryList inventoryList = new InventoryList();
          inventoryList.show(this, game.getPlayers()[index]);
        }
      }

      for (int index = 0; index < playerPieces.length; ++index) {
        if (drawable.equals(playerPieces[index])) {
          chosenObject = playerPieces[index];
          chosenPiece = game.getGamePieceList().get(index);
          chosenPlayer = chosenPiece.getOwner();
          chosenPieceNum = chosenPiece.getNumber();
          break;
        }
      }
      if (chosenPiece == null) {
        // ANY CELL
        LOG.trace("Begin looking for chosen cell");
        for (int index = 0; index < (NUM_CELLS_PER_SIDE + 2) * NUM_SIDES; index++) {
          if (drawable.equals(perimeterCells[index])) {
            LOG.debug("Chosen cell: " + index);
            if (board.isInPossibleMovesList(possibleMoveChoices, index)) {
              if (board.hasBattle(possibleMoveChoices, index)) {
                // Has Battle
                isInBattle = true;
                board.highlightBattleCell(perimeterCells, index);
                battlePieces = board.getOpponent(game, index);
                addMessage("Select a piece to battle with");
              } else {
                // No Battle
                battlePieces = null;
                isInBattle = false;
                LOG.trace("Call logic to make move");
                LogicFactory.getInstance().makeMove(chosenPieceForMove, index);
                LOG.trace("Logic make move call returns, check for end game");
                checkEndGame();
                LOG.trace("End game check completed");
              }
            }
          }

        }
        LOG.trace("End looking for chosen cell");
      } else {
        if (isInBattle) {
          // Battle Piece is chosen here
          addMessage("Oh well, so you are fighting with " + chosenPiece.getOwner().getName());
          for (GamePiece piece : battlePieces) {
            if (piece.equals(chosenPiece)) {
              LogicFactory.getInstance().makeMove(chosenPieceForMove, chosenPiece,
                  chosenPiece.getLocation());
              break;
            }
          }
          Player attacker = game.getBattle().getAttacker().getOwner();
          Player defender = game.getBattle().getDefender().getOwner();
          Player winner = game.getBattle().getWinner().getOwner();
          if (attacker.equals(defender)) {
            // Self-attack
            showErrorMessage("Battle lost/won",
                "Unless self-sacrificing tactic is really a thing now... Oh well!");
          } else if (attacker.equals(winner)) {
            // Battle Won
            showErrorMessage("Battle won",
                defender.getName() + "'s feeble skills are no match for your power!");
          } else {
            // Battle Lost
            showErrorMessage("Battle lost",
                "I'm sorry. You are tough but " + defender.getName() + " is tougher...");
          }
          checkEndGame();
        } else {
          // GAME PIECE
          LOG.debug("Chosen piece: " + chosenPieceNum + " of " + chosenPlayer.getName());

          if (board.isInMovablePiecesList(movablePieces, chosenPiece)) {
            // If it is a movable piece, get possible moves
            board.setupMovablePieces(game, playerPieces, movablePieces,
                EnhancedColor.beautify(chosenPiece.getColor()));
            // Reset the color for cells
            board.setupPossibleMoves(possibleMoveChoices, perimeterCells, false);
            if (chosenPieceForMove != null && chosenPiece.equals(chosenPieceForMove)) {
              // Un-select the selected movable piece
              chosenObject.setFillColor(EnhancedColor.beautify(chosenPiece.getColor()));
              chosenPieceForMove = null;
              possibleMoveChoices = null;
            } else {
              // Select the movable piece
              chosenPieceForMove = chosenPiece;
              chosenObject
                  .setFillColor(EnhancedColor.beautify(chosenPiece.getColor()).darker().darker());
              possibleMoveChoices = LogicFactory.getInstance().possibleMove(chosenPiece);
              board.setupPossibleMoves(possibleMoveChoices, perimeterCells, true);
            }
          }
        }
      }
    }
  }

  /**
   * Save the currently displayed image to a file.
   */
  private void saveCurrentImage() {
    String fileName =
        getUserInput("Save Image to File", "Please enter the filename for the saved image");

    if (fileName != null && fileName.trim().length() > 0) {
      try {
        saveImage(fileName, ImageType.PNG);
      } catch (Throwable throwable) {
        showErrorMessage("Error Saving File", "Unable to save image to: " + fileName + "\n\n"
            + throwable.getClass() + ": " + throwable.getMessage());
      }
    }
  }

  @Override
  public void handleKeyCode(int keyCode) {
    if (keyCode == KeyEvent.VK_F2) {
      saveCurrentImage();
    }
  }

  /**
   * Allow a specific arrangement of piece locations to be set via a String array.
   * 
   * <p>
   * TODO This design is bad: there should be an official way to do this through the LogicFactory
   * </p>
   * 
   * <p>
   * The array is interpreted as follows:
   * </p>
   * 
   * <p>
   * An email address matching a player followed by 1 or more integers, each representing the number
   * of cell position to add to the current position of the piece. The values are applied to piece
   * numbers in order. If more values are supplied than there are pieces for a plyer, the extra
   * values are ignored.
   * </p>
   * 
   * <p>
   * For example (each line is an element of the array):
   * </p>
   * 
   * <p>
   * ann@skidmore.edu
   * </p>
   * 
   * <p>
   * 35
   * </p>
   * 
   * <p>
   * 35
   * </p>
   * 
   * <p>
   * 34
   * </p>
   * 
   * <p>
   * For a three player game this would place 2 of Ann's pieces in her home and one just below her
   * home.
   * </p>
   * 
   * @param initialPositionConfiguration The array matching the format described above
   */
  private void setInitialPiecePositions(String[] initialPositionConfiguration) {
    GameManipulator gm = new GameManipulator();
    LOG.debug("Checking arguments for position configuration details. Number of input arguments: "
        + initialPositionConfiguration.length);
    Map<String, Integer> playerMap = new HashMap<String, Integer>();
    for (int index = 0; index < game.getPlayers().length; ++index) {
      playerMap.put(game.getPlayers()[index].getEmail(), index);
    }
    Player currentPlayer = null;
    Integer foundPlayer;
    int currentPieceNumber = 0;
    for (String config : initialPositionConfiguration) {
      LOG.trace("Next command line argument: " + config);
      if ((foundPlayer = playerMap.get(config)) != null) {
        currentPlayer = game.getPlayers()[foundPlayer];
        currentPieceNumber = 1;
        LOG.debug("Matched player for position adjustment: " + currentPlayer);
      } else if (currentPlayer != null) {
        for (GamePiece piece : game.getGamePieceList()) {
          LOG.trace("Check owner of piece: " + piece);
          if (piece.getOwner().equals(currentPlayer) && piece.getNumber() == currentPieceNumber) {
            try {
              int adjustment = Integer.parseInt(config);
              LOG.debug("Adjust position of piece " + piece.getNumber() + " of " + currentPlayer
                  + " by " + adjustment + " starting at " + piece.getLocation());
              gm.movePiece(game, piece,
                  (piece.getLocation() + adjustment) % game.getGameBoard().getCellList().length);
              // piece.setLocation(
              // (piece.getLocation() + adjustment) % game.getGameBoard().getCellList().length);
              LOG.debug("Adjusted position of piece " + piece.getNumber() + " of " + currentPlayer
                  + " changed to " + piece.getLocation());
            } catch (NumberFormatException nfe) {
              LOG.warn("Illegal position value (should be a number): " + config, nfe);
            }
          }
        }
        ++currentPieceNumber;
      }
    }
  }

  /**
   * The game begins here.
   * 
   * @param args.
   */
  public static void main(String[] args) {
    new Main(args);
  }
}
