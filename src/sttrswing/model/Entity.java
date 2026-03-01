package sttrswing.model;

import sttrswing.model.interfaces.HasSymbol;
import sttrswing.model.interfaces.Hittable;
import sttrswing.model.interfaces.Positionable;
import java.util.List;

/**
 * {@link Entity} is used to represent an {@link Entity} or 'thing' for want of a better term in the
 * {@link Game}. It's x and y coordinates stored in .position are used to indicate where it is in a
 * {@link Quadrant}.
 */
public class Entity implements Hittable, Positionable, HasSymbol {

  private final XyPair position = new XyPair(0, 0);
  private String symbol = "###";
  private boolean markedForRemoval = false;
  private boolean scanned = false;

  /**
   * Construct a new {@link Entity} at the given coordinates.
   *
   * @param x - horizontal coordinate
   * @param y - vertical coordinate
   */
  public Entity(final int x, final int y) {
    this.position.setX(x);
    this.position.setY(y);
  }

  /**
   * Marks this {@link Entity} as scanned, so it doesn't have to be scanned twice.
   */
  public void scan() {
    this.scanned = true;
  }

  /**
   * Marks an {@link Entity} as ready to be removed, intended to be used by {@link List}, arrays
   * etc. Holding that entity as part of a cleanup step.
   */
  public void remove() {
    this.markedForRemoval = true;
  }

  /**
   * Returns if an {@link Entity} has been marked as ready for removal.
   *
   * @return if an {@link Entity} has been marked as ready for removal.
   */
  public boolean isMarkedForRemoval() {
    return this.markedForRemoval;
  }

  /**
   * Takes a given {@link String} as the new symbol we want to use to represent the {@link Entity}.
   *
   * @param symbol - the new {@link String} symbol we want to use to represent the {@link Entity}.
   */
  public void setSymbol(final String symbol) {
    this.symbol = symbol;
  }

  /**
   * Returns the x coordinate value.
   *
   * @return the x coordinate value.
   */
  @Override
  public int getX() {
    return this.position.getX();
  }

  /**
   * Sets the x coordinate to the new value.
   *
   * @param x - the new value for our x coordinate.
   */
  @Override
  public void setX(final int x) {
    this.position.setX(x);
  }

  /**
   * Returns the y coordinate value.
   *
   * @return the y coordinate value.
   */
  @Override
  public int getY() {
    return this.position.getY();
  }

  /**
   * Sets the y coordinate to the new value.
   *
   * @param y - the new value for our y coordinate.
   */
  @Override
  public void setY(final int y) {
    this.position.setY(y);
  }

  /**
   * Adjust the {@link Entity} position (which is an {@link XyPair}) by a given amount for the x and
   * y values. e.g. sample.adjustPosition(-1,0) - move left by 1 sample.adjustPosition(1,0) - move
   * right by 1 sample.adjustPosition(0,-1) - move up by 1 sample.adjustPosition(0,1) - move down by
   * 1 sample.adjustPosition(0,0) - don't move sample.adjustPosition(-1,-1) - move left 1 and up 1
   * sample.adjustPosition(1,1) - move right 1 and down 1 sample.adjustPosition(-1,1) - move left 1
   * and down 1 sample.adjustPosition(1,-1) - move right 1 and up 1
   *
   * @param x - amount we wish to adjust the horizontal position of this {@link Entity} by.
   * @param y - amount we wish to adjust the vertical position of this {@link Entity} by.
   */
  @Override
  public void adjustPosition(final int x, final int y) {
    this.position.adjustX(x);
    this.position.adjustY(y);
  }

  /**
   * Returns a boolean indicating if the {@link Entity} has been scanned.
   *
   * @return a boolean indicating if the {@link Entity} has been scanned.
   */
  public boolean isScanned() {
    return scanned;
  }

  @Override
  public void hit(final int damage) {
  }

  /**
   * Returns a {@link String} representation of the current {@link Entity} showing its current
   * position, if it has been scanned, and if it is marked for removal E.g. {@link Entity}[ x:1,
   * y:2, scanned:true, markedForRemoval:true ]
   *
   * @return a {@link String} representation of the current {@link Entity} showing its current
   * position, if it has been scanned, and if it is marked for removal
   */
  public String toString() {
    return "Entity[x:" + this.position.getX() + ",y:" + this.position.getY() + ",scanned:"
        + this.isScanned() + ",markedForRemoval:" + this.isMarkedForRemoval() + "]";
  }

  /**
   * Returns a 3 letter {@link String} symbol for this {@link Entity}. Will return " ? " if it has
   * not been scanned.
   *
   * @return 3 letter {@link String} symbol " ? " if not been scanned.
   */
  public String symbol() {
    if (!this.scanned) {
      return " ? ";
    }
    return this.symbol;
  }
}
