package sttrswing.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class responsible for saving the stringified game data to a .trek file.
 */
public class GameSaver {

    /** The game data to be saved. */
    private String gameData;
    /** The file path where the game will be saved. */
    private String path;
    /** Flag indicating whether the save operation was successful. */
    private boolean saveSuccessful = false;

    /**
     * Construct a new GameSaver instance with the data and target location we wish to save it to.
     * 
     * @param gameData - stringified representaiton of game state we wish to save
     * @param path - filepath for where we want to save the given data
     */
    public GameSaver(String gameData, String path) {
        // Set the game data and path
        this.gameData = gameData;
        this.path = path;
        // Initialize saveSuccessful to false
        this.saveSuccessful = false;
    }

    /**
     * Run the saving code, attempt to write to the file location with our stringified game data. 
     */
    public void save() {
        // Use a try-catch block to handle the IOException
        try {
            // Ensure the parent directory exists
            java.nio.file.Path filePath = Paths.get(path);
            java.nio.file.Path parentDir = filePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            
            // Write the game data to the file
            Files.write(filePath, gameData.getBytes());
            // Set the save successful flag to true
            this.saveSuccessful = true;
        } catch (IOException e) {
            // Set the save successful flag to false
            this.saveSuccessful = false;
        }
    }

    /**
     * Return if the GameSaver succeeded.
     * @return if the GameSaver succeeded.
     */
    public Boolean success() {
        // Return the save successful flag
        return this.saveSuccessful;
    }
}