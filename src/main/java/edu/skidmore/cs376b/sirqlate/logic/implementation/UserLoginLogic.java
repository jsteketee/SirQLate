package edu.skidmore.cs376b.sirqlate.logic.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.infrastructure.factory.InfrastructureFactory;
import edu.skidmore.cs376b.sirqlate.logic.interfaces.UserLogin;
import edu.skidmore.cs376b.sirqlate.persistence.factory.PersistenceFactory;

/**
 * This class contains the logic to authenticate a user.
 * 
 * @author xhuang1
 *
 */

public class UserLoginLogic implements UserLogin {
  /**
   * This method checks if the player have signed up.
   * 
   * @param useremail user's email
   * @return true if the user need to sign up
   */
  public boolean signUpNeeded(String useremail) {
    Player player = PersistenceFactory.getInstance().retrievePlayer(useremail);
    if (player == null) {
      return true;
    }
    return false;
  }

  /**
   * This method checks if the password is correct.
   * 
   * @param useremail user's email
   * @param password user's password
   * @return true if the email and the password match false if the email and the password does not
   *         match
   */
  @Override
  public boolean userPasswordMatched(String useremail, String password) {
    Player player = PersistenceFactory.getInstance().retrievePlayer(useremail);
    if (player != null) {
      if (InfrastructureFactory.getInstance().validatePassword(player, password)) {
        return true;
      }
    }
    return false;
  }
}
