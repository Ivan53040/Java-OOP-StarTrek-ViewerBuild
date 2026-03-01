package sttrswing.model.validator;

/**
 * {@link Validator} passed to {@link InputHandler} for use in validating the users input. The base
 * {@link Validator} class should always return true for isValid() as it accepts all input unless
 * extended to be more picky.
 */
public class Validator {

  /**
   * Constructs a {@link Validator} instance.
   */
  public Validator() {
  }

  /**
   * Returns if a given {@link String} input is valid, by default in the base {@link Validator}
   * class it is always valid, intended to be overridden in more picky subclasses.
   *
   * @param input - {@link String} user input we wish to validate.
   * @return if a given {@link String} input is valid, by default in the base {@link Validator} *
   * class it is always valid, intended to be overridden in more picky subclasses.
   */
  public boolean isValid(final String input) {
    return true;
  }
}
