package sttrswing.view;

import sttrswing.controller.GameController;
import sttrswing.model.interfaces.GameModel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * A {@link View} that shows the mission success screen to the user.
 */
public class WinGameView extends View {

  /** The game model. */
  private final GameModel game;
  /** The game controller. */
  private final GameController controller;
  /** The win text area. */
  private final JTextArea winText;
  /** The details text area. */
  private final JTextArea details;

  /**
   * Constructs a new {@link WinGameView} using access to the given game state 
   * and controller state to access relevant data and bind relevant method calls 
   * from both states to our listeners.
   *
   * @param game       game state we use to construct this view for both 
   *                   information and method calls for relevant action listeners if any.
   * @param controller controller state we use for both information access and 
   *                   method calls for relevant action listeners if any.
   */
  public WinGameView(final GameModel game, final GameController controller) {
    super("Star Trek - Victory!");
    this.game = game;
    this.controller = controller;
    this.setLayout(new GridLayout(0, 1));
    this.winText = this.buildTextArea("Mission Success!");
    this.winText.setFont(new Font("Arial", Font.BOLD, 48));
    this.details = this.buildTextArea(
        "The Klingons have been brought to the negotiating table "
            + "and have been forced to sue for peace!\n\nKlingons Left:"
            + game.totalKlingonCount() + "\n" + "Starbases Remaining:"
            + game.totalStarbaseCount());
    this.details.setFont(new Font("Arial", Font.BOLD, 24));
    this.add(this.winText);
    this.add(this.details);
  }

  /**
   * Public for testability reasons. Constructs a {@link JTextArea} configured for our
   * {@link WinGameView}. editable: false lineWrap: true wrapStyleWord: true foreground: white from
   * pallete margin: 10,15,10,15
   *
   * @param contents string contents we wish to display on our {@link JTextArea}.
   * @return a {@link JTextArea} configured for our {@link WinGameView}
   */
  public JTextArea buildTextArea(final String contents) {
    final var text = new JTextArea(contents);
    text.setEditable(false);
    text.setLineWrap(true);
    text.setWrapStyleWord(true);
    text.setOpaque(false);
    text.setAlignmentX(SwingConstants.HORIZONTAL);
    text.setAlignmentY(SwingConstants.VERTICAL);
    text.setForeground(Pallete.WHITE.color());
    text.setMargin(new Insets(10, 15, 10, 15));
    return text;
  }

  public JTextArea getWinText() {
    return this.winText;
  }

}