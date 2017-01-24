package edu.skidmore.cs376b.sirqlate.gamification.interfaces;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;

/**
 * Interface called by frontend to return top five leaderboard.
 * 
 * @author cenglish
 */
public interface LeaderboardInterface {
  public Player[] getLeaderboard(Player[] players);
}


