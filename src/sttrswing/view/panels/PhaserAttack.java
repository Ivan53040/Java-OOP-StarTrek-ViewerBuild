package sttrswing.view.panels;

import sttrswing.view.guicomponents.Slider;
import sttrswing.view.View;
import sttrswing.view.Pallete;
import sttrswing.model.interfaces.GameModel;
import sttrswing.controller.GameController;
import javax.swing.*;
import java.awt.*;

/**
 * Displays the controls for choosing how much energy to allocate to a 
 * phaser attack using a {@link Slider}.
 */
public class PhaserAttack extends View {

    /** The game model. */
    private final GameModel game;
    /** The game controller. */
    private final GameController controller;
    /** The slider for energy selection. */
    private final Slider slider;
    /** The fire phasers button. */
    private final JButton firePhasersButton;

    /**
     * Constructs a new {@link PhaserAttack} instance.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public PhaserAttack(GameModel game, GameController controller) {
        // Set the title and the game model and controller
        super("Phaser Controls");
        this.game = game;
        this.controller = controller;
        
        // Set up the layout - half-half split between slider and button
        // using a GridLayout of 1 row, 2 columns, 10px gap
        this.setLayout(new GridLayout(1, 2, 10, 10));

        
        //---------left----------------//
        // Create slider with appropriate range for phaser energy
        this.slider = new Slider(2401, JSlider.VERTICAL);
        // Left panel with slider (50% width) using a BorderLayout and style it
        JPanel leftPanel = new JPanel(new BorderLayout());
        // Set the background color of the left panel to GREY
        leftPanel.setBackground(Pallete.GREY.color());
        // Add the slider to the center of the left panel
        leftPanel.add(this.slider, BorderLayout.CENTER);
        // Add the left panel to the main layout
        this.add(leftPanel, BorderLayout.WEST);
        
        //---------right----------------//
        // Create Fire Phasers button which will fire phasers with the selected energy
        this.firePhasersButton = this.buildButton("⏳ Fire Phasers!", 
            e -> {
                // Get the value of the slider
                int phaserEnergy = this.slider.getValue();
                // Fire phasers with the selected energy
                this.game.firePhasers(phaserEnergy);
                // Advance game time
                this.game.turn();
                // Return to default view to show results
                this.controller.setDefaultView(this.game);
            });
        // Right panel as one big clickable button (50% width) using a GridBagLayout and style it
        this.firePhasersButton.setLayout(new GridBagLayout());
        // Set the background color of the right panel to REDPALE
        this.firePhasersButton.setBackground(Pallete.REDPALE.color());
        // Add the fire phasers button to the main layout
        this.add(this.firePhasersButton, BorderLayout.EAST);
    }

    /**
     * Present for Testability reasons, returns the Slider used in the phaser attack screen.
     * 
     * @return the Slider used in the phaser attack screen.
     */
    public Slider getSlider() {
        // Return the slider
        return this.slider;
    }   
}
