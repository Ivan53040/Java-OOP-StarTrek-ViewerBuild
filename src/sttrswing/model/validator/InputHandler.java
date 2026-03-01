package sttrswing.model.validator;

import java.util.Scanner;

/**
 * Responsible for handling user input, uses a given {@link Validator} to validate user input, and a
 * given {@link String} for the user prompt to print.
 */
public class InputHandler {

  /*
      we specify a maximum number of attempts for getting input so the program
      won't hang indefinitely if we screw up our while loop :)
  */
  private static final int maxAttempts = 9999;

  private Validator validator;
  private String prompt = "default prompt";

  /**
   * Constructs a InputHandler instance with a default generic {@link Validator} and a default
   * prompt of "default prompt".
   */
  public InputHandler() {
    this.validator = new Validator();
  }

  /**
   * Sets the input prompt to a new {@link String} value.
   *
   * @param prompt - the new {@link String} value to set the internal prompt to for future use when
   *               calling .get()
   */
  public void setPrompt(final String prompt) {
    this.prompt = prompt;
  }

  /**
   * Sets the internal {@link Validator} used for the given prompts.
   *
   * @param validator - the validator we wish to use to validate user input.
   */
  public void setValidator(final Validator validator) {
    this.validator = validator;
  }

  /**
   * Prints out the current prompt and requests user input, checks that input against the
   * {@link Validator} isValid method to determine if the {@link InputHandler} can return that input
   * or if it has to ask the user again. Maximum number of 9999 checks per time .get() is called, to
   * safeguard against infinite loop.
   *
   * @return the input assessed as valid by the {@link Validator} currently held by
   * {@link InputHandler}
   */
  public String get() {
    int attempts = 0;
    String input = "";
    System.out.print(prompt + " ");
    Scanner scanner = new Scanner(System.in);
    while (attempts < maxAttempts) {
      input = scanner.nextLine().trim().toUpperCase();
      if (this.validator.isValid(input)) {
        return input; //got a valid input! TIME TO LEAVE;
      } else {
        System.out.println(this.prompt + " ");
      }
      attempts += 1;
    }
    return null; //return null because something has gone wrong.
  }

  /**
   * Resets the {@link InputHandler} to its default {@link Validator}.
   */
  public void resetValidator() {
    this.setValidator(new Validator());
  }
}
