package sttrswing.view;

import java.awt.Color;

/**
 * Enum for the colour pallete used throughout this project, intended to help remove magic
 * hexcodes littering the codebase.
 *
 * <p>sample use
 * {@code Pallete.RED.color(); to get the red {@link java.awt.Color} value based on the color value
 * stored inside the pallete.}
 */
public enum Pallete {
  GREYDARK("#595e5c"),
  GREY("#869393"),
  WHITEMID("#efece8"),
  WHITE("#f9f9f7"),
  BLACK("#2d2d2d"),
  PINK("#df0164"),
  REDDARK("#631519"),
  RED("#c5222b"),
  REDPALE("#e17b94"),
  BLUEDARK("#283a78"),
  BLUE("#3f75b3"),
  BLUEPALE("#675bcb"),
  CYANDARK("#008080"),
  CYANPALE("#66FFFF"),
  YELLOWDARK("#c7b44e"),
  YELLOW("#ffd539"),
  YELLOWPALE("#f1e6c8"),
  BROWN("#ab492d"),
  TAN("#f0c8be"),
  GREENDARK("#5d6a36"),
  GREENTERMINAL("#4AF626"),
  GREENPALE("#baf589"),
  ORANGE("#c66427");

  private final String hex;

  Pallete(final String hex) {
    this.hex = hex;
  }

  /**
   * Returns a stringified hex code for the relevant Pallete option.
   *
   * @return a stringified hex code for the relevant Pallete option
   */
  public String hex() {
    return this.hex;
  }

  /**
   * Returns a {@link java.awt.Color} value for the given enum value. Example:
   * {@code Pallete.BROWN.color();}
   *
   * @return a {@link java.awt.Color} value for the given enum value.
   */
  public Color color() {
    return Color.decode(this.hex());
  }
}