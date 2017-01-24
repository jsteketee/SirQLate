package edu.skidmore.cs376b.sirqlate.frontend.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.HomeCell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;

import us.daveread.edu.graphics.shape.impl.Circle;
import us.daveread.edu.graphics.shape.impl.Image;
import us.daveread.edu.graphics.shape.impl.Rectangle;
import us.daveread.edu.graphics.shape.impl.Text;
import us.daveread.edu.graphics.surface.DrawingSurface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Draw the board with all of the decorations (points, pieces, items).
 * 
 * @author James Armenta, An Phi (aphi).
 *
 */
public class Board {

  /**
   * Path for Image files.
   */
  private static final String IMAGE_PATH = "assets/images/";

  /**
   * File names for die images.
   */
  private final String[] dieImages = {"die_neg02_42159_mth.82.gif", "die_neg01_42158_mth.82.gif",
      "die_00_42158_mth.82.gif", "die_01_42158_mth.82.gif", "die_02_42159_mth.82.gif",
      "die_03_42160_mth.82.gif", "die_04_42161_mth.82.gif", "die_05_42162_mth.82.gif",
      "die_06_42164_mth.82.gif", "die_07_42164_mth.82.gif", "die_08_42164_mth.82.gif"};

  /**
   * Pixel width of a die image.
   */
  private static final int DIE_IMAGE_WIDTH = 82;

  /**
   * Pixel height of a die image.
   */
  private static final int DIE_IMAGE_HEIGHT = 100;

  /**
   * Background color for die (white recommended).
   */
  private static Color DIE_BACKGROUND_COLOR;

  /**
   * The window width.
   */
  private static final int WINDOW_WIDTH = 800;

  /**
   * The window height.
   */
  private static final int WINDOW_HEIGHT = 600;

  /**
   * The window top Margin.
   */
  private static final int WINDOW_MARGIN_TOP = 20;

  /**
   * Background color of the game.
   */
  private static Color BACKGROUND_COLOR;

  /**
   * Number of sides on the board.
   */
  private static int NUM_SIDES = 3;

  /**
   * Number of cells per side.
   */
  private static int NUM_CELLS_PER_SIDE = 10;

  /**
   * Pixel side length of a cell.
   */
  private static final int CELL_LEN = 50;

  /**
   * Pixel separation length between cells.
   */
  private static final int CELL_SEPARATION = 2;

  /**
   * Fill color of the cell.
   */
  private static Color CELL_FILL_COLOR;

  /**
   * Border color of the cell.
   */
  private static Color CELL_BORDER_COLOR;

  /**
   * Index of the home cell.
   */
  private static final int HOME_CELL_INDEX = 2;

  /**
   * Diameter for a player's piece.
   */
  private static final int PIECE_DIAMETER = 15;

  /**
   * Spacing between pieces and the edge of the containing cell.
   */
  private static final int PIECE_DISTANCE_FROM_EDGE = 5;

  /**
   * Maximum number of pieces for a cell - used for position calculation.
   */
  private static final int PIECE_MAX_CAPACITY_PER_CELL = 3;

  /**
   * Border color of game piece.
   */
  private static Color PIECE_BORDER_COLOR;

  /**
   * Reserved height for player's name label.
   */
  private static final int LABEL_AREA_HEIGHT = 40;

  /**
   * Font size for players' labels.
   */
  private static final int LABEL_FONT_SIZE = 20;



  /**
   * The height for each item image.
   */
  private static final int ITEM_IMAGE_LENGTH = 33;

  /**
   * Fill color for the highlighted objects.
   */
  private static Color HIGHLIGHT_FILL_COLOR;

  /**
   * Border color for the highlighted objects.
   */
  private static Color HIGHLIGHT_BORDER_COLOR;

  /**
   * Color for the permanent points.
   */
  private static Color PERM_POINT_COLOR;

  /**
   * Color for the temporary points.
   */
  private static Color TEMP_POINT_COLOR;

  /**
   * The set of piece counter for each perimeter cells.
   */
  private int[] perimeterCellsPieceCounter;

