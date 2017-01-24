package edu.skidmore.cs376b.sirqlate.gamification.implementation;

import edu.skidmore.cs376b.sirqlate.beans.implementation.Game;
import edu.skidmore.cs376b.sirqlate.beans.implementation.Points;
import edu.skidmore.cs376b.sirqlate.gamification.interfaces.assignWinnerPointsInterface;

public class assignWinnerPoints implements assignWinnerPointsInterface{

  @Override
  public void assignWinnerPoints(Game game) {

    Points winnerPoints = new Points(game.getWinner().getPoints().getPoints() + 100);
    game.getWinner().setPoints(winnerPoints);
    
  }

}
