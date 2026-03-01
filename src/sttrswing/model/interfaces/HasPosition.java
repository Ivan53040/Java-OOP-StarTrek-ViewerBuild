package sttrswing.model.interfaces;

/**
 * Interface that indicates something has a position that can be viewed.
 */
public interface HasPosition {

  /**
   * Returns the X coordinate of the {@link Positionable} Object.
   *
   * @return the X coordinate of the {@link Positionable} Object
   */
  public int getX();


  /**
   * Returns the Y coordinate of the {@link Positionable} Object.
   *
   * @return the Y coordinate of the {@link Positionable} Object.
   */
  public int getY();
}
