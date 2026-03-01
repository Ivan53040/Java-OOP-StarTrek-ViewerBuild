package sttrswing.view.panels;

import sttrswing.view.guicomponents.DirectionButton;
import java.awt.event.ActionListener;
import sttrswing.view.View;
import sttrswing.model.interfaces.GameModel;
import sttrswing.controller.GameController;
import sttrswing.view.guicomponents.Slider;
import javax.swing.JSlider;

/**
 * Responsible for displaying an 8 button 'wheel' of {@link DirectionButton} 
 * for the 8 possible directions the {@link Enterprise} could be along and 
 * a {@link Slider} for how far to move it in that direction.
 */
public class QuadrantNavigation extends View {

    /** The game model. */
    private final GameModel game;
    /** The game controller. */
    private final GameController controller;
    /** The slider for navigation. */
    private final Slider slider;

    /**
     * Constructs a new instance of {@link QuadrantNavigation}.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public QuadrantNavigation(GameModel game, GameController controller) {
        // Set the title and the game model and controller
        super("Quadrant Navigation");
        this.game = game;
        this.controller = controller;

        // Set up the layout using a BorderLayout of 8px horizontal and vertical gaps
        this.setLayout(new java.awt.BorderLayout(8, 8));
        
        //---------right----------------//
        // Create slider with appropriate range for Enterprise movement distance
        this.slider = new Slider(8, JSlider.VERTICAL);
        // Add the slider to the right of the layout
        this.add(this.slider, java.awt.BorderLayout.EAST);

        //---------left----------------//
        // Create a 3x3 grid for 8 direction buttons 
        javax.swing.JPanel grid = new javax.swing.JPanel(new java.awt.GridLayout(3, 3, 6, 6));
        // Create an array of directions: 4 ↖, 3 ↑, 2 ↗, 5 ←, (empty), 1 →, 6 ↙, 7 ↓, 8 ↘
        int[] layout = new int[] {4, 3, 2, 5, 0, 1, 6, 7, 8};
        // Loop through the directions and add the direction buttons to the grid
        for (int dir : layout) {
            if (dir == 0) {
                // Add an empty panel to the grid
                grid.add(new javax.swing.JPanel());
            // Add a direction button to the grid
            } else {
                //Function of the button when clicked, move the Enterprise within the quadrant
                grid.add(buildButton(dir, e -> {
                    // Move the Enterprise within the quadrant with the selected distance
                    int distance = this.slider.getValue();
                    this.game.moveWithinQuadrant(dir, distance);
                    // Advance game time
                    this.game.turn();
                    // Return to default view to show movement results
                    this.controller.setDefaultView(this.game);
                }));
            }
        }
        // Add the grid to the center of the layout
        this.add(grid, java.awt.BorderLayout.CENTER);
        
    }

    /**
     * Builds a {@link DirectionButton} for the given direction, (that should 
     * indicate it consumes a turn) and attaches the given {@link ActionListener} 
     * to said button. It also sets up tracking both the created button and
     * given {@link ActionListener} for use in the cleanup method of its 
     * parent {@link View} class.
     * 
     * @param direction - direction we want the new direction button to represent
     * @param listener - listener that controls triggering a game turn, moving 
     * the player between quadrants etc.
     * @return the newly built and tracked {@link DirectionButton}.
     */
    public DirectionButton buildButton(int direction, ActionListener listener) {
        // Create a new DirectionButton with the given direction
        DirectionButton button = new DirectionButton(direction, true);
        // If the listener is not null, add it to the button and track it
        if (listener != null) {
            button.addActionListener(listener);
            this.trackListener(listener);
        }
        // Add the button to the list of buttons for cleanup
        this.trackButton(button);
        return button;
    }

    /**
     * For testability reasons, should return the {@link Slider} used for the 
     * in quadrant navigation views distance.
     * 
     * @return the Slider used for the in quadrant navigation views distance.
     */
    public Slider getSlider() {
        // Return the slider
        return this.slider;
    }
    
}
