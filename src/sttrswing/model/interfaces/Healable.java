package sttrswing.model.interfaces;

/**
 * Interface that indicates something is Healable via the heal() method.
 */
public interface Healable {

  /**
   * Method used to pass the amount of energy we want the Healable Object to heal by.
   *
   * @param energy - the amount of energy we want the Healable Object to heal by.
   */
  public void heal(final int energy);
}
