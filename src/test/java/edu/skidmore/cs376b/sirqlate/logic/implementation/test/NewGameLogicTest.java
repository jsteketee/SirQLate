package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.logic.implementation.NewGameLogic;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Junit tests for NewGameLogic class.
 * 
 * @author jstekete
 *
 */
public class NewGameLogicTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  NewGameLogic newGame = new NewGameLogic();

  Player[] players = {new Player("jstekete@", "jack", null, "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4="),
      new Player("dread@", "david", null, "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4="),
      new Player("pres@", "obama", null, "+RW1MFx1lm4Vn6mCLc/HTw==", "5voLxa4=")};


  @Test
  public void newGameNullPlayerList() {
    String[] emailList = null;
    thrown.expect(NullPointerException.class);
    newGame.newGame(emailList);
  }

  @Test
  public void newGamePlayerListToLarge() {
    String[] emailList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    thrown.expect(IllegalArgumentException.class);
    thrown
        .expectMessage(startsWith("The given number of players does not match the allowed range."));
    newGame.newGame(emailList);
  }

  @Test
  public void newGamePlayerListToSmall() {
    String[] emailList = {"1", "2"};
    thrown.expect(IllegalArgumentException.class);
    thrown
        .expectMessage(startsWith("The given number of players does not match the allowed range."));
    newGame.newGame(emailList);
  }
  
  //tests create new game using emails. 
  @Test
  public void newGameValidPlayerListEmails() {
    String[] emailList = {"Bob@skidmore.edu", "Jack@skidmore.edu", "Ann@skidmore.edu"};
    Game game = newGame.newGame(emailList);
    System.out.println("NewGameValidEmailListTest:");
    System.out.println();
    System.out.println(game.getGameBoard().toString());
    System.out.println(game.getMoveablePieces());
  }

  // tests create new game using players.
  @Test
  public void newGameValidPlayerList() {
    // TODO Unit tests must use assertions not printouts - error detection needs to be automated
    Game game = newGame.newGame(players);
    System.out.println("NewGameValidPlayerListTest:");
    System.out.println();
    System.out.println(game.getGameBoard().toString());
  }
  
  //This test is here just to make sure that persistence is working correctly. 
  @Test
  public void testRetreivePlayer() {
    Player player = PersistenceFactory.getInstance().retrievePlayer("bob@skidmore.edu");
    assertEquals("player not retrieved", player.getName(), "Bob");
    player = PersistenceFactory.getInstance().retrievePlayer("ann@skidmore.edu");
    assertEquals("player not retrieved", player.getName(), "Ann");
    player = PersistenceFactory.getInstance().retrievePlayer("jack@skidmore.edu");
    assertEquals("player not retrieved", player.getName(), "Jack");
  }
}