  /**
   * Initialize some parameters.
   */
  public Board() {
    BACKGROUND_COLOR = EnhancedColor.BLACK.getColor().brighter();
    DIE_BACKGROUND_COLOR = EnhancedColor.GRAY.getColor().brighter();
    CELL_FILL_COLOR = Color.LIGHT_GRAY;
    CELL_BORDER_COLOR = EnhancedColor.BLACK.getColor();
    PERM_POINT_COLOR = EnhancedColor.beautify(Color.BLACK);
    PIECE_BORDER_COLOR = EnhancedColor.BLACK.getColor();
    TEMP_POINT_COLOR = EnhancedColor.LIME.getColor().darker().darker();
    HIGHLIGHT_FILL_COLOR = EnhancedColor.YELLOW.getColor();
    HIGHLIGHT_BORDER_COLOR = EnhancedColor.YELLOW.getColor().brighter();
  }

  /**
   * Create and layout the die image.
   */
  /**
   * @param surface the drawing surface
   * @param game the current game
   * @return the die image.
   */
  public Image setupDie(DrawingSurface surface, Game game) {
    Image die = new Image(IMAGE_PATH + "die/" + dieImages[game.getRoll() + 2],
        new Point(surface.getWidth() / 2 - DIE_IMAGE_WIDTH / 2,
            WINDOW_MARGIN_TOP + surface.getHeight() / 2 - DIE_IMAGE_HEIGHT / 2 - LABEL_AREA_HEIGHT),
        DIE_BACKGROUND_COLOR);
    surface.add(die);
    return die;
  }

  /**
   * Calculate the next position.
   * 
   * @param position current position.
   * @param angleInRad the angle from the current position.
   * @param distance the distance of movement.
   * @return the next position.
   */
  private int[] getNextPosition(int[] position, double angleInRad, int distance) {
    int[] result = new int[2];
    result[0] = (int) (position[0] + distance * Math.cos(angleInRad));
    result[1] = (int) (position[1] + distance * Math.sin(angleInRad));
    return result;
  }

