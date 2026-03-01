package sttrswing.view.panels;

import sttrswing.model.interfaces.GameModel;
import sttrswing.controller.GameController;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import sttrswing.view.View;

/**
 * Presents a list of the main game options in a panel for the user.
 */
public class Options extends View {

    /**
     * Constructs a Options view.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public Options(GameModel game, GameController controller) {
        //Setup the layout and add the buttons to the view
        super("Options");
        this.setLayout(new java.awt.GridLayout(7, 1, 8, 8));
        this.setBackground(sttrswing.view.Pallete.BLACK.color());
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        this.buildOptionButtons(game, controller);
    }

    /**
     * Constructs the various game options as {@link JButton} to be presented 
     * on this {@link Options}.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     */
    public void buildOptionButtons(GameModel game, GameController controller) {
        //Build the quadrant navigation button which will open the quadrant navigation view
        JButton quadrantNavigationButton = this.buildButton("Quadrant Navigation", 
            e -> controller.setQuadrantNavigationView(game));
        style(quadrantNavigationButton);
        //Build the warp navigation button which will open the warp navigation view
        JButton warpNavigationButton = this.buildButton("Warp Navigation", 
            e -> controller.setWarpNavigationView(game));
        style(warpNavigationButton);
        //Add all the buttons to the view
        this.add(quadrantNavigationButton);
        this.add(warpNavigationButton);
        this.add(this.phasersButton(game, controller));
        this.add(this.torpedoButton(game, controller));
        this.add(this.shieldsButton(game, controller));
        this.add(this.scanInQuadrantButton(game, controller));
        this.add(this.scanNearbyQuadrantsButton(game, controller));
    }

    /**
     * Creates a JButton configured to enable scanning the current quadrant, 
     * triggering a game turn and then transitioning to the current quadrant scan view.
     * 
     * @param game - reference to the game state for use in the action listener.
     * @param controller - reference to the controller, for use in the action listener.
     * @return the scan in quadrant button
     */
    public JButton scanInQuadrantButton(GameModel game, GameController controller) {
        //Build the scan in quadrant button
        JButton button = this.buildButton("⌛ Short Range Scan", e -> {
            // Trigger a game turn and scan the current quadrant
            game.turn();
            game.scanQuadrant();
            // Set the current quadrant scan view
            controller.setCurrentQuadrantScanView(game);
        });
        // Style the button
        style(button);
        return button;
    }

    /**
     * Creates a JButton configured to enable scanning nearby quadrants, 
     * triggering a game turn and then transitioning to the sector scan view.
     * 
     * @param game - reference to the game state for use in the action listener.
     * @param controller - reference to the controller, for use in the action listener.
     * @return the scan nearby quadrants button
     */
    public JButton scanNearbyQuadrantsButton(GameModel game, GameController controller) {
        //Build the scan nearby quadrants button
        JButton button = this.buildButton("⌛ Long Range Scan", e -> {
            // Trigger a game turn and scan the nearby quadrants
            game.turn();
            // Set the scan nearby quadrant view
            controller.setScanNearbyQuadrantView(game);
        });
        // Style the button
        style(button);
        return button;
    }

    /**
     * Creates a {@link JButton} configured to enable setting the 
     * {@link GameController} to the shields view.
     * 
     * @param game - game state
     * @param controller - game controller used for manipulating the user view
     * @return the shields button
     */
    public JButton shieldsButton(GameModel game, GameController controller) {
        //Create the shields button which will open the shields view
        JButton button = this.buildButton("Shields", e -> controller.setShieldsView(game));
        //Check if the player has spare energy, if so enable the button
        button.setEnabled(game.hasSpareEnergy());
        // Style the button
        style(button);
        return button;
    }

    /**
     * Constructs a {@link JButton} that has an {@link ActionListener} bound 
     * to it that will request the controller to go to the torpedo view.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     * @param controller - controller state we use for both information 
     * access and method calls for relevant action listeners if any.
     * @return the torpedo button
     */
    public JButton torpedoButton(GameModel game, GameController controller) {
        //Create the torpedo button which will open the torpedo view
        String label = game.spareTorpedoes() + " x Torpedoes";
        JButton button = this.buildButton(label, e -> controller.setTorpedoView(game));
        //Check if the player has spare torpedoes, if so enable the button
        button.setEnabled(game.hasSpareTorpedoes());
        // Style the button
        style(button);
        return button;
    }

    /**
     * Creates a {@link JButton} with an action bound that will tell the 
     * controller to go to the phaser attack screen. 
     * Makes to tracks the {@link JButton} and any {@link ActionListener}s 
     * created for when the {@link View} cleanup method is called during 
     * screen destruction. 
     * Will set the button to disabled if there is not sufficient energy 
     * for phasers to be an option.
     * 
     * @param game - Game state for use in any relevant actions
     * @param controller - Reference to the Controller, so we can alter screens.
     * @return the phasers button
     */
    public JButton phasersButton(GameModel game, GameController controller) {
        //Create the phasers button which will open the phaser attack view
        JButton button = this.buildButton("Phasers", e -> controller.setPhaserAttackView(game));
        //Check if the player has spare energy, if so enable the button
        button.setEnabled(game.hasSpareEnergy());
        // Style the button
        style(button);
        return button;
    }

    /**
     * Styles the given {@link JButton} to match the style of the Options view.
     * 
     * @param button - the {@link JButton} to style.
     */
    private void style(JButton button) {
        // Set the background color to white
        button.setBackground(sttrswing.view.Pallete.WHITEMID.color());
        // Set the foreground color to black
        button.setForeground(sttrswing.view.Pallete.BLACK.color());
        // Set the font to bold
        button.setFont(button.getFont().deriveFont(java.awt.Font.BOLD));
    }

}