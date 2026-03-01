package sttrswing.view.guicomponents;

import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * JButton that can also hold a directional value, and dynamically represents that directional value
 * with an appropriate utf-8 arrow
 */
public class DirectionButton extends JButton {
  private final HashMap<Integer, String> arrows = new HashMap<>() {{
    put(1, "→");
    put(2, "↗");
    put(3, "↑");
    put(4, "↖");
    put(5, "←");
    put(6, "↙");
    put(7, "↓");
    put(8, "↘");
  }};
  /** The direction value. */
  private final int direction;

  /**
   * Create a Button that stores the given direction, sets its label to the 
   * relevant directional utf-8 arrow and prepends an indicator if this wil 
   * consume a turn.
   *
   * @param direction    - the direction value we wish to store for later access 
   *                     and represent the value of on the button with the relevant utf-8 arrow.
   * @param consumesTurn - indicates if this button should visually indicate that 
   *                     it will consume a turn
   */
  public DirectionButton(final int direction, final boolean consumesTurn) {
    super();
    if (direction < 1 || direction > 8) {
      throw new IllegalArgumentException("Direction must be between 0 and 8");
    }
    this.direction = direction;
    String label = arrows.get(direction);
    if (consumesTurn) {
      label = "⌛ " + label;
    }
    this.setText(label);
    this.setHorizontalAlignment(SwingConstants.CENTER);
  }

  /**
   * Return the directional value of this button.
   * @return the direction value bound to this button.
   */
  public int getDirection() {
    return direction;
  }
}
