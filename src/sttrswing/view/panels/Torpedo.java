package sttrswing.view.panels;

import sttrswing.view.View;
import sttrswing.model.interfaces.GameModel;
import sttrswing.controller.GameController;
import sttrswing.view.guicomponents.DirectionButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Shows controls for choosing the direction fire a torpedo using 8 {@link DirectionButton}s.
 */
public class Torpedo extends View {
    // Declare the game state, controller and list of direction buttons
    /** The game model. */
    private final GameModel game;
    /** The game controller. */
    private final GameController controller;
    /** The list of direction buttons. */
    private final ArrayList<DirectionButton> directionButtons;

    /**
     * Construct a new {@link Torpedo} instance.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public Torpedo(GameModel game, GameController controller) {
        // Set the title and the game model, controller and list of direction buttons
        super("Torpedo Controls");
        this.game = game;
        this.controller = controller;
        this.directionButtons = new ArrayList<>();

        // Set up the layout using a BorderLayout of 8px horizontal and vertical gaps
        this.setLayout(new java.awt.BorderLayout(8, 8));
        // Create a 3x3 grid for 8 direction buttons (center is empty) using a 
        // GridLayout of 3 rows, 3 columns, 6px horizontal and vertical gaps
        javax.swing.JPanel grid = new javax.swing.JPanel(
            new java.awt.GridLayout(3, 3, 6, 6));
        // Layout: 4 ↖, 3 ↑, 2 ↗, 5 ←, (empty), 1 →, 6 ↙, 7 ↓, 8 ↘
        int[] layout = new int[] {4, 3, 2, 5, 0, 1, 6, 7, 8};
        for (int dir : layout) {
            if (dir == 0) {
                // Add an empty panel to the grid
                grid.add(new javax.swing.JPanel());
            } else {
                //Add a direction button to the grid which will fire a torpedo
                // in the given direction
                grid.add(buildButton(dir, e -> {
                    this.game.fireTorpedo(dir);
                    this.game.turn();
                    // Return to default view to show results
                    this.controller.setDefaultView(this.game);
                }));
            }
        }
        // Add the grid to the main layout
        this.add(grid, java.awt.BorderLayout.CENTER);
    }

    /** 
     * Build a {@link DirectionButton}, tracking it and the given 
     * {@link ActionListener} listener.
     * 
     * @param direction - direction this button is meant to represent.
     * @param listener - {@link ActionListener} we want to bind to the newly 
     * instantiated {@link DirectionButton}.
     * @return A tracked, configured {@link DirectionButton}.
     */
    public DirectionButton buildButton(int direction, ActionListener listener) {
        // Create a new DirectionButton with the given direction and true for consume turn
        DirectionButton button = new DirectionButton(direction, true);
        // If the listener is not null, add it to the button and track it
        if (listener != null) {
            button.addActionListener(listener);
            this.trackListener(listener);
        }
        // Add the button to the list of direction buttons
        this.directionButtons.add(button);
        // Add the button to the list of buttons for cleanup
        this.trackButton(button);
        return button;
    }

}
