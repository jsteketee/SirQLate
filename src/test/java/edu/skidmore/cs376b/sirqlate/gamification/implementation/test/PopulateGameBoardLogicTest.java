package edu.skidmore.cs376b.sirqlate.gamification.implementation.test;

import edu.skidmore.cs376b.sirqlate.beans.implementation.GameBoard;
import edu.skidmore.cs376b.sirqlate.beans.implementation.HomeCell;
import edu.skidmore.cs376b.sirqlate.beans.implementation.StartCell;
import edu.skidmore.cs376b.sirqlate.gamification.implementation.PopulateGameBoardLogic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;



public class PopulateGameBoardLogicTest {
  /**
   * @author cenglish.
   */

  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();


  /**
   * An instance of the generator for testing.
   */
  private PopulateGameBoardLogic pgbl;



  @Before
  public void setup() {

    pgbl = new PopulateGameBoardLogic();
  }

  @Test
  public void testPopulateGameBoard() {
    GameBoard gameboard = new GameBoard(3);
    GameBoard gameboard2 = new GameBoard(3);
    GameBoard gameboard3 = new GameBoard(3);
    for (int tempi = 0; tempi < gameboard2.getCellList().length - 3; tempi++) {
      gameboard2.getCellList()[tempi] = new HomeCell(null, null);
    }

    for (int tempp = 0; tempp < gameboard3.getCellList().length - 3; tempp++) {
      gameboard3.getCellList()[tempp] = new StartCell(null, null);
    }

    pgbl.populateGameBoard(gameboard);
    pgbl.populateGameBoard(gameboard2);
    pgbl.populateGameBoard(gameboard3);


  }
}
