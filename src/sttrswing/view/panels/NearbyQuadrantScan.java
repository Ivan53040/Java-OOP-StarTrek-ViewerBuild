package sttrswing.view.panels;

import sttrswing.model.interfaces.GameModel;
import sttrswing.view.guicomponents.MapSquare;
import sttrswing.view.View;
import sttrswing.view.Pallete;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * A view responsible for showing you the symbols of the surrounding quadrants. 
 * (Quadrant make sure to check what a quadrants .symbol() represents)
 */
public class NearbyQuadrantScan extends View {
    
    /** The game state we use to construct this view. */
    private final GameModel game;

    /**
     * Construct a new {@link NearbyQuadrantScan} instance.
     * 
     * @param game - game state we use to construct this view for both information and 
     * method calls for relevant action listeners if any.
     */
    public NearbyQuadrantScan(GameModel game) {

        // Set the title and the game state
        super("Long Range Scan Results");
        this.game = game;
        
        //Build the layout
        this.setLayout(new BorderLayout());

        //---------top----------------//
        // Create the title label and style it
        JLabel titleLabel = new JLabel("Long Range Scan Results", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Pallete.WHITE.color());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        //---------center----------------//
        // Create a new JPanel with a GridLayout of 3x3 and style it
        JPanel mapPanel = new JPanel(new GridLayout(3, 3, 4, 4));
        mapPanel.setBackground(Pallete.BLACK.color());
        mapPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Get the surrounding quadrant data
        HashMap<String, String> surroundingQuadrants = this.game.getSurroundingQuadrants();
        // Create an array of positions and create the map with the surrounding quadrants
        String[] positions = {"topLeft", "top", "topRight", "left", "current", 
            "right", "bottomLeft", "bottom", "bottomRight"};
        for (String position : positions) {
            String data;
            if (position.equals("current")) {
                data = "Current Quadrant";
            } else {
                data = (surroundingQuadrants.get(position) != null)
                    ? surroundingQuadrants.get(position) : "---";
            }
            mapPanel.add(this.buildMapSquare(data));
        }
        // Add the map panel to the center of the layout
        this.add(mapPanel, BorderLayout.CENTER);

        //---------bottom----------------//
        //Create the entities panel and add it to the bottom of the layout
        JPanel entitiesPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        entitiesPanel.setBackground(Pallete.BLACK.color());
        entitiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //Create the entities title and style it
        JLabel entitiesTitle = new JLabel("Entities: [Stars][Starbases][Klingons]", 
            JLabel.CENTER);
        entitiesTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        entitiesTitle.setForeground(Pallete.WHITEMID.color());
        //Create the entities example label and style it
        JLabel entitiesExample = new JLabel("Example: 123 = 1 Star, 2 Starbases, 3 Klingons", 
            JLabel.CENTER);
        entitiesExample.setFont(new Font("Arial", Font.PLAIN, 12));
        entitiesExample.setForeground(Pallete.WHITEMID.color());
        //Add the entities title and example to the entities panel
        entitiesPanel.add(entitiesTitle);
        entitiesPanel.add(entitiesExample);
        // Add the entities panel to the bottom of the layout
        this.add(entitiesPanel, BorderLayout.SOUTH);
    }

    /**
     * Constructs a new {@link MapSquare} with the given label or a blank string 
     * if the label is null.
     * 
     * @param label - label we wish to display on this {@link MapSquare}.
     * @return a new {@link MapSquare} with the given label or a blank string 
     * if the label is null.
     */
    public MapSquare buildMapSquare(String label) {
        // Create a new MapSquare with the given label and color
        MapSquare square = new MapSquare(label, Pallete.GREENTERMINAL.color());
        // Set the preferred size of the square to 60x60 pixels
        square.setPreferredSize(new Dimension(60, 60));
        // Return the square
        return square;
    }
}
