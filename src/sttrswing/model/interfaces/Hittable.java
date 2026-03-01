package sttrswing.model.interfaces;

/**
 * Interface that indicates something is {@link Hittable} for a given amount of damage.
 */
public interface Hittable {

  /**
   * Method for hitting a {@link Hittable} Object.
   *
   * @param damage - the amount of damage the {@link Hittable} Object is being hit by.
   */
  public void hit(final int damage);
}
