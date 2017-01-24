package edu.skidmore.cs376b.sirqlate.frontend.implementation;

import edu.skidmore.cs376b.sirqlate.logic.factory.LogicFactory;
import us.daveread.edu.graphics.surface.DrawingSurface;

import java.util.HashSet;

/**
 * The class that is responsible for login functionalities, potentially can work as the main menu.
 * 
 * @author An Phi (aphi)
 *
 */
public class Authentication {

  /**
   * Number of sides on the board.
   */
  private static int NUMBER_PLAYER = 3;

  /**
   * Default number of sides on the board.
   */
  private static int DEFAULT_NUMBER_HUMAN_PLAYER = 3;

  /**
   * Prompt the user for login and password.
   */
  public String[] login(DrawingSurface surface) {
    while (true) {
      // Getting the number of player
      try {
        String response =
            surface.getUserInput("Set Number of Human Players", "How many human players?");
        if (response == null) {
          System.exit(0);
        }
        NUMBER_PLAYER = Integer.parseInt(response);
      } catch (NumberFormatException exception) {
        surface.addMessage("Really? We will use " + DEFAULT_NUMBER_HUMAN_PLAYER + " then.");
        NUMBER_PLAYER = DEFAULT_NUMBER_HUMAN_PLAYER;
      } finally {
        if (NUMBER_PLAYER < 0 || NUMBER_PLAYER > 3) {
          NUMBER_PLAYER = DEFAULT_NUMBER_HUMAN_PLAYER;
          surface.addMessage("Really? We will use " + DEFAULT_NUMBER_HUMAN_PLAYER + " then.");
        }
      }
      boolean abortAuthentication = false;
      HashSet<String> playerEmailsSet = new HashSet<String>();
      while (!abortAuthentication && playerEmailsSet.size() != NUMBER_PLAYER) {
        String userId = "";
        boolean firstTrialUser = true;
        while (userId != null && LogicFactory.getInstance().signUpNeeded(userId)) {
          if (!firstTrialUser) {
            surface.addMessage("Sorry, but you are not ONE of US!");
          }
          firstTrialUser = false;
          userId = surface.getUserInput("User Id Input",
              "Please enter the User Id for player " + (playerEmailsSet.size() + 1));

          // Check for duplicated players
          if (playerEmailsSet.contains(userId)) {
            surface.addMessage("Really, are you trying to play against yourself now?!");
          } else if (userId != null && !LogicFactory.getInstance().signUpNeeded(userId)) {
            // If not duplicated, move on verify the password
            boolean firstTrialPassword = true;
            String password = "";
            do {
              if (!firstTrialPassword) {
                surface.addMessage("Wrong code, we can't let your in yet!");
              }
              firstTrialPassword = false;
              password = surface.getUserInput("Password Input",
                  "Please enter the password for " + userId, true);
              if (password == null) {
                break;
              }
            }
            while (password != null
                && !LogicFactory.getInstance().userPasswordMatched(userId, password));

            if (password != null) {
              playerEmailsSet.add(userId);
            }
          }
        }
        if (userId == null) {
          abortAuthentication = true;
        }
      }

      // Prepare for return here
      if (playerEmailsSet.size() == NUMBER_PLAYER) {
        String nameMessage = "";
        String[] playerEmails = new String[NUMBER_PLAYER];
        playerEmailsSet.toArray(playerEmails);
        for (int index = 0; index < playerEmails.length; ++index) {
          nameMessage += playerEmails[index];
          if (index != playerEmails.length - 1) {
            nameMessage += ", ";
          }
        }
        surface.addMessage("Welcome " + nameMessage + " to Sir-Q-Late!");
        return playerEmails;
      }
    }
  }
}