  /**
   * Setup Background for the game.
   * 
   * @param surface the drawing surface.
   * @return Rectangle background.
   */
  public Rectangle setupBackground(DrawingSurface surface) {
    Rectangle background = new Rectangle(new Point(0, 0),
        new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), BACKGROUND_COLOR, BACKGROUND_COLOR);
    surface.add(background);
    return background;
  }

  /**
   * Create and layout the pieces for the start of a game.
   *
   * @param surface the drawing surface.
   * @param game the current game.
   * @param perimeterCells the array of cells.
   * @return the array of pieces.
   */
  public Circle[] setupPieces(DrawingSurface surface, Game game, Circle[] perimeterCells) {
    perimeterCellsPieceCounter = new int[(NUM_CELLS_PER_SIDE + 2) * NUM_SIDES]; // Reset the count
    ArrayList<GamePiece> pieces = game.getGamePieceList();
    Circle[] playerPieces = new Circle[pieces.size()];
    for (int index = 0; index < pieces.size(); ++index) {
      int containingCellIndex = pieces.get(index).getLocation();
      perimeterCellsPieceCounter[containingCellIndex]++; // Increase the count
      playerPieces[index] = new Circle(
          calculateLocationForPiece(containingCellIndex,
              perimeterCellsPieceCounter[containingCellIndex], perimeterCells),
          PIECE_DIAMETER, PIECE_BORDER_COLOR, EnhancedColor.beautify(pieces.get(index).getColor()));
      surface.add(playerPieces[index]);
    }
    return playerPieces;
  }

  /**
   * Calculate the position of the pieces in cell.
   * 
   * @param cellNumber the cell the piece is being placed in
   * @param cellPieceCounter the number of the piece currently in the cell
   * @param perimeterCells the array of cells.
   * @return point where the piece is to be placed (top left corner)
   */
  private Point calculateLocationForPiece(int cellNumber, int cellPieceCounter,
      Circle[] perimeterCells) {
    Point cellLocation = perimeterCells[cellNumber].getLocation();
    double cellCentX = cellLocation.getX() + (CELL_LEN - CELL_SEPARATION) / 2;
    double cellCentY = cellLocation.getY() + (CELL_LEN - CELL_SEPARATION) / 2;
    int[] result = new int[] {(int) cellCentX, (int) cellCentY};
    result = getNextPosition(result, (2 * Math.PI / PIECE_MAX_CAPACITY_PER_CELL) * cellPieceCounter,
        (CELL_LEN - CELL_SEPARATION - PIECE_DIAMETER) / 2 - PIECE_DISTANCE_FROM_EDGE);
    return new Point(result[0] - PIECE_DIAMETER / 2, result[1] - PIECE_DIAMETER / 2);
  }


  /**
   * Populate the current table with items.
   * 
   * @param surface the drawing surface.
   * @param game the current game.
   * @param perimeterCells the array of cells.
   * @return the list of item images.
   */
  public ArrayList<Image> setupItems(DrawingSurface surface, Game game, Circle[] perimeterCells) {
    Cell[] cells = game.getGameBoard().getCellList();
    ArrayList<Image> itemImages = new ArrayList<Image>();
    for (int index = 0; index < cells.length; ++index) {
      if (cells[index].hasItem()) {
        // addMessage("Cell index " + index + " has item " + cells[index].getItem().getName());
        Image item = new Image(IMAGE_PATH + "item/" + cells[index].getItem().getName() + ".png",
            new Point(
                perimeterCells[index].getLocation().x
                    + (CELL_LEN - CELL_SEPARATION - ITEM_IMAGE_LENGTH) / 2,
                perimeterCells[index].getLocation().y
                    + (CELL_LEN - CELL_SEPARATION - ITEM_IMAGE_LENGTH) / 2),
            null);
        item.setClickable(false);
        itemImages.add(item);
      }
    }
    for (Image item : itemImages) {
      if (item != null) {
        surface.add(item);
      }
    }
    return itemImages;
  }

  /**
   * Populate the current table with points.
   * 
   * @param surface the drawing surface.
   * @param game the current game.
   * @param perimeterCells the array of cells.
   * @return the list of point texts.
   */
  public ArrayList<Text> setupPoints(DrawingSurface surface, Game game, Circle[] perimeterCells) {
    Cell[] cells = game.getGameBoard().getCellList();
    ArrayList<Text> pointTexts = new ArrayList<Text>();
    for (int index = 0; index < cells.length; ++index) {
      // Item override the displays of point in each cell
      if (cells[index].getPoints().getPoints() != 0 && !cells[index].hasItem()) {
        Color color = cells[index].isPermPoint() ? PERM_POINT_COLOR : TEMP_POINT_COLOR;
        Text point = new Text(Long.toString(cells[index].getPoints().getPoints()), new Point(0, 0),
            14, 0, null, color);
        point.setLocation(new Point(
            perimeterCells[index].getLocation().x
                + (CELL_LEN - CELL_SEPARATION - (int) point.getTextDimension().getWidth()) / 2,
            perimeterCells[index].getLocation().y
                + (CELL_LEN - CELL_SEPARATION + (int) point.getTextDimension().getHeight()) / 2));
        point.setClickable(false);
        pointTexts.add(point);
      }
    }
    for (Text point : pointTexts) {
      if (point != null) {
        surface.add(point);
      }
    }
    return pointTexts;
  }


  /**
   * Create and layout the cells.
   * 
   * @param surface the drawing surface
   * @param game the current game
   * @return several arrays of cells, including perimeter cells, home cells and start cells.
   */
  public ArrayList<Circle[]> setupBoard(DrawingSurface surface, Game game) {
    ArrayList<Circle[]> result = new ArrayList<Circle[]>();
    // We will make dummy head at the beginning and end of each side for better drawing
    int currentX = surface.getWidth() / 2 - (CELL_LEN * (NUM_CELLS_PER_SIDE + 2) / 2);
    int currentY = WINDOW_MARGIN_TOP + LABEL_AREA_HEIGHT;
    int[] currentPosition = new int[] {currentX, currentY};
    int[] auxPosition = new int[] {0, 0};

    Circle[] perimeterCells = new Circle[(NUM_CELLS_PER_SIDE + 2) * NUM_SIDES];
    Circle[] startCells = new Circle[NUM_SIDES];
    Circle[] homeCells = new Circle[NUM_SIDES];

    for (int side = 0; side < NUM_SIDES; ++side) {
      for (int cell = 0; cell < NUM_CELLS_PER_SIDE + 2; ++cell) {
        if (cell == 0) {
          // Start of edge, skip the Dummy Cell
          currentPosition = getNextPosition(currentPosition,
              side * 2 * (Math.PI / NUM_SIDES - Math.PI), CELL_LEN);
          perimeterCells[side * (NUM_CELLS_PER_SIDE + 2) + cell] =
              new Circle(new Point(currentPosition[0], currentPosition[1]),
                  CELL_LEN - CELL_SEPARATION, CELL_BORDER_COLOR, CELL_FILL_COLOR);
          currentPosition = getNextPosition(currentPosition,
              side * 2 * (Math.PI / NUM_SIDES - Math.PI), CELL_LEN);
        } else if (cell == HOME_CELL_INDEX - 1) {
          // Perimeter Cell ABOVE Home Cell
          perimeterCells[side * (NUM_CELLS_PER_SIDE + 2) + cell] =
              new Circle(new Point(currentPosition[0], currentPosition[1]),
                  CELL_LEN - CELL_SEPARATION, CELL_BORDER_COLOR, CELL_FILL_COLOR);
          auxPosition = getNextPosition(currentPosition,
              (Math.PI / 2) + side * 2 * (Math.PI / NUM_SIDES - Math.PI), CELL_LEN);
          currentPosition = getNextPosition(currentPosition,
              side * 2 * (Math.PI / NUM_SIDES - Math.PI), CELL_LEN);
        } else if (cell == HOME_CELL_INDEX) {
          // Home Cell
          homeCells[side] =
              new Circle(new Point(auxPosition[0], auxPosition[1]),
                  CELL_LEN
                      - CELL_SEPARATION,
                  CELL_BORDER_COLOR, EnhancedColor.beautify(((HomeCell) game.getGameBoard()
                      .getCell(side * (NUM_CELLS_PER_SIDE + 2) + cell)).getColor()).brighter());
          perimeterCells[side * (NUM_CELLS_PER_SIDE + 2) + cell] = homeCells[side];
        } else if (cell == HOME_CELL_INDEX + 1) {
          // Start Cell
          auxPosition =
              getNextPosition(auxPosition, side * 2 * (Math.PI / NUM_SIDES - Math.PI), CELL_LEN);
          startCells[side] =
              new Circle(new Point(auxPosition[0], auxPosition[1]),
                  CELL_LEN
                      - CELL_SEPARATION,
                  CELL_BORDER_COLOR, EnhancedColor.beautify(((StartCell) game.getGameBoard()
                      .getCell(side * (NUM_CELLS_PER_SIDE + 2) + cell)).getColor()).darker());
          perimeterCells[side * (NUM_CELLS_PER_SIDE + 2) + cell] = startCells[side];
        } else {
          // Perimeter Cell
          perimeterCells[side * (NUM_CELLS_PER_SIDE + 2) + cell] =
              new Circle(new Point(currentPosition[0], currentPosition[1]),
                  CELL_LEN - CELL_SEPARATION, CELL_BORDER_COLOR, CELL_FILL_COLOR);
          currentPosition = getNextPosition(currentPosition,
              side * 2 * (Math.PI / NUM_SIDES - Math.PI), CELL_LEN);
        }
      }
    }

    result.add(perimeterCells);
    result.add(homeCells);
    result.add(startCells);

    for (Circle cell : perimeterCells) {
      if (cell != null) {
        surface.add(cell);
      }
    }

    for (Circle home : homeCells) {
      if (home != null) {
        surface.add(home);
      }
    }

    for (Circle start : startCells) {
      if (start != null) {
        surface.add(start);
      }
    }

    return result;
  }


  /**
   * Draw Players' Label.
   * 
   * @param surface the drawing surface.
   * @param game the current game.
   * @param perimeterCells the array of cells.
   * @return Text[]
   */
  public Text[] setupPlayerLabels(DrawingSurface surface, Game game, Circle[] perimeterCells) {
    Text[] playerLabels = new Text[NUM_SIDES];
    int currentX;
    int currentY;
    int[] currentPosition;
    double heightLabel;
    double widthLabel;
    Text tempLabel;
    Color colorLabel;

    for (int index = 0; index < NUM_SIDES; ++index) {
      tempLabel = new Text(game.getPlayers()[index].getName(), new Point(500, 500), LABEL_FONT_SIZE,
          0, Color.BLACK, Color.BLACK);
      heightLabel = tempLabel.getTextDimension().getHeight();
      widthLabel = tempLabel.getTextDimension().getWidth();

      // Find the center of the text label
      if (NUM_CELLS_PER_SIDE % 2 == 0) {
        currentX = (perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + (NUM_CELLS_PER_SIDE + 2) / 2]
            .getLocation().x
            + perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + (NUM_CELLS_PER_SIDE + 2) / 2 + 1]
                .getLocation().x)
            / 2 + (CELL_LEN - CELL_SEPARATION) / 2;
        currentY = (perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + (NUM_CELLS_PER_SIDE + 2) / 2]
            .getLocation().y
            + perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + (NUM_CELLS_PER_SIDE + 2) / 2 + 1]
                .getLocation().y)
            / 2 + (CELL_LEN - CELL_SEPARATION) / 2;
      } else {
        currentX = perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + (NUM_CELLS_PER_SIDE + 2) / 2]
            .getLocation().x + (CELL_LEN - CELL_SEPARATION) / 2;
        currentY = perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + (NUM_CELLS_PER_SIDE + 2) / 2]
            .getLocation().y + (CELL_LEN - CELL_SEPARATION) / 2;
      }


      currentPosition = getNextPosition(new int[] {currentX, currentY},
          -(Math.PI / 2) + index * 2 * (Math.PI / NUM_SIDES),
          (CELL_LEN - CELL_SEPARATION) / 2 + (int) heightLabel);

      colorLabel = ((HomeCell) (game.getGameBoard().getCell(index * (NUM_CELLS_PER_SIDE + 2) + 2)))
          .getColor();
      playerLabels[index] = new Text(game.getPlayers()[index].getName(),
          new Point(currentPosition[0] - (int) widthLabel / 2,
              currentPosition[1] + (int) heightLabel / 2),
          20, index * 2 * (180 / NUM_SIDES),
          game.getPlayers()[index].equals(game.getCurrentPlayer()) ? HIGHLIGHT_FILL_COLOR : null,
          EnhancedColor.beautify(colorLabel).brighter());
    }

    for (Text text : playerLabels) {
      surface.add(text);
    }
    return playerLabels;
  }

  /**
   * Create the buttons for each player's inventory.
   * 
   * @param surface the drawing surface
   * @param playerLabels the text labels of current players
   * @return the inventory button image.
   */
  public Circle[] setupInventory(DrawingSurface surface, Text[] playerLabels) {
    Circle[] inventoryButtons = new Circle[NUM_SIDES];
    for (int index = 0; index < NUM_SIDES; ++index) {
      int[] currentPosition = new int[] {
          (int) (playerLabels[index].getLocation().getX()
              + playerLabels[index].getTextDimension().getWidth() / 2 - LABEL_FONT_SIZE / 2),
          (int) (playerLabels[index].getLocation().getY()
              - playerLabels[index].getTextDimension().getHeight() / 2 - LABEL_FONT_SIZE / 2)};

      currentPosition = getNextPosition(currentPosition, index * 2 * (Math.PI / NUM_SIDES),
          (int) (playerLabels[index].getTextDimension().getWidth() / 2) + LABEL_FONT_SIZE);

      Circle inventoryButton = new Circle(new Point(currentPosition[0], currentPosition[1]),
          LABEL_FONT_SIZE, Color.BLACK, playerLabels[index].getFillColor());
      inventoryButtons[index] = inventoryButton;
    }

    for (Circle button : inventoryButtons) {
      if (button != null) {
        surface.add(button);
      }
    }

    return inventoryButtons;
  }

  /**
   * Fill the movable pieces with the assigned color.
   * 
   * @param game the current game.
   * @param playerPieces array of game pieces.
   * @param movablePieces array of movable pieces.
   * @param fillColor the assigned color.
   */
  public void setupMovablePieces(Game game, Circle[] playerPieces,
      ArrayList<GamePiece> movablePieces, Color fillColor) {
    ArrayList<GamePiece> pieces = game.getGamePieceList();
    for (int index = 0; index < pieces.size(); ++index) {
      if (isInMovablePiecesList(movablePieces, pieces.get(index))) {
        if (fillColor != null) {
          playerPieces[index].setFillColor(fillColor);
        }
        playerPieces[index].setBorderColor(HIGHLIGHT_BORDER_COLOR);
      }
    }
  }

  /**
   * Check if the selected cell is in the possible move array or not
   * 
   * @param possibleMoveChoices array of possible move locations.
   * @param index the index of the cell.
   * @return boolean
   */
  public boolean isInPossibleMovesList(int[][] possibleMoveChoices, int index) {
    if (possibleMoveChoices == null) {
      return false;
    }
    for (int i = 0; i < possibleMoveChoices.length; ++i) {
      if (possibleMoveChoices[i][0] == index) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the piece is in the list of movable pieces.
   * 
   * @param movablePieces array of movable pieces.
   * @param piece the piece being checked.
   * @return boolean
   */
  public boolean isInMovablePiecesList(ArrayList<GamePiece> movablePieces, GamePiece piece) {
    if (movablePieces == null) {
      return false;
    }
    for (GamePiece movablePiece : movablePieces) {
      if (piece.equals(movablePiece)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Fill the possible moves cells with the assigned color.
   * 
   * @param possibleMoveChoices array of possible move locations.
   * @param perimeterCells array of cells.
   * @param isHighlighted indicates if possible moves should be highlighted or not.
   */
  public void setupPossibleMoves(int[][] possibleMoveChoices, Circle[] perimeterCells,
      boolean isHighlighted) {
    if (possibleMoveChoices == null) {
      return;
    }
    Color fillColor;
    if (!isHighlighted) {
      fillColor = CELL_FILL_COLOR;
    } else {
      fillColor = HIGHLIGHT_FILL_COLOR;
    }
    for (int i = 0; i < possibleMoveChoices.length; ++i) {
      perimeterCells[possibleMoveChoices[i][0]].setFillColor(fillColor);
      // perimeterCells[possibleMoveChoices[i][0]].setBorderColor(fillColor.darker());
    }
  }

  /**
   * Check if the selected move potentially trigger a battle
   * 
   * @param possibleMoveChoices array of possible move locations.
   * @param index the index of the cell.
   * @return boolean
   */
  public boolean hasBattle(int[][] possibleMoveChoices, int index) {
    if (possibleMoveChoices == null) {
      return false;
    }
    for (int i = 0; i < possibleMoveChoices.length; ++i) {
      if (possibleMoveChoices[i][0] == index) {
        if (possibleMoveChoices[i][1] == 1) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Highlight the battle cell.
   * 
   * @param perimeterCells the array of cells.
   * @param index the location of the cell.
   */
  public void highlightBattleCell(Circle[] perimeterCells, int index) {
    perimeterCells[index].setFillColor(HIGHLIGHT_FILL_COLOR.darker());
  }

  /**
   * Check for available opponents. TODO: What if the cell has one piece of the same player
   * 
   * @param game the current game.
   * @param index index the location of the battle.
   * @return GamePiece[] the array of opponents' pieces.
   */
  public GamePiece[] getOpponent(Game game, int index) {
    ArrayList<GamePiece> result = new ArrayList<GamePiece>();
    for (GamePiece piece : game.getGamePieceList()) {
      if (piece.getLocation() == index) {
        result.add(piece);
      }
    }
    return result.toArray(new GamePiece[result.size()]);
  }

  /**
   * This method reset drawn objects to prepare for draw out next game state.
   *
   * @param surface the drawing surface.
   * @param game the current game.
   * @param possibleMoveChoices array of possible move locations.
   * @param perimeterCells array of cells.
   * @param playerLabels array of player labels.
   * @param playerPieces array of game pieces.
   * @param pointTexts array of points.
   * @param itemImages array of items.
   */
  public void cleanBoard(DrawingSurface surface, Game game, int[][] possibleMoveChoices,
      Circle[] perimeterCells, Text[] playerLabels, Circle[] playerPieces,
      ArrayList<Text> pointTexts, ArrayList<Image> itemImages) {
    // Reset the color of the possible move cells
    setupPossibleMoves(possibleMoveChoices, perimeterCells, false);
    // Reset color for home cells
    for (int index = 0; index < NUM_SIDES; ++index) {
      perimeterCells[index * (NUM_CELLS_PER_SIDE + 2) + HOME_CELL_INDEX]
          .setFillColor(EnhancedColor
              .beautify(((HomeCell) game.getGameBoard()
                  .getCell(index * (NUM_CELLS_PER_SIDE + 2) + HOME_CELL_INDEX)).getColor())
              .brighter());
    }
    // Remove the playerLabels
    if (playerLabels != null) {
      for (Text label : playerLabels) {
        if (label != null) {
          surface.remove(label);
        }
      }
    }
    // Remove the game pieces
    if (playerPieces != null) {
      for (Circle piece : playerPieces) {
        if (piece != null) {
          surface.remove(piece);
        }
      }
    }
    // Remove the points
    if (pointTexts != null) {
      for (Text point : pointTexts) {
        if (point != null) {
          surface.remove(point);
        }
      }
    }
    // Remove the items
    if (itemImages != null) {
      for (Image item : itemImages) {
        if (item != null) {
          surface.remove(item);
        }
      }
    }
  }

  /**
   * The right window margin.
   */
  private static final int WINDOW_MARGIN_RIGHT = WINDOW_WIDTH - 100;

  /**
   * Resuklts screen vertical spacing.
   */
  private static final int RESULTS_VERTICAL_SPACING = 100;

  /**
   * Reults screen horizontal spacing.
   */
  private static final int RESULTS_HORIZONTAL_SPACING = 50;

  /**
   * Results screen font size.
   */
  private static final int RESULTS_FONT_SIZE = 15;
  
  /**
   * The left window margin.
   */
  private static final int WINDOW_MARGIN_LEFT = 20;
  

  public void setupResults(DrawingSurface surface, Player[] places, Game game) {

    surface.add(new Text("RESULTS: " + game.getWinner() + " has finished first!",
        new Point(WINDOW_MARGIN_LEFT + RESULTS_HORIZONTAL_SPACING, WINDOW_MARGIN_TOP + 40),
        RESULTS_FONT_SIZE + 20, Color.WHITE));

    surface.add(new Text("Points rankings:",
        new Point(WINDOW_MARGIN_LEFT + RESULTS_HORIZONTAL_SPACING, WINDOW_MARGIN_TOP + 70),
        RESULTS_FONT_SIZE + 10, Color.WHITE));

    int verticalSpace = 100;

    for (int i = 0; i < places.length; i++) {
      surface.add(new Text(Integer.toString(i + 1) + "-",
          new Point(WINDOW_MARGIN_LEFT, WINDOW_MARGIN_TOP + verticalSpace), RESULTS_FONT_SIZE,
          Color.WHITE));

      surface.add(
          new Text(places[i].getName() + " (" + places[i].getGamePlayed() + " total games played):",
              new Point(WINDOW_MARGIN_LEFT + RESULTS_HORIZONTAL_SPACING,
                  WINDOW_MARGIN_TOP + verticalSpace),
              RESULTS_FONT_SIZE, Color.WHITE));

      surface.add(new Text(Long.toString(places[i].getPoints().getPoints()) + " points",
          new Point(WINDOW_MARGIN_RIGHT - RESULTS_HORIZONTAL_SPACING,
              WINDOW_MARGIN_TOP + verticalSpace),
          RESULTS_FONT_SIZE, Color.WHITE));

      verticalSpace += RESULTS_VERTICAL_SPACING;

    }
  }

  public Image[] setupResultsInventories(DrawingSurface surface, Player[] places) {
    Image[] invimages = new Image[places.length];
    int verticalSpace = 100;

    for (int i = 0; i < places.length; i++) {
      invimages[i] = new Image(IMAGE_PATH + "buttons/ResultsInvButton.png",
          new Point(WINDOW_MARGIN_LEFT + RESULTS_HORIZONTAL_SPACING,
              (WINDOW_MARGIN_TOP + 10) + verticalSpace),
          Color.black);
      verticalSpace += RESULTS_VERTICAL_SPACING;
    }

    for (int i = 0; i < invimages.length; i++) {
      surface.add(invimages[i]);
    }
    return invimages;
  }

  public Image setupEndButton(DrawingSurface surface) {
    Image endButton = new Image(IMAGE_PATH + "buttons/EndButton.png",
        new Point(WINDOW_MARGIN_RIGHT - 20, WINDOW_HEIGHT - 100), Color.black);
    surface.add(endButton);
    return endButton;
  }
}
