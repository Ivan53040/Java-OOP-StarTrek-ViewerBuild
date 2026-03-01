package sttrswing.view;

import sttrswing.controller.GameController;
import sttrswing.model.interfaces.GameModel;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.GridLayout;


/**
 * A {@link View} configured for the starting screen view where it should 
 * have a {@link JTextArea} area holding the welcome message.
*/
public class StartView extends View {

    /** The start button. */
    private final JButton button;
    /** The welcome message text area. */
    private final JTextArea welcomeMessage;
    /** The game model. */
    private final GameModel game;
    /** The game controller. */
    private final GameController controller;

    /**
     * Constructs a new StartView using access to the given game state and 
     * controller state to access relevant data and bind relevant method calls 
     * from both states to our listeners.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public StartView(final GameModel game, final GameController controller) {
        // Set the title and the layout and the game and controller
        super("Start");
        this.game = game;
        this.controller = controller;
        
        // Half-and-half layout (2 rows, 1 column)
        this.setLayout(new GridLayout(2, 1));
        
        // Setup the welcome message
        this.welcomeMessage = new JTextArea(
            "WELCOME CAPTAIN\nClick the Start button to start the game!");
        this.welcomeMessage.setFont(new java.awt.Font("Arial", 
            java.awt.Font.BOLD, 20));
        
        // Setup the START button
        this.button = new JButton("START");
        this.button.setFont(new java.awt.Font("Arial", 
            java.awt.Font.BOLD, 18));
        
        // Wire the START button to transition to default view
        this.button.addActionListener(e -> this.controller.setDefaultView(this.game));
        this.trackListener(e -> this.controller.setDefaultView(this.game));
        this.trackButton(this.button);
        
        // Add components to layout (top half = welcome, bottom half = button)
        this.add(this.welcomeMessage);
        this.add(this.button);
    }

    /**
     * Return the {@link JButton} instance for this start screen, only really 
     * here for testability reasons.
     * 
     * @return the {@link JButton} instance for this start screen
     */ 
    public JButton getButton() {
        return this.button;
    }

    /**
     * Returns the {@link JTextArea} instance for this starting screen, 
     * only really here for testability reasons.
     * 
     * @return the {@link JTextArea} instance for this starting screen
     */
    public JTextArea getText() {
        return this.welcomeMessage;
    }
}

