package sttrswing.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import sttrswing.model.interfaces.GameModel;
import sttrswing.view.View;
import sttrswing.view.StartView;
import sttrswing.view.WinGameView;
import sttrswing.view.LoseGameView;
import sttrswing.view.panels.QuadrantNavigation;
import sttrswing.view.panels.QuadrantScan;
import sttrswing.view.panels.NearbyQuadrantScan;
import sttrswing.view.panels.WarpNavigation;
import sttrswing.view.panels.PhaserAttack;
import sttrswing.view.panels.Torpedo;
import sttrswing.view.StandardLayoutView;
import sttrswing.view.panels.Shield;

/**
 * Responsible for managing a {@link JFrame} that it manipulates as well as coordinating 
 * the interactions between the {@link GameModel} and the various {@link View}s.
 */
public class GameController extends JFrame {

    /** The current view being displayed. */
    private View currentView;
    /** The menu bar for the application. */
    private JMenu menu;
    /** The game model containing the game state. */
    private GameModel gameModel;
    
    /**
     * Constructs a new GameController that is responsible for managing a JFrame they extend,
     * and coordinating the GameModel and various Views.
     * 
     * @param windowSize the dimensions we wish to set the created JFrame to.
     * @param game the game this controller will be responsible for
     */
    public GameController(Dimension windowSize, GameModel game) {
        // Set the window size
        super();
        this.setSize(windowSize);
        // Set the game model
        this.gameModel = game;
        // Set the default close operation to use our custom end method
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                end();
            }
        });
        
        // Create menu bar and menu
        this.menu = new JMenu("File");
        
        // Create Save menu item
        JMenuItem saveItem = new JMenuItem("Save");
        // Add an action listener to the save item
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current working directory and create data directory path
                String currentDir = System.getProperty("user.dir");
                String dataDirPath = currentDir + File.separator + "data";
                
                // Use fixed filename: save.trek
                String filePath = dataDirPath + File.separator + "save.trek";
                
                // Export the game data
                String gameData = gameModel.export();
                // Create a new game saver
                GameSaver saver = new GameSaver(gameData, filePath);
                // Save the game data
                saver.save();
                
                // Check if the game data was saved successfully
                if (saver.success()) {
                    // Show a success message
                    JOptionPane.showMessageDialog(GameController.this, 
                        "Game saved successfully", "Save Game", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(GameController.this, 
                        "Failed to save game!", "Save Game", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Create Load menu item  
        JMenuItem loadItem = new JMenuItem("Load");
        // Add an action listener to the load item
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current working directory and create data directory path
                String currentDir = System.getProperty("user.dir");
                String dataDirPath = currentDir + File.separator + "data";
                File dataDir = new File(dataDirPath);
                
                // Use fixed filename: save.trek
                String filePath = dataDirPath + File.separator + "save.trek";
                File saveFile = new File(filePath);
                
                // Check if save file exists
                if (!saveFile.exists()) {
                    JOptionPane.showMessageDialog(GameController.this, 
                        "No save file found. Please save a game first.", "Load Game", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Create a new game loader
                GameLoader loader = new GameLoader(filePath);
                // Load the game data
                loader.load();
                
                // Check if the game data was loaded successfully
                if (loader.success()) {
                    // Use a try-catch block to handle the Exception
                    try {
                        // Build new Enterprise and Galaxy from loaded data
                        sttrswing.model.Enterprise enterprise = 
                            loader.buildEnterprise();
                        // Build new Galaxy from loaded data
                        sttrswing.model.Galaxy galaxy = 
                            loader.buildGalaxy();
                        
                        // Load the game state into the game model
                        gameModel.load(enterprise, galaxy);
                        
                        // Check if the game model is a game
                        if (gameModel instanceof sttrswing.model.Game) {
                            sttrswing.model.Game game = 
                                (sttrswing.model.Game) gameModel;
                            // Get the current quadrant x coordinate
                            int currentQuadrantX = 
                                game.getCurrentQuadrant().getX();
                            // Get the current quadrant y coordinate
                            int currentQuadrantY = 
                                game.getCurrentQuadrant().getY();
                            // Get the enterprise quadrant x coordinate
                            int enterpriseQuadrantX = enterprise.getX();
                            // Get the enterprise quadrant y coordinate
                            int enterpriseQuadrantY = enterprise.getY();
                            
                            // Calculate the difference to move to the correct quadrant
                            int deltaX = enterpriseQuadrantX - currentQuadrantX;
                            int deltaY = enterpriseQuadrantY - currentQuadrantY;
                            
                            // If we need to move to a different quadrant, 
                            // use attemptMoveBetweenQuadrants
                            if (deltaX != 0 || deltaY != 0) {
                                // Use attemptMoveBetweenQuadrants to change current quadrant 
                                // without moving Enterprise
                                sttrswing.model.XyPair vector = 
                                    new sttrswing.model.XyPair(deltaX, deltaY);
                                game.attemptMoveBetweenQuadrants(vector);
                            }
                            
                            // Place Enterprise at a random empty sector 
                            // within the current quadrant
                            sttrswing.model.XyPair randomSector = 
                                game.getCurrentQuadrant().getRandomEmptySector();
                            if (randomSector != null) {
                                enterprise.setX(randomSector.getX());
                                enterprise.setY(randomSector.getY());
                            }
                        }
                        // Show a success message if the game was loaded successfully
                        String successMessage = "Game loaded successfully!\n\nLoader details: "
                            + loader.toString();
                        JOptionPane.showMessageDialog(GameController.this, 
                            successMessage, "Load Game", 
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Refresh the current view to display the loaded game
                        setDefaultView(gameModel);
                    } catch (Exception ex) {
                        // Show a detailed error message if the game was not loaded successfully
                        String errorMessage = "Failed to load game: "
                            + ex.getMessage() + "\n\nLoader details: "
                            + loader.toString();
                        JOptionPane.showMessageDialog(GameController.this, 
                            errorMessage, "Load Game", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Show a detailed error message if the game file 
                    // was not loaded successfully
                    String errorMessage = "Failed to load game file!\n\nLoader details: "
                        + loader.toString();
                    JOptionPane.showMessageDialog(GameController.this, 
                        errorMessage, "Load Game", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add the save and load items to the menu
        this.menu.add(saveItem);
        this.menu.add(loadItem);
        // Create menu bar and add the menu to it
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(this.menu);
        // Set the menu bar
        this.setJMenuBar(menuBar);
    }

    /**
     * Exposed for testability reasons.
     * 
     * @return the JMenuBar used for the save/load options.
     */
    public JMenu getFileMenu() {
        // Return the menu
        return this.menu;
    }

    /**
     * End Controller/the {@link JFrame}.
     * Properly disposes of resources and exits the application.
     */
    public void end() {
        // Clean up the current view before closing
        if (this.currentView != null) {
            this.currentView.cleanup();
        }
        
        // Dispose of the JFrame and free system resources
        this.dispose();
        // Exit the application
        System.exit(0);
    }

    /**
     * Calls start, begins the visualisation of the program etc. 
     * Creates a {@link StartView} and sets for the {@link GameController}s current view.
     * 
     * @param game game state we need to refer to and/or manipulate for this view.
     */
    public void start(GameModel game) {
        // Set the title to Welcome
        String title = "Star Trek | Welcome";
        this.setTitle(title);
        // Create a new standard layout view
        StandardLayoutView layout = new StandardLayoutView(title);
        // Create a new quadrant scan
        QuadrantScan qs = new QuadrantScan(game);
        layout
            .addViewPanel(new sttrswing.view.StartView(game, this))
            .addViewPanel(new sttrswing.view.panels.EnterpriseStatus(game))
            .addViewPanel(qs)
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;
        // Display the view
        this.displayCurrentView();
    }

    /** 
     * Display the winning game over screen.
     * 
     * @param game gamestate we wish to present some of to the user.
     */
    public void setWinGameView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek - Victory!");
        // Create a new win game view
        this.currentView = new WinGameView(game, this);
        // Display the view
        this.displayCurrentView();
    }

    /**Display the losing game over screen.
     * 
     * @param game gamestate we wish to present some of to the user.
     */
    public void setLoseGameView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek - Defeat!");
        // Create a new lose game view
        this.currentView = new LoseGameView(game, this);
        // Display the view
        this.displayCurrentView();
    }

    /**
     * Takes the given game as a reference,
     * creates a screen for representing the current visible sector state and 
     * presenting controls to allow you to pick a system (square) in the system 
     * to head to in a straight line from the current position.
     * 
     * @param game game state we need to refer to and/or manipulate for this view.
     */
    public void setQuadrantNavigationView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek | Quadrant Navigation");
        
        // Create base layout and add 2 specific panels (Quadrant Navigation and Options)
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new QuadrantNavigation(game, this))
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the quadrant navigation view
        this.displayCurrentView();
    }


    /**
     * Takes the given game as a reference and creates a screen for representing 
     * the adjacent quadrants state, showing number of klingons, starbases and 
     * stars in each.
     * 
     * @param game - the game state that will be represented by our scan quadrant screen.
     */
    public void setScanNearbyQuadrantView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek | Long Range Scan Results");
        
        // Create base layout and add 2 specific panels (Nearby Quadrant Scan and Options)
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new NearbyQuadrantScan(game))
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the long range scan view
        this.displayCurrentView();
    }

    /**
     * Takes the given game as a reference, creates a screen for representing 
     * the current visible quadrant state, and allowing you to set direction and 
     * speed for a movement action.
     * 
     * @param game - the game state that will be manipulated and represented by 
     * our quadrant nav screen.
    */
    public void setWarpNavigationView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek | Warp Navigation");
        // Create base layout and add 2 specific panels (Warp Navigation and Options)
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new WarpNavigation(game, this))
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the warp navigation view
        this.displayCurrentView();
    }

    /**
     * Creates a screen for manipulating a phaser attack and sets the {@link GameController} to it.
     * 
     * @param game - game state we need to refer to and/or manipulate for this view.
     */
    public void setPhaserAttackView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek | Phaser Controls");
        
        // Create base layout and add 2 specific panels (Phaser Attack and Options)
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new PhaserAttack(game, this))
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the phaser attack view
        this.displayCurrentView();
    }

    /**
     * Takes the given Game as a reference. Creates a screen for manipulating a torpedo attack.
     * 
     * @param game - the game state that will be manipulated and represented by our torpedo screen.
     */
    public void setTorpedoView(GameModel game) {
        // Set the title
        this.setTitle("Star Trek | Torpedo Controls");
        
        // Create base layout and add 2 specific panels (Torpedo and Options)
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new Torpedo(game, this))
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the torpedo view
        this.displayCurrentView();
    }
    
    /**
     * This is the default landing view, it will check if the game is over yet, 
     * if it is it will call a game overview, otherwise it will present the 
     * default view as per below.
     * Sets the current view to present the default arrangement of subviews. 
     * Including: Current map of the quadrant Status of the Enterprise 
     * Menu of Game Options
     * 
     * @param game the game model to set the default view for
     */
    public void setDefaultView(GameModel game) {        
        // Get the last action report
        String report = game.lastActionReport();
        //Set the title
        String title;
        // If there's a non-empty report, show it in title
        if (report != null && !report.isEmpty()) {
            title = "Star Trek | " + report;
        } else {
            // Default title when no action report
            title = "Star Trek";
        }
        this.setTitle(title);
        
        // Create base layout and add options panel
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the default view
        this.displayCurrentView();
    }

    /**
     * Sets the current view to present the Short Range Scan results. 
     * Including: Current Map of the Quadrant, Enterprise Status, 
     * Options Menu, and Empty panel for scan results.
     * 
     * @param game - game state this view can refer to/manipulate as needed.
     */
    public void setCurrentQuadrantScanView(GameModel game) {
        // Set title to Short Range Scan
        this.setTitle(game.lastActionReport());
        
        // Create base layout and add options panel
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;

        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the short range scan view
        this.displayCurrentView();
    }

    /**
     * Sets the current view to present relevant views for adjusting the 
     * players shield value. Including: Current Map of the Quadrant 
     * Shield Controls EnterpriseStatus Game Options Menu
     * 
     * @param game - game state this view can refer to/manipulate as needed.
     */
    public void setShieldsView(GameModel game) {
        // Set title to Shield Controls
        this.setTitle("Star Trek | Shield Controls");
        
        // Create base layout and add specific panels
        StandardLayoutView layout = this.createBaseLayout(game);
        layout
            .addViewPanel(new Shield(game, this))
            .addViewPanel(new sttrswing.view.panels.Options(game, this));
        this.currentView = layout;
        // Check if game is over first
        if (this.checkGameEndState(game)) {
            return;
        }
        // If game is not over, display the shields view
        this.displayCurrentView();
    }

    /**
     * Helper method to create a StandardLayoutView with the base panels 
     * (QuadrantScan and EnterpriseStatus).
     * This creates the foundation that most views use, and then specific 
     * views can add their additional panels.
     * 
     * @param game - the game state
     * @return the StandardLayoutView with base panels configured
     */
    private StandardLayoutView createBaseLayout(GameModel game) {
        // Create a new standard layout view
        StandardLayoutView layout = new StandardLayoutView(getTitle());
        // Create a new quadrant scan
        QuadrantScan qs = new QuadrantScan(game);
        // Add the quadrant scan and enterprise status to the layout(top left and top right)
        layout
            .addViewPanel(qs)
            .addViewPanel(new sttrswing.view.panels.EnterpriseStatus(game));
        // Return the layout
        return layout;
    }

    /**
     * Helper method to check if the game is won or lost and handle the 
     * appropriate view transition.
     * If the game is over, this method will set the appropriate end-game 
     * view and return true.
     * If the game is still ongoing, this method returns false.
     * 
     * @param game - the game state to check
     * @return true if the game is over (won or lost), false if the game continues
     */
    private boolean checkGameEndState(GameModel game) {
        // Check if the game has been won
        if (game.hasWon()) {
            // Set the win game view
            this.setWinGameView(game);
            // Return true
            return true;
        }
        // Check if the game has been lost
        if (game.hasLost()) {
            // Set the lose game view
            this.setLoseGameView(game);
            // Return true
            return true;
        }
        // If the game is not over, return false
        return false;
    }

    /**
     * Helper method to display the current view by removing all existing 
     * components and adding the new view.
     * This reduces code duplication across all view-setting methods.
     */
    private void displayCurrentView() {
        // Clean up the previous view before switching to a new one
        View previousView = this.currentView;
        
        // Remove all components from the content pane
        this.getContentPane().removeAll();
        
        // Clean up the previous view after removing it from display
        if (previousView != null) {
            previousView.cleanup();
        }
        
        // Check if the current view is not null
        if (this.currentView != null) {
            // Add the current view to the content pane
            this.add(this.currentView);
        }
        // Set the location relative to the null
        this.setLocationRelativeTo(null);
        // Set the view to visible
        this.setVisible(true);
    }

    /**
     * Here for testability reasons, lets us access what view is currently set. 
     * This is so we can do some basic sanity checks on the controller.
     * 
     * @return the current view
     */
    public View getView() {
        // Return the current view
        return this.currentView;
    }

    /**
     * Sets the title for the frame to newly given title value.
     * 
     * @Override - setTitle in class {@link Frame}
     * @param title - the title to be displayed in the frame's border. 
     * A null value is treated as an empty string, "".
     */
    public void setTitle(String title) {
        // Set the title of the frame
        super.setTitle(title);
    }

    /**
     * Returns the currently set title for the frame.
     * 
     * @Override - getTitle in class {@link Frame}
     * @return - the string being used for the frame title currently.
     */
    public String getTitle() {
        // Return the title of the frame
        return super.getTitle();
    }
}
