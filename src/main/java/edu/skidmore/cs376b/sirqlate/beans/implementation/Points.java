package edu.skidmore.cs376b.sirqlate.beans.implementation;


/**
 * @author cenglish.
 */
public class Points {
  /**
   * number of points.
   */
  private long points;

  public Points(long points) {
    this.points = points;
  }

  /**
   * @return number of points.
   */
  public long getPoints() {
    return this.points;
  }

  /**
   * @param points to set to.
   */
  public void setPoints(long points) {
    this.points = points;
  }

}
