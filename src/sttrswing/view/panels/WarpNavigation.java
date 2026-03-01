package sttrswing.view.panels;

import sttrswing.view.View;
import sttrswing.model.interfaces.GameModel;
import sttrswing.controller.GameController;
import sttrswing.view.guicomponents.Slider;
import javax.swing.JSlider;
import sttrswing.view.guicomponents.DirectionButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Displays and handles the actions for managing warp based navigation, 
 * creating a {@link Slider} with a maximum value of 8 and minimum value 
 * of 1 to control distance, surrounded by a ring of {@link DirectionButton}s 
 * one for each of the 8 possible directions that will allow us progress a 
 * game turn, move between quadrants in the game and then return to our 
 * default view.
 */
public class WarpNavigation extends View {

    /** The game model. */
    private final GameModel game;
    /** The game controller. */
    private final GameController controller;
    /** The slider for warp speed. */
    private final Slider slider;
    /** The list of direction buttons. */
    private final ArrayList<DirectionButton> directionButtons;

    /**
     * Construct a new {@link WarpNavigation} instance.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public WarpNavigation(GameModel game, GameController controller) {
        // Set the title and the game model and controller
        super("Warp Navigation");
        this.game = game;
        this.controller = controller;

        // Set up the layout 
        this.setLayout(new java.awt.BorderLayout(8, 8));
        
        //---------right----------------//
        // Slider range 1-9 for warp navigation distance
        this.slider = new Slider(8, JSlider.VERTICAL);
        // Add the slider to the right of the layout
        this.add(this.slider, java.awt.BorderLayout.EAST);

        //----------left----------------//
        // Create a new ArrayList of DirectionButtons
        this.directionButtons = new ArrayList<>();
        // Create a 3x3 grid for 8 direction buttons (center is empty) 
        // using a GridLayout of 3 rows, 3 columns, 6px horizontal and vertical gaps
        javax.swing.JPanel grid = new javax.swing.JPanel(
            new java.awt.GridLayout(3, 3, 6, 6));
        // Layout: 4 ↖, 3 ↑, 2 ↗, 5 ←, (empty), 1 →, 6 ↙, 7 ↓, 8 ↘
        int[] layout = new int[] {4, 3, 2, 5, 0, 1, 6, 7, 8};
        for (int dir : layout) {
            if (dir == 0) {
                grid.add(new javax.swing.JPanel());
            } else {
                //Add a direction button
                grid.add(buildButton(dir, e -> {
                    // Move the Enterprise between quadrants with the selected distance
                    // from the slider
                    int distance = this.slider.getValue();
                    // Advance game time before moving
                    this.game.turn();
                    this.game.moveBetweenQuadrants(dir, distance);
                    // Return to default view to show results
                    this.controller.setDefaultView(this.game);
                }));
            }
        }
        // Add the grid to the left of the layout
        this.add(grid, java.awt.BorderLayout.CENTER);
    }

    /**
     * Exists for testability reasons to allow us to confirm the {@link Slider} 
     * matches some expected constraints.
     * 
     * @return the {@link Slider} used for warp factor/distance control.
     */
    public Slider getSlider() {
        // Return the slider
        return this.slider;
    }

    /**
     * Builds a {@link DirectionButton} for the given direction, 
     * (that should indicate it consumes a turn) and attaches the given 
     * {@link ActionListener} to said button. 
     * It also sets up tracking both the created button and given 
     * {@link ActionListener} for use in the cleanup method of its parent 
     * {@link View} class.
     * 
     * @param direction - direction we want the new direction button to represent
     * @param listener - listener that controls triggering a game turn, moving 
     * the player between quadrants etc.
     * @return the newly built and tracked {@link DirectionButton}.
     */
    public DirectionButton buildButton(int direction, ActionListener listener) {
        // Create a new DirectionButton with the given direction and true for consume turn
        DirectionButton button = new DirectionButton(direction, true);
        // If the listener is not null, add it to the button and track it
        if (listener != null) {
            button.addActionListener(listener);
            this.trackListener(listener);
        }
        // Add the button to the list of direction buttons for cleanup
        this.directionButtons.add(button);
        // Add the button to the list of buttons for cleanup
        this.trackButton(button);
        return button;
    }
    
}
