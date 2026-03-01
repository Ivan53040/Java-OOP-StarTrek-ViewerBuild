package sttrswing.model.interfaces;

import sttrswing.model.Entity;

/**
 * Interface indicates the Object both has a viewable position (see {@link HasPosition}) and that
 * position can be altered via these given interface methods.
 */
public interface Positionable extends HasPosition {

  /**
   * Sets the X coordinate of the {@link Positionable} Object.
   *
   * @param x - the new value for the x coordinate of this {@link Positionable} Object.
   */
  public void setX(final int x);

  /**
   * Sets the Y coordinate of the {@link Positionable} Object.
   *
   * @param y - the new value for the y coordinate of this {@link Positionable} Object.
   */
  public void setY(final int y);

  /**
   * Adjust the {@link Positionable} position by a given amount for the x and y values. e.g.
   * <ul>
   *   <li>sample.adjustPosition(-1,0) - move left by 1
   *   sample.adjustPosition(1,0) - move right by 1 </li>
   *   <li>sample.adjustPosition(0,0) - don't move
   *   sample.adjustPosition(-1,-1) - move left 1 and up 1 </li>
   *   <li>sample.adjustPosition(1,1) - move right 1 and down 1</li>
   *   <li>sample.adjustPosition(1,-1) - move right 1 and up 1</li>
   * </ul>
   *
   * @param x - amount we wish to adjust the horizontal position of this {@link Entity} by.
   * @param y - amount we wish to adjust the vertical position of this {@link Entity} by.
   */
  public void adjustPosition(final int x, final int y);
}
