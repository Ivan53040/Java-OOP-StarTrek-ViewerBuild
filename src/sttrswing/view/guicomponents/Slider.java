package sttrswing.view.guicomponents;

import javax.swing.*;
import java.util.Hashtable;


/**
 * A JSlider that takes a given range of energy choices to represent. 
 * Major Tick Spacing is set to be equal to the given MAX energy/5 or 150, whichever is smaller. 
 * Ticks and Labels are rendered. Orientation is Vertical by default.
 */
public class Slider extends JSlider {

    /**
     * Constructs a new Slider.
     * 
     * @param max - maximum selectable value on our Slider
     * @param orientation - Orientation we wish to use, see the JSlider constants 
     * for relevant values. i.e new Slider(2,JSlider.VERTICAL);
     */
    public Slider(int max, int orientation) {
        //set the orientation of the slider
        super(orientation);
        this.setMinimum(1);
        this.setMaximum(max);
        this.setValue(1); // Default value is 1
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        
        // Handle 2 slider types based on max value
        // 1. Slider for navigation (1-8)
        if (max == 8) {
            // Set major tick spacing to 1 for clear 1-9 labels
            this.setMajorTickSpacing(1);
            // Custom labels for 1-9 range, add labels for 1-9
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            for (int i = 1; i <= 9; i++) {
                labelTable.put(i, new JLabel(String.valueOf(i)));
            }
            this.setLabelTable(labelTable);
        } else {
            // 2. Slider for energy (1-2401)
            this.setMajorTickSpacing(150);
            // Custom labels to show 1, 151, 301, 451... instead of 0, 150, 300, 450...
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            for (int i = 1; i <= max; i += this.getMajorTickSpacing()) {
                labelTable.put(i, new JLabel(String.valueOf(i)));
            }
            this.setLabelTable(labelTable);
        }
    }

    /**
     * Constructs a new Slider.
     * 
     * @param max - maximum selectable value on our Slider
     */
    public Slider(int max) {
        // Call the original constructor with the max value and vertical orientation(default)
        this(max, JSlider.VERTICAL);
    }
    
}

