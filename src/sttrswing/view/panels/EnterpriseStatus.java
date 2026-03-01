package sttrswing.view.panels;

import sttrswing.model.interfaces.GameModel;

import sttrswing.view.View;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

/**
 * Takes the game state and represents the current state of the enterprise on the screen. 
 * Intended to be used as a side informative panel.
 */
public class EnterpriseStatus extends View {

    /** The game state we use to construct this view. */
    private final GameModel game;
    /** The table component for displaying enterprise status. */
    private final JTable jtTable;

    /**
     * Constructs a new {@link EnterpriseStatus} view.
     * 
     * @param game - game state we use to construct this view for both 
     * information and method calls for relevant action listeners if any.
     */
    public EnterpriseStatus(GameModel game) {

        // Set the title and the game state
        super("Enterprise Status");
        this.game = game;
        
        //Build the layout and add the JTable to the center
        this.setLayout(new BorderLayout(8, 8));
        
        // Create a new DefaultTableModel with the headers "Stat" and "Value"
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Stat", "Value"}, 0);
        // Create a new JTable with the model
        this.jtTable = new JTable(model);
        this.jtTable.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        this.add(new JScrollPane(this.jtTable), BorderLayout.CENTER);
        // Populate the JTable with the basic data to display the enterprise status
        try {
            model.addRow(new Object[]{"Quadrant (X,Y)", 
                this.game.galaxyPosition().getX() + "," + this.game.galaxyPosition().getY()});
            model.addRow(new Object[]{"Sector (X,Y)", 
                this.game.playerPosition().getX() + "," + this.game.playerPosition().getY()});
            model.addRow(new Object[]{"Torpedoes", this.game.spareTorpedoes()});
            model.addRow(new Object[]{"Energy", this.game.playerEnergy()});
            model.addRow(new Object[]{"Shields", this.game.playerShields()});
            model.addRow(new Object[]{"Klingons left in Galaxy", this.game.totalKlingonCount()});
            model.addRow(new Object[]{"Starbases left in Galaxy", this.game.totalStarbaseCount()});
        } catch (Exception e) {
            // Ignore the exception
        }
    }
    

    /**
     * Public for Testability reasons, lets us access the {@link JTable}, 
     * so we ensure it has instantiated as expected.
     * 
     * @return the {@link JTable}
     */
    public JTable getTable() {
        // Return the JTable
        return this.jtTable;
    } 
    
}
