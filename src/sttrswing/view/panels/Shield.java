package sttrswing.view.panels;

import sttrswing.view.View;
import sttrswing.view.Pallete;
import sttrswing.model.interfaces.GameModel;
import sttrswing.controller.GameController;
import sttrswing.view.guicomponents.Slider;
import javax.swing.*;
import java.awt.*;


/**
 * Displays the controls for choosing how much energy to re-allocate to 
 * shields using a {@link Slider}.
 */

public class Shield extends View {

    /** The game model. */
    private final GameModel game;
    /** The game controller. */
    private final GameController controller;
    /** The slider for shield adjustment. */
    private final Slider slider;
    /** The adjust shields button. */
    private final JButton adjustShieldsButton;

    /**
     * Constructs a new {@link Shield} instance.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public Shield(GameModel game, GameController controller) {
        // Set the title and the game model and controller
        super("Shield Controls");
        this.game = game;
        this.controller = controller;
                
        // Set up the layout - half-half split between slider and button
        this.setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, 10px gap
        
        //--------left----------------//
        // Create slider with appropriate range for shield adjustment
        this.slider = new Slider(2401, JSlider.VERTICAL);
        // Left panel with slider (50% width) and style it
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Pallete.GREY.color());
        // Add the slider to the center of the left panel
        leftPanel.add(this.slider, BorderLayout.CENTER);
        // Add the left panel to the main layout
        this.add(leftPanel);

        //---------right----------------//
        // Create adjust shields button which will adjust the shields with the selected energy
        this.adjustShieldsButton = this.buildButton("⏳ Adjust Shields!", 
            e -> {
                // Adjust the shields with the selected energy
                int shieldAdjustment = this.slider.getValue();
                this.game.shields(shieldAdjustment);
                // Advance game time
                this.game.turn();
                // Return to default view to show results
                this.controller.setDefaultView(this.game);
            });
        
        // Right panel as one big clickable button (50% width) and style it
        this.adjustShieldsButton.setLayout(new GridBagLayout());
        this.adjustShieldsButton.setBackground(Pallete.CYANPALE.color());
        // Add the adjust shields button to the main layout
        this.add(this.adjustShieldsButton);
    }

    /**
     * For Testability Purposes, lets us access the {@link Slider} that 
     * should be held by the Shield panel.
     * 
     * @return {@link Slider} held by the Shield panel.
     */
    public Slider getSlider() {
        // Return the slider
        return this.slider;
    }
    

}
