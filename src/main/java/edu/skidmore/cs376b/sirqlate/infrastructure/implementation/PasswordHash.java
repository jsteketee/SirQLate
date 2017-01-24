package edu.skidmore.cs376b.sirqlate.infrastructure.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordHash {

  /**
   * This class provides create hashed password and validate password methods.
   * 
   * @author: gsnider
   * @author: gjin
   */

  private static final Logger LOG = Logger.getLogger(PasswordHash.class);

  /**
   * creates a hashed password(input).
   * 
   * @param player the player who is creating password
   * @param input string password the user chose
   */
  public void createHashedPassword(Player player, String input) {

    Base64.Encoder b64 = Base64.getEncoder();

    if (input == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    // generate salt
    final Random r = new SecureRandom();
    byte[] salt = new byte[5];
    r.nextBytes(salt); // stores bytes in salt byte array

    // convert input to bytes and create input array of bytes
    // append salt to input byte array
    byte[] inputInBytes = input.getBytes();
    byte[] inputAndSalt = new byte[inputInBytes.length + salt.length];
    System.arraycopy(inputInBytes, 0, inputAndSalt, 0, inputInBytes.length);
    System.arraycopy(salt, 0, inputAndSalt, inputInBytes.length, salt.length);

    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] md5 = md.digest(inputAndSalt);
      player.setPasswordHash(b64.encodeToString(md5));
      player.setSalt(b64.encodeToString(salt));
    } catch (Throwable t0) {
      // log incorrect attempts at hashing password input
      LOG.error("Failed to create hash", t0);
    }
  }

  /**
   * validates password hash at login attempt.
   * 
   * @param player the player is logging in
   * @param password the password the player typed in
   * @return match whether the password matched what is in persistence
   */
  public boolean validatePassword(Player player, String password) {
    // boolean match = false;
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    String saltstring = player.getSalt();
    // byte[] salt = saltstring.getBytes();
    Base64.Decoder b64 = Base64.getDecoder();
    byte[] salt = b64.decode(saltstring);

    byte[] hash = b64.decode(player.getPasswordHash());
    byte[] passInBytes = password.getBytes();

    byte[] passAndSalt = new byte[passInBytes.length + salt.length];
    System.arraycopy(passInBytes, 0, passAndSalt, 0, passInBytes.length);
    System.arraycopy(salt, 0, passAndSalt, passInBytes.length, salt.length);

    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] md5 = md.digest(passAndSalt);
      return Arrays.equals(hash, md5);
    } catch (Throwable t0) {
      // log incorrect attempts at hashing password input
      LOG.error("Failed to create hash", t0);
    }

    return false;
  }
}
