package sttrswing.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A "view" is an extended JPanel that displays some information, 
 * presents some relevant ways to interact with it, 
 * it also handles tracking all the {@link JButton} assigned to it, 
 * their listeners and provides a way to clear them from memory when you wish to 
 * deconstruct the View.
 */

public class View extends JPanel {

    /** List of action listeners for cleanup. */
    private ArrayList<ActionListener> listeners = new ArrayList<>();
    /** List of buttons for cleanup. */
    private ArrayList<JButton> buttons = new ArrayList<>();
    /** The label component. */
    private JLabel label;
    /** The title of this view. */
    private String title;

    /**
     * Constructs a new {@link View} with the given title, and a black 
     * (from {@link Pallete}) background.
     * 
     * @param title - title we wish to assign to the given {@link View}.
     */
    public View(final String title) {
        // Set the title and the background color
        this.setTitle(title);
        this.setBackground(Pallete.BLACK.color());
    }

    /**
     * Public for testability reasons {@link JLabel}, returns the last label 
     * added to this {@link View}.
     * 
     * @return the last label added to this {@link View}.
     */
    public JLabel getLabel() {
        // Return the label
        return this.label;
    }

    /**
     * Takes a given {@link JLabel} and adjusts its font size, colour etc. 
     * To match the settings for this screen;
     * 
     * @param label - label to modify and then add to this screen
     */
    public void addLabel(JLabel label) {
        // Set the label
        this.label = label;
    }

    /**
     * Returns the title of this View.
     * 
     * @return the title of this View.
     */
    public String getTitle() {
        // Return the title
        return this.title;
    }

    /**
     * Sets the title of this View.
     * 
     * @param title - title to set for this View.
     */
    public void setTitle(String title) {
        // Set the title
        this.title = title;
    }

    /**
     * Track the given {@link ActionListener}, intended for use with tracked 
     * buttons to clear events when we call cleanup.
     * 
     * @param action - {@link ActionListener} we wish to track.
     */
    public void trackListener(ActionListener action) {
        // Add the action listener to the list of listeners for cleanup
        this.listeners.add(action);
    }

    /**
     * Track the given {@link JButton}, intended for use with tracked 
     * {@link ActionListener} to clear events from these {@link JButton} when we 
     * call cleanup.
     * 
     * @param button - the {@link JButton} we wish to track.
     */
    public void trackButton(JButton button) {
        // Add the button to the list of buttons for cleanup
        this.buttons.add(button);
    }

    /**
     * Used for testing purposes, and to help with tracking listeners to remove 
     * when removing a screen.
     * 
     * @return {@link ArrayList} of {@link ActionListeners}, intended to be used 
     * for testing when removing a screen to clear out listeners, so they don't 
     * persist in memory.
     */
    public ArrayList<ActionListener> getListeners() {
        // Return the list of listeners
        return this.listeners;
    }

    /**
     * Used for testing purposes, so we can check the correct number of 
     * {@link JButton} are created and labels for said buttons are set.
     * 
     * @return list of {@link JButton} created for this {@link View}.
     */
    public ArrayList<JButton> getButtons() {
        // Return the list of buttons
        return this.buttons;
    }

    /**
     * Takes a given String Label and action listener, 
     * creates a {@link JButton} using both that also tracks the button and 
     * the {@link ActionListener} for when this {@link View} needs to be cleaned up.
     * 
     * @param label - label text we want on the {@link JButton} and tracked for 
     * this screen.
     * @param listener - {@link ActionListener} we want to be attached to the 
     * button and tracked for this screen.
     * @return the newly created {@link JButton}.
     */
    public JButton buildButton(String label, ActionListener listener) {
        // Create a new JButton with the given label
        JButton button = new JButton(label);
        // Add the action listener to the button
        button.addActionListener(listener);
        // Add the button to the list of buttons for cleanup
        this.trackButton(button);
        // Add the action listener to the list of listeners for cleanup
        this.trackListener(listener);
        // Return the button
        return button;
    }

    /**
     * Handles cleaning out {@link ActionListener}s. So they don't persist in 
     * memory after this screen being removed.   
     */
    public void cleanup() {
        // Clear the list of listeners
        this.listeners.clear();
        // Clear the list of buttons
        this.buttons.clear();
    }
}   