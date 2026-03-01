package sttrswing.model.validator;

/**
 * {@link Validator} responsible for validating if the user {@link String} input can be evaluated as
 * * conforming to either a NUMBER,NUMBER or NUMBER,NUMBER.NUMBER pattern.
 */
public class WarpCoordValidator extends Validator {

  /**
   * Constructs a {@link WarpCoordValidator} instance.
   */
  public WarpCoordValidator() {
  }


  /**
   * Takes the given {@link String} input and return if the given {@link String} input can be
   * evaluated as conforming to either a DIGIT,DIGIT or DIGIT,DIGIT.DIGIT pattern. Examples of valid
   * inputs:
   * <ul>
   *     <li>1, 1</li>
   *     <li>5, 0.1</li>
   *     <li>2, 0.4</li>
   * </ul>
   * Examples of invalid inputs:
   * <ul>
   *     <li>10,0</li>
   *     <li>-1,0</li>
   *     <li>1,</li>
   *     <li>5,0.1</li>
   *     <li>0.5,1</li>
   * </ul>
   *
   * @param input - The given {@link String} we wish to evaluate.
   * @return if the given {@link String} input can be evaluated as conforming to either a
   * DIGIT,DIGIT or DIGIT,DIGIT.DIGIT pattern.
   */
  @Override
  public boolean isValid(final String input) {
    var trimmedInput = input.trim();
    if (trimmedInput.length() <= 2
        || trimmedInput.length()
        > 5) { //can't be a valid NUMBER,NUMBER or NUMBER,NUMBER.NUMBER pattern!
      return false;
    }
    if (trimmedInput.charAt(1) != ',') {
      return false;
    }
    if (trimmedInput.length() > 3
        && trimmedInput.charAt(3) != '.') { //malformed shape for longer trimmedInput
      return false;
    }

    if (trimmedInput.length() == 3) {
      var firstDigit = trimmedInput.charAt(0);
      var lastDigit = trimmedInput.charAt(2); //confirm both characters are digits!

      return Character.isDigit(firstDigit) && Character.isDigit(lastDigit);
    } else if (trimmedInput.length() == 5) {
      var firstDigit = trimmedInput.charAt(0);  //should be digit
      var secondDigit = trimmedInput.charAt(1); //should be ,
      var thirdDigit = trimmedInput.charAt(2);  //should be digit
      var fourthDigit = trimmedInput.charAt(3); //should be .
      var fifthDigit = trimmedInput.charAt(4);  //should be digit

      return Character.isDigit(firstDigit) && secondDigit == ',' && Character.isDigit(thirdDigit)
          && fourthDigit == '.' && Character.isDigit(fifthDigit);
    }
    return false;
  }
}
