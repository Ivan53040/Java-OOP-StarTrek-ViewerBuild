package sttrswing.model;

/**
 * Represents a data object with an x and y Integer, used for things like position and vectors. x
 * represents the horizontal y represents the vertical
 */
public class XyPair {

  private int coordinateX = 0;
  private int coordinateY = 0;

  /**
   * Constructs an instance of {@link XyPair} with the given values.
   *
   * @param x - x value of the pair.
   * @param y - y value of the pair.
   */
  public XyPair(final int x, final int y) {
    this.coordinateX = x;
    this.coordinateY = y;
  }

  /**
   * Returns the x value for this xy pair.
   *
   * @return the x value for this xy pair.
   */
  public int getX() {
    return this.coordinateX;
  }

  /**
   * Returns the y value for this xy pair.
   *
   * @return the y value for this xy pair.
   */
  public int getY() {
    return this.coordinateY;
  }

  /**
   * Sets the x value to the given value.
   *
   * @param x - the new x value
   */
  public void setX(final int x) {
    this.coordinateX = x;
  }

  /**
   * Sets the y value to the given value.
   *
   * @param y - the new y value
   */
  public void setY(final int y) {
    this.coordinateY = y;
  }

  /**
   * Adjusts the current x value by the given amount.
   *
   * @param value - the amount to adjust the current x value by.
   */
  public void adjustX(final int value) {
    this.coordinateX += value;
  }

  /**
   * Adjusts the current y value by the given amount.
   *
   * @param value - the amount to adjust the current y value by.
   */
  public void adjustY(final int value) {
    this.coordinateY += value;
  }

  /**
   * Returns a string representation of this xypair.
   *
   * @return a string representation of this xypair.
   */
  public String toString() {
    return "XYPair[x:" + this.coordinateX + ", y:" + this.coordinateY + "]";
  }
}
