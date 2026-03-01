package sttrswing.model.validator;

/**
 * {@link Validator} overwrites isValid to validate that the given course input is 1 or more
 * (inclusive) and less than 9.
 */
public class CourseValidator extends Validator {

  /**
   * Constructs an instance of {@link CourseValidator}.
   */
  public CourseValidator() {
  }

  /**
   * Takes the given {@link String} input and return if the given String input can be evaluated as 1
   * or more (inclusive) and less than 9.
   *
   * @param input - The given {@link String} we wish to evaluate.
   * @return if the given String input can be evaluated as 1 or more (inclusive) and less than 9.
   */
  @Override
  public boolean isValid(final String input) {
    var trimmedInput = input.trim().toUpperCase();
    if (trimmedInput.length() != 1) { //more or less than one symbol means it can't be valid!
      return false;
    }
    char symbol = trimmedInput.charAt(0);
    if (!Character.isDigit(symbol)) { //if its nota digit it is not a valid course!
      return false;
    }
    int courseInt = -1;
    try { //try to convert it to an int
      courseInt = Integer.parseInt(trimmedInput);
    } catch (NumberFormatException e) { //if it throws that means its not a valid int!
      return false;
    }
    return courseInt >= 1
        && courseInt < 9; //finally we confirm the given int is within the valid range
  }
}
