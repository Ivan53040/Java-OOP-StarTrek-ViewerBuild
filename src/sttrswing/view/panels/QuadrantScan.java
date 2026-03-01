package sttrswing.view.panels;

import sttrswing.view.View;
import sttrswing.model.interfaces.GameModel;
import sttrswing.model.interfaces.HasSymbol;
import sttrswing.model.interfaces.HasFaction;
import sttrswing.model.interfaces.HasPosition;
import sttrswing.view.guicomponents.MapSquare;
import sttrswing.view.Pallete;
import java.awt.GridLayout;

/**
 * A view representing the internal state of the quadrant the player is in 
 * as a grid of squares, each indicating either empty, Klingon, Starbase, 
 * Star, Enterprise or unknown (i.e. not yet scanned).
 */
public class QuadrantScan extends View {


    /**
     * Construct a new {@link QuadrantScan}.
     *  
     * @param game - game state we need access to for the symbol for the 
     * current {@link Quadrant} in the gamestate.
     */
    public QuadrantScan(GameModel game) {
        //Set the title to Quadrant Scan
        super("Quadrant Scan");

        // Set up the 8x8 grid layout using a GridLayout of 8 rows, 8 columns,
        // 2px horizontal and vertical gaps
        this.setLayout(new GridLayout(8, 8, 2, 2));
        
        // Fill with empty squares first
        for (int i = 0; i < 64; i++) {
            this.add(this.buildEmptyMapSquare());
        }
        
        // Get the symbols for the current quadrant
        java.util.ArrayList<?> items = game.getSymbolsForQuadrant();
        // For each object in the items
        for (Object obj : items) {
            // Get the position of the object
            HasPosition pos = (HasPosition) obj;
            // Get the x and y coordinates of the object
            int x = pos.getX();
            int y = pos.getY();
            // Get the index of the object in the grid
            int idx = (y * 8) + x;
            // Remove the object from the grid
            this.remove(idx);
            // Replace the empty map square with the object's map square
            this.add(this.buildMapSquare((HasSymbol & HasFaction) obj), idx);
        }
    }
    

    /**
     * Public for Testability reasons, constructs a {@link MapSquare} with 
     * no symbol and default coloring and adds it this view.
     * 
     * @return the empty map square
     */
    public MapSquare buildEmptyMapSquare() {
        // Use MapSquare's simple constructor for empties (green border/foreground, black bg)
        MapSquare square = new MapSquare(null);
        return square;
    }

    /**
     * Public for Testability reasons, constructs a {@link MapSquare} displays 
     * the data entries .symbol() that is color coded based on the Faction of 
     * the given data entry.
     * 
     * @param <T> - Requires access to a .symbol() method and a .faction() method
     * @param data - Information we need for the visuals of this map square
     * @return the map square
     */
    public <T extends HasSymbol & HasFaction> MapSquare buildMapSquare(T data) {
        // Create a new map square with the symbol and appropriate foreground color from the Pallete
        MapSquare square = new MapSquare(data.symbol(), Pallete.GREENTERMINAL.color());
        return square;
    }
}
