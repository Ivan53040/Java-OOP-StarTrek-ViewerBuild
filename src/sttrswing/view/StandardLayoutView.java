package sttrswing.view;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel; 

/**
 * A standardised screen layout used for holding our various {@link View}s, 
 * to hold 4 injected panels + a universal results panel above them that 
 * documents the results of the last game action.
 */
public class StandardLayoutView extends View {
    // Declare the list of view panels and the grid
    /** The list of view panels. */
    private final ArrayList<View> viewPanels = new ArrayList<>();
    /** The grid panel. */
    private final JPanel grid;
    
    /**
     * Creates an empty StandardLayoutView with the layout preconfigured, 
     * ready to receive up to 4 Views.
     * 
     * @param title - title to display on the top of the panel.
     */
    public StandardLayoutView(final String title) {
        // Set the title and the layout
        super(title);
        this.setLayout(new BorderLayout(8, 8));

        //---------top----------------//
        // Add the title to the top of the panel
        this.add(new JLabel(title), BorderLayout.NORTH);

        //---------center----------------//
        // Create a 2x2 grid for 4 view panels using a GridLayout of 2 rows,
        // 2 columns, 8px horizontal and vertical gaps
        this.grid = new JPanel(new GridLayout(2, 2, 8, 8));
        // Add the grid to the center of the layout
        this.add(this.grid, BorderLayout.CENTER);
    }

    /**
     * Adds a {@link View} to the layout of this {@link StandardLayoutView} 
     * up to a maximum of 4.
     * 
     * @param view - {@link View} to add as a panel, a {@link StandardLayoutView} 
     * can have up to 4 panels added
     * @return a reference to this instance of {@link StandardLayoutView} so you 
     * can chain these method calls together i.e. 
     * example.addScreenPanel(coolScreen).addScreenPanel(otherCoolScreen);
     * @throws RuntimeException - if a {@link View} beyond the max panel count is added.
     */
    public StandardLayoutView addViewPanel(final View view) {
        // Check if the maximum number of panels is exceeded
        if (this.viewPanels.size() >= 4) {
            // Throw a runtime exception if the maximum number of panels is exceeded
            throw new RuntimeException("Too many panels added to StandardLayoutView (max 4)");
        }
        // Add the view to the list of view panels for cleanup
        this.viewPanels.add(view);
        // Add the view to the grid
        this.grid.add(view);
        // Revalidate and repaint the layout
        this.revalidate();
        this.repaint();
        // Return this instance of StandardLayoutView for chaining
        return this;
    }

    /**
     * Here purely for testability reasons.
     * 
     * @return a list of all the {@link View} that were added to this {@link StandardLayoutView}.
     */
    public ArrayList<View> getViewPanels() {
        // Return the list of view panels
        return this.viewPanels;
    }

}