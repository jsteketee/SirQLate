package edu.skidmore.cs376b.sirqlate.frontend.implementation;

import java.awt.Color;

/**
 * Enum for enhanced color - better contrast and allow usage of method like brighter() and darker()
 * for Color. Shoutout to http://clrs.cc/
 *
 * @author An Phi (aphi)
 */
public enum EnhancedColor {
  RED(Color.RED, new Color(255, 65, 54)), BLUE(Color.BLUE, new Color(18, 119, 214)), GREEN(
      Color.GREEN, new Color(46, 204, 64)), YELLOW(Color.YELLOW, new Color(255, 220, 0)), CYAN(
          Color.CYAN, new Color(127, 219, 255)), MAGENTA(Color.MAGENTA,
              new Color(147, 11, 168)), ORANGE(Color.ORANGE, new Color(255, 133, 27)), BLACK(
                  Color.BLACK, new Color(17, 17, 17)), WHITE(Color.WHITE,
                      new Color(221, 221, 221)), GRAY(Color.GRAY, new Color(170, 170, 170)), AQUA(
                          new Color(127, 219, 255)), LIME(new Color(1, 255, 112)), SILVER(
                              new Color(223, 223, 223)), OLIVE(new Color(61, 153, 112));

  private Color orgColor;
  private Color newColor;

  private EnhancedColor(Color orgColor, Color newColor) {
    this.orgColor = orgColor;
    this.newColor = newColor;
  }

  private EnhancedColor(Color newColor) {
    this.newColor = newColor;
  }

  /**
   * Return the color in the enum.
   * 
   * @return the color.
   */
  public Color getColor() {
    return this.newColor;
  }

  /**
   * Get the enhanced color from the original color.
   * 
   * @param orgColor.
   * @return enhancedColor
   */
  public static Color beautify(Color orgColor) {
    for (EnhancedColor color : EnhancedColor.values()) {
      if (color.orgColor != null && color.orgColor.equals(orgColor)) {
        return color.newColor;
      }
    }
    return BLACK.newColor;
  }
}
