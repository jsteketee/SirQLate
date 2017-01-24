package edu.skidmore.cs376b.sirqlate.gamification.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Player;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.LeaderboardInterface;

/**
 * Class that returns the top five players in the database. Per the customer's instruction,
 * "top five" refers to five highest average points. The class is ugly but runs quicker than a
 * comparison sort (O(n) compared to O(nlogn). It looks dumb but O'Connell said it was good.
 * 
 * @author cenglish
 *
 */
public class Leaderboard implements LeaderboardInterface {
  /**
   * @param players is the list of all players in the database.
   * @return leaderboard that is returned is the top five ranked players.
   */
  public Player[] getLeaderboard(Player[] players) {

    // creates a dummy leaderboard
    Player fakePlayer = new Player();
    fakePlayer.setPoints(new Points(0));
    Player[] leaderboard = new Player[5];
    for (int tempp = 0; tempp < 5; tempp++) {
      leaderboard[tempp] = fakePlayer;
    }

    // for each player in the database
    for (int tempi = 0; tempi < players.length; tempi++) {

      // compare their placements to the top five
      long average = players[tempi].getPoints().getPoints() / players[tempi].getGamePlayed();

      // starting with 1st place and moving down, see if a place can be taken and move others to
      // accommodate the change; constant time comparisons and swaps
      if (average > leaderboard[0].getPoints().getPoints() / leaderboard[0].getGamePlayed()) {
        leaderboard[4] = leaderboard[3];
        leaderboard[3] = leaderboard[2];
        leaderboard[2] = leaderboard[1];
        leaderboard[1] = leaderboard[0];
        leaderboard[0] = players[tempi];
      } else if (average > leaderboard[1].getPoints().getPoints()
          / leaderboard[1].getGamePlayed()) {
        leaderboard[4] = leaderboard[3];
        leaderboard[3] = leaderboard[2];
        leaderboard[2] = leaderboard[1];
        leaderboard[1] = players[tempi];
      } else if (average > leaderboard[2].getPoints().getPoints()
          / leaderboard[2].getGamePlayed()) {
        leaderboard[4] = leaderboard[3];
        leaderboard[3] = leaderboard[2];
        leaderboard[2] = players[tempi];
      } else if (average > leaderboard[3].getPoints().getPoints()
          / leaderboard[3].getGamePlayed()) {
        leaderboard[4] = leaderboard[3];
        leaderboard[3] = players[tempi];
      } else if ((average > leaderboard[4].getPoints().getPoints()
          / leaderboard[4].getGamePlayed())) {
        leaderboard[4] = players[tempi];
      }
    }
    return leaderboard;
  }

}
