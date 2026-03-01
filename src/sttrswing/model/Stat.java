package sttrswing.model;

/**
 * {@link Stat} wraps a numerical int value, with a minimum and maximum value enforced inclusively.
 * i.e. if you pass in a current value of 2 and a maximum value of 2, current will become 2 This
 * implementation of {@link Stat} assumes the minimum value is always 0.
 */
public class Stat {

  private final int min;
  private int current;
  private int max;


  /**
   * Constructs a {@link Stat} instance.
   *
   * @param current - amount to set the current value of the {@link Stat} to, minimum and maximum
   *                bounds will be enforced on construction.
   * @param max     - amount to set the maximum value of the {@link Stat} to, used in boundary
   *                enforcement.
   */
  public Stat(final int current, final int max) {
    this.min = 0;
    this.current = current;
    this.max = max;
    this.enforceBounds();
  }

  /**
   * Adjusts the current value of the {@link Stat} by the given amount, maximum and minimum bounds
   * are enforced.
   *
   * @param amount - the amount we wish to adjust the current value by
   */
  public void adjust(final int amount) {
    this.current += amount;
    this.enforceBounds();
  }

  /**
   * Sets the current value of the {@link Stat} to the given amount, maximum and minimum bounds are
   * enforced.
   *
   * @param amount - amount we wish to the set the current value to.
   */
  public void set(final int amount) {
    this.current = amount;
    this.enforceBounds();
  }

  /**
   * Return the current value of {@link Stat}.
   *
   * @return the current value of {@link Stat}.
   */
  public int get() {
    return this.current;
  }

  /**
   * Returns the value of the max set.
   *
   * @return the value of the max set.
   */
  public int getMax() {
    return this.max;
  }

  /**
   * Sets a new maximum to the given amount.
   *
   * @param amount the amount we want to set the max to.
   */
  public void setMax(final int amount) {
    this.max = amount;
  }

  /**
   * Adjust the current value to be within the set minimum and maximum bounds (inclusive).
   */
  public void enforceBounds() {
    if (this.current > max) {
      this.current = max;
    }
    if (this.current < min) {
      this.current = min;
    }
  }

  /**
   * Returns a String representation of the {@link Stat}.
   *
   * @return a String representation of the {@link Stat}.
   */
  @Override
  public String toString() {
    return this.current + "";
  }
}
