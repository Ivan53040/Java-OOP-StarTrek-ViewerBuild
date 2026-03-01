package sttrswing.model.validator;

/**
 * {@link Validator} responsible for validating if a given user input can be  evaluated as an int
 * equal to or less then the specified max energy.
 */
public class EnergyValidator extends Validator {

  final private int maxEnergy;

  /**
   * Constructs a {@link EnergyValidator} with a given maximum acceptable energy value.
   *
   * @param maxEnergy - maximum energy value this {@link EnergyValidator} should accept.
   */
  public EnergyValidator(final int maxEnergy) {
    super();
    this.maxEnergy = maxEnergy;
  }


  /**
   * Takes the given {@link String} input and return if the given {@link String} input can be
   * evaluated as an int equal to or less then the specified max energy.
   *
   * @param input - The given {@link String} we wish to evaluate.
   * @return if the given {@link String} input can be evaluated as an int equal to or less then the
   * specified max energy.
   */
  @Override
  public boolean isValid(final String input) {
    var trimmedInput = input.trim();
    for (int i = 0; i < trimmedInput.length(); i++) {
      if (!Character.isDigit(trimmedInput.charAt(i))) {
        return false;
      }
    }
    return Integer.parseInt(trimmedInput) <= maxEnergy;
  }


}
