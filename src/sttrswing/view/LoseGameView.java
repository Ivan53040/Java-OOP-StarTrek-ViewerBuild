package sttrswing.view;

import sttrswing.controller.GameController;
import sttrswing.controller.ImageLoader;
import sttrswing.model.interfaces.GameModel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * A {@link LoseGameView} that shows the mission loss screen to the user, including an image loaded
 * with {@link ImageLoader}.
 */
public class LoseGameView extends View {

  /** The game model. */
  private final GameModel game;
  /** The game controller. */
  private final GameController controller;
  /** The lose text area. */
  private final JTextArea loseText;
  /** The details text area. */
  private final JTextArea details;
  /** The image loader for the destroyed image. */
  private final ImageLoader imageLoader = new ImageLoader("./data/destroyed.jpg");

  /**
   * Constructs a new {@link LoseGameView} using access to the given game state 
   * and controller state to access relevant data and bind relevant method calls 
   * from both states to our listeners.
   *
   * @param game       game state we use to construct this view for both 
   *                   information and method calls for relevant action listeners if any.
   * @param controller controller state we use for both information access and 
   *                   method calls for relevant action listeners if any.
   */
  public LoseGameView(final GameModel game, final GameController controller) {
    super("Star Trek | Game Over");
    System.out.println(imageLoader.get());
    this.game = game;
    this.controller = controller;
    JLabel destroyedEnterprise = new JLabel(imageLoader.get());
    this.setLayout(new GridLayout(0, 1));
    this.add(destroyedEnterprise);
    this.loseText = this.buildTextArea("Game Over!");
    this.loseText.setFont(new Font("Arial", Font.BOLD, 48));
    this.details = this.buildTextArea(
        "The Enterprise has been shattered, its remnants float "
            + "in the cold dark of space...\n\n\nKlingons Left:"
            + game.totalKlingonCount() + "\n" + "Starbases Remaining:"
            + game.totalStarbaseCount());
    this.details.setFont(new Font("Arial", Font.BOLD, 24));
    this.add(this.loseText);
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

  public JTextArea getLoseText() {
    return this.loseText;
  }

}
