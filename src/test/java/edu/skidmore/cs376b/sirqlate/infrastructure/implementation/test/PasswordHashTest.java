package edu.skidmore.cs376b.sirqlate.infrastructure.implementation.test;

import static org.hamcrest.CoreMatchers.startsWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.infrastructure.implementation.PasswordHash;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mortbay.log.Log;

import java.util.Date;

public class PasswordHashTest {

  private Player player;
  private PasswordHash passwordhash;
  public Date date;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Setup the state for the tests.
   */
  @Before
  public void setup() {
    date = new Date();
    player = new Player("gabe@gmail.com", "Gabe", date, null, null);
    passwordhash = new PasswordHash();
  }

  @Test
  public void testPasswordIsNull1() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(startsWith("Password cannot be null"));

    passwordhash.createHashedPassword(player, null);
  }

  @Test
  public void testPassworsIsThere() {
    passwordhash.createHashedPassword(player, "password");
    Log.info(
        "'password' resulted in hash:" + player.getPasswordHash() + " salt:" + player.getSalt());
    assertNotNull("Password hash is null", player.getPasswordHash());
    assertNotNull("Salt is null", player.getSalt());
  }

  @Test
  public void testValidatePasswordT() {
    passwordhash.createHashedPassword(player, "password");
    assertTrue("Hash did not match", passwordhash.validatePassword(player, "password"));
  }

  @Test
  public void testValidatePasswordF() {
    passwordhash.createHashedPassword(player, "pasword");
    assertFalse("Hash did not match", passwordhash.validatePassword(player, "password"));
  }

  @Test
  public void testValidatePasswordNull1() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(startsWith("Password cannot be null"));
    passwordhash.validatePassword(player, null);
  }

  @Test
  public void testValidatePasswrdF() throws Exception {
    passwordhash.createHashedPassword(player, "asdfadtgeatga");
    passwordhash.validatePassword(player, "psword");
  }

}
