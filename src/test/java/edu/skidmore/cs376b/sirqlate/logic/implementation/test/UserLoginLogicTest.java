package edu.skidmore.cs376b.sirqlate.logic.implementation.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.infrastructure.factory.InfrastructureFactory;
import edu.skidmore.cs376b.sirqlate.logic.implementation.UserLoginLogic;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.UserLogin;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

/**
 * This is a test class to test UserLoginLogic.
 * 
 * @author xhuang1
 */

public class UserLoginLogicTest {
  /**
   * Exception detection rule.
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static UserLogin login = new UserLoginLogic();

  private Player player = new Player("12345@skidmore.edu", 3, "12345", new Date(), new Points(5),
      null, null, null,null);

  /**
   * set up.
   */
  @Before
  public void setup() {
    
    InfrastructureFactory.getInstance().createHashedPassword(player, "password");
    PersistenceFactory.getInstance().persistPlayer(player);
  }

  @Test
  public void signUpNeededTrue() {

    assertTrue("Incorrect signal for SignedUpNeeded", login.signUpNeeded("123@gmail.com"));
  }

  @Test
  public void signupNeedFalse() {

    assertFalse("Incorrect signal for SignedUpNeeded",
        login.signUpNeeded(player.getEmail()));
  }

  @Test
  public void userPasswordMatchedTrue() {
    assertTrue("Incorrect Password matching",
        login.userPasswordMatched("12345@skidmore.edu", "password"));
  }

  @Test
  public void userPasswordMatchedFalse() {
    assertFalse("Incorrect Password matching",
        login.userPasswordMatched("12345@skidmore.edu", "passwor"));
  }

}
