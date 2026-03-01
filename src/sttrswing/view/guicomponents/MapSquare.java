package sttrswing.view.guicomponents;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import sttrswing.view.Pallete;

public class MapSquare extends JLabel {

  /**
   * Creates a {@link MapSquare} with a given symbol to display and 
   * {@link Color}s for the foreground, background and border.
   *
   * @param symbol     - symbol we want to display on the square.
   * @param foreground - {@link Color} to set the foreground to.
   * @param background - {@link Color} to set the background to.
   * @param border     - {@link Color} to set the border to.
   */
  public MapSquare(
      final String symbol,
      final Color foreground,
      final Color background,
      final Color border
  ) {
    super(symbol);
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setBackground(background);
    this.setForeground(foreground);
    this.setBorder(BorderFactory.createLineBorder(border));
  }

  /**
   * Creates a {@link MapSquare} with a default background {@link Color} from {@link Pallete}, a
   * given symbol to display and a customisable foreground and border {@link Color}.
   *
   * @param symbol         - symbol we want to display on the square.
   * @param highlightColor - colour we want to use for the border and foreground of this square.
   */
  public MapSquare(
      final String symbol,
      final Color highlightColor
  ) {
    super(symbol);
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setBackground(Pallete.BLACK.color());
    this.setForeground(highlightColor);
    this.setBorder(BorderFactory.createLineBorder(highlightColor));
  }

  /**
   * Creates a {@link MapSquare} with a default background,foreground and border {@link Color} from
   * {@link Pallete}
   *
   * @param symbol - symbol we want to display on the square.
   */
  public MapSquare(final String symbol) {
    super(symbol);
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setBackground(Pallete.BLACK.color());
    this.setForeground(Pallete.GREENTERMINAL.color());
    this.setBorder(BorderFactory.createLineBorder(Pallete.GREENDARK.color()));
  }

}
