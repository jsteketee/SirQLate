package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Cell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.GamePiece;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.logic.implementation.MoveChoices;
import edu.skidmore.cs376b.sirqlate.logic.implementation.NewGameLogic;
import org.junit.Test;

public class MoveChoicesTest {
  NewGameLogic gameMaker = new NewGameLogic();
  String[] emailList = {"Bob@skidmore.edu", "Jack@skidmore.edu", "Ann@skidmore.edu"};
  Game gameTest = gameMaker.newGame(emailList);
  GameBoard gameBoardTest = gameTest.getGameBoard();
  MoveChoices moveChoiceLocal = new MoveChoices();
  Player[] list = gameTest.getPlayers();
  Player first = list[0];
  Player second = list[1];
  Player third = list[2];

  @Test
  public void testPossibleMoveChoices() {

    int[][] testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 3, 8, first, true);

    assertEquals("standard move forward failed for position", 4, testReturn[0][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[0][1]);

    assertEquals("standard move forward failed for position", 11, testReturn[7][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[7][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 0, 3, first, true);

    assertEquals("move into homecell failed for position", 2, testReturn[1][0]);
    assertEquals("move into homecell failed for status of move", 2, testReturn[1][1]);

    assertEquals("standard move forward failed for position", 4, testReturn[2][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[2][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 13, 8, first, true);

    assertEquals("standard move forward failed for position", 16, testReturn[0][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[0][1]);

    assertEquals("standard move forward failed for position", 23, testReturn[7][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[7][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 34, 8, first, true);

    assertEquals("move forward to homeCell failed for position", 2, testReturn[3][0]);
    assertEquals("move forward to homeCell for status of move", 2, testReturn[3][1]);

    assertEquals("standard move forward failed for position", 4, testReturn[4][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[4][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 0, -2, first, true);

    assertEquals("move forward to homeCell failed for position", 35, testReturn[0][0]);
    assertEquals("move forward to homeCell for status of move", 0, testReturn[0][1]);

    assertEquals("standard move forward failed for position", 34, testReturn[1][0]);
    assertEquals("standard move forward failed for status of move", 0, testReturn[1][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 5, -2, first, true);

    assertEquals("move forward to homeCell failed for position", 4, testReturn[0][0]);
    assertEquals("move forward to homeCell for status of move", 0, testReturn[0][1]);

    assertEquals("only one possible move should be present, as you cant move back to home", 1,
        testReturn.length);

    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 4, -2, first, true);

    assertEquals("move backwards past homecell should return no posible moves", true,
        testReturn == null);

    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 3, -2, first, true);

    assertEquals("move backwards at startcell should return no posible moves", true,
        testReturn == null);

  }

  @Test
  public void testBattlePosition() {
    GamePiece test1 = new GamePiece(7, null, first, 0);
    GamePiece test2 = new GamePiece(8, null, first, 0);
    Cell testCell = gameBoardTest.getCell(5);
    GamePiece[] testSpace = testCell.getGamePieceSpace();
    testSpace[0] = test1;
    testSpace[1] = test2;
    gameBoardTest.setCell(testCell, 5);
    gameBoardTest.setCell(testCell, 35);


    test1 = new GamePiece(7, null, second, 0);
    test2 = new GamePiece(8, null, second, 0);
    testCell = gameBoardTest.getCell(6);
    testSpace = testCell.getGamePieceSpace();
    testSpace[0] = test1;
    testSpace[1] = test2;
    gameBoardTest.setCell(testCell, 6);
    gameBoardTest.setCell(testCell, 34);


    test1 = new GamePiece(7, null, second, 0);
    testCell = gameBoardTest.getCell(7);
    testSpace = testCell.getGamePieceSpace();
    testSpace[0] = test1;
    gameBoardTest.setCell(testCell, 7);
    gameBoardTest.setCell(testCell, 33);



    int[][] testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 4, 3, first, true);

    assertEquals("battle needed forward failed for position", 6, testReturn[0][0]);
    assertEquals("battle needed forward failed for status of move", 1, testReturn[0][1]);

    assertEquals("one piece taken forward failed for position", 7, testReturn[1][0]);
    assertEquals("one piece forward failed for status of move", 0, testReturn[1][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 7, -2, first, true);

    assertEquals("battle needed back failed for position", 6, testReturn[0][0]);
    assertEquals("battle needed back failed for status of move", 1, testReturn[0][1]);

    assertEquals("only one possible move should be present, as you cant move into a full cell", 1,
        testReturn.length);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 8, -1, first, false);

    assertEquals("one piece taken back failed for position", 7, testReturn[0][0]);
    assertEquals("one piece back failed for status of move", 0, testReturn[0][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 35, 8, first, true);

    assertEquals("battle needed forward failed for position", 6, testReturn[4][0]);
    assertEquals("battle needed forward failed for status of move", 1, testReturn[4][1]);

    assertEquals("one piece taken forward failed for position", 7, testReturn[5][0]);
    assertEquals("one piece forward failed for status of move", 0, testReturn[5][1]);


    testReturn = moveChoiceLocal.possibleMoveChoices(gameBoardTest, 0, -3, first, true);

    assertEquals("move back into battle around board failed for position", 34, testReturn[0][0]);
    assertEquals("move back into battle around board failed for staus", 1, testReturn[0][1]);

    assertEquals("one piece taken back around board failed for position", 33, testReturn[1][0]);
    assertEquals("one piece taken back around baord failed for status of move", 0,
        testReturn[1][1]);
  }



  @Test
  public void testGetHomeCellPosition() {

    assertEquals("Value incorrect for position of first player's homecell", 2,
        moveChoiceLocal.getHomeCellPosition(gameBoardTest, first));
    assertEquals("Value incorrect for position of second player's homecell", 14,
        moveChoiceLocal.getHomeCellPosition(gameBoardTest, second));
    assertEquals("Value incorrect for position of third player's homecell", 26,
        moveChoiceLocal.getHomeCellPosition(gameBoardTest, third));


    Player fake = new Player();
    assertEquals("Value incorrect for non existing player's homecell", -1,
        moveChoiceLocal.getHomeCellPosition(gameBoardTest, fake));
  }

  @Test
  public void testGetStartCellPosition() {
    assertEquals("Value incorrect for position of first player's startcell", 3,
        moveChoiceLocal.getStartCellPosition(gameBoardTest, first));
    assertEquals("Value incorrect for position of second player's startcell", 15,
        moveChoiceLocal.getStartCellPosition(gameBoardTest, second));
    assertEquals("Value incorrect for position of third player's startcell", 27,
        moveChoiceLocal.getStartCellPosition(gameBoardTest, third));

    Player fake = new Player();
    assertEquals("Value incorrect for non existing player's startcell", -1,
        moveChoiceLocal.getStartCellPosition(gameBoardTest, fake));
  }

}
