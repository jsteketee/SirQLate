package edu.skidmore.cs376b.sirqlate.logic.interfaces;

/**
 * This is an interface to do user authentication.
 * 
 * @author xhuang1
 *
 */

public interface UserLogin {
  /**
   * This method checks if the player have signed up.
   * 
   * @param useremail user's email
   * @return true if the user need to sign up
   */
  boolean signUpNeeded(String useremail);

  /**
   * This method checks if the password is correct.
   * 
   * @param useremail user's email
   * @param password user's password
   * @return true if the email and the password match
   */
  boolean userPasswordMatched(String useremail, String password);


}
