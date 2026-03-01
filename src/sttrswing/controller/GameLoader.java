package sttrswing.controller;

import sttrswing.model.Enterprise;
import sttrswing.model.Galaxy;
import sttrswing.model.Quadrant;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * GameLoader is responsible for well, loading the game state from a .trek file and 
 * building a new {@link Enterprise} and {@link Galaxy}.
 */
public class GameLoader {
    
    /** The file path to load from. */
    private String path;
    /** The lines read from the file. */
    private List<String> fileLines;
    /** Flag indicating whether the load was successful. */
    private boolean loadSuccessful;
    
    /**
     * Constructs a new GameLoader instance with a given filepath it will try to load 
     * the .trek file form.
     * 
     * @param path - filepath we want to load the .trek file from.
     */
    public GameLoader(String path) {
        // Set the path
        this.path = path;
    }

    /**
     * Attempt to load the file at the given path, track if it succeeds.
     */
    public void load() {
        // Use a try-catch block to handle the IOException
        try {
            // Read the file lines
            this.fileLines = Files.readAllLines(Paths.get(path));
            // Set the load flag for checking if the file was read successfully
            this.loadSuccessful = true;
        } catch (IOException e) {
            // Set the load flag for checking if the file was read successfully
            this.loadSuccessful = false;
            // Clear the file lines
            this.fileLines = new ArrayList<>();
        }
    }

    /**
     * Extract the line related to the enterprise.
     * @return first line of the data which should be the enterprise data
     * @hint sample line shape for the line related to the enterprise. 
     * [e] x:2 y:5 e:2500 s:500 t:10 |
     */
    public String enterpriseLine() {
        // Check if the file lines are null or empty
        if (fileLines == null || fileLines.isEmpty()) {
            return null;
        }
        // Return the first line of the file lines
        return fileLines.get(0);
    }

    /**
     * Extract each line of the file that relates to the galaxy into a {@link ArrayList} we return.
     * @return a list of galaxy relates lines from the file.
     * @hint example line shape for lines relevant to the galaxy: [q] x:4 y:4 s:201
     */
    public ArrayList<String> galaxyLines() {
        // Create a new empty array list to store the galaxy lines
        ArrayList<String> galaxyLines = new ArrayList<>();
        // Check if the file lines are null or empty or if the size is less than or equal to 1
        if (fileLines == null || fileLines.size() <= 1) {
            return galaxyLines;
        }
        
        // Skip the first line (enterprise line) and get all quadrant lines (starting from index 1)
        for (int i = 1; i < fileLines.size(); i++) {
            galaxyLines.add(fileLines.get(i));
        }
        // Return the galaxy lines
        return galaxyLines;
    }

    /**
     * Returns a string representation of the {@link GameLoader} internal state.
     * @override toString in class {@link Object}
     * @return a string representation of the {@link GameLoader} internal state.
     */
    public String toString() {
        // Return the string representation of the GameLoader internal state
        return "GameLoader{path='" + path + "', loadSuccessful=" + loadSuccessful + "}";
    }

    /**
     * Constructs a new {@link Galaxy}.
     * @return a new {@link Galaxy}.
     */
    public Galaxy buildGalaxy() {
        // Create a new empty array list to store the quadrants
        ArrayList<Quadrant> quadrants = new ArrayList<>();
        // Get the galaxy lines
        ArrayList<String> galaxyLines = galaxyLines();
        
        // Loop through the galaxy lines
        for (String line : galaxyLines) {
            // Try to parse the line
            try {
                // Parse the line for the coordinates
                int x = parseLineForX(line);
                int y = parseLineForY(line);
                
                // Validate that coordinates are within the valid galaxy range (0,0 to 7,7)
                if (x < 0 || x > 7 || y < 0 || y > 7) {
                    continue; // Skip invalid coordinates
                }
                
                // Parse the line for the quadrant symbol
                HashMap<String, Integer> counts = parseLineForQuadrantSymbol(line);
                // Get the number of stars, klingons, and starbases
                int stars = counts.get("stars");
                int klingons = counts.get("klingons");
                int starbases = counts.get("starbases");
                // Create a new quadrant with the coordinates, starbases, klingons, and stars
                Quadrant quadrant = new Quadrant(x, y, starbases, klingons, stars);
                // Add the quadrant to the quadrants list
                quadrants.add(quadrant);
            } catch (IOException e) {
                // Skip invalid lines by continuing the loop
                continue;
            }
        }
        // Create a new galaxy with the quadrants
        return new Galaxy(quadrants);
    }

    /**
     * Construct a new {@link Enterprise}.
     * @return the newly constructed {@link Enterprise}.
     */
    public Enterprise buildEnterprise() {
        // Get the enterprise line
        String enterpriseLine = enterpriseLine();
        // Check if the enterprise line is null
        if (enterpriseLine == null) {
            // Return a new enterprise with the default constructor
            return new Enterprise();
        }
        // Use a try-catch block to handle the IOException
        try {
            // Parse the enterprise line for the x coordinate
            int x = parseLineForX(enterpriseLine);
            // Parse the enterprise line for the y coordinate
            int y = parseLineForY(enterpriseLine);
            // Parse the enterprise line for the energy
            int energy = parseLineForEnergy(enterpriseLine);
            // Parse the enterprise line for the shields
            int shields = parseLineForShields(enterpriseLine);
            // Parse the enterprise line for the torpedoes
            int torpedoes = parseLineForTorpedoes(enterpriseLine);
            // Create a new enterprise with the coordinates, energy, shields, and torpedoes
            return new Enterprise(x, y, energy, shields, torpedoes);
        } catch (IOException e) {
            // Return a new enterprise with the default constructor to prevent 
            // null pointer exceptions
            return new Enterprise();
        }
    }

    /**
     * Extract an xCoordinate value from the given string. We assume the line has 
     * pattern like 'x:DIGITDIGIT '.
     * @param line - line we wish to parse
     * @return xCoordinate in this line or throws.
     * @throws IOException if the coordinate format is invalid
     */
    public int parseLineForX(String line) throws IOException {
        // Get the start and end index of the x coordinate
        int startIndex = line.indexOf("x:") + 2;
        int endIndex = line.indexOf(" ", startIndex);
        // Check if the start and end index are valid
        if (startIndex == 1 || endIndex == -1) {
            // Throw an exception if the start and end index are not valid
            throw new IOException("Invalid x coordinate format");
        }
        // Get the x coordinate string
        String xcoordStr = line.substring(startIndex, endIndex);
        // Return the x coordinate as an integer
        return Integer.parseInt(xcoordStr);
    }

    /**
     * Extract an yCoordinate value from the given string. We assume the line has 
     * pattern like 'y:DIGITDIGIT ', so we can use the whitespace on the right and 
     * the 'y:' on the left to identify the one or more digits we should be extracting.
     * @param line - line we wish to parse
     * @return yCoordinate in this line or throws.
     * @throws IOException if the coordinate format is invalid
     */
    public int parseLineForY(String line) throws IOException {
        // Get the start and end index of the y coordinate
        int startIndex = line.indexOf("y:") + 2;
        int endIndex = line.indexOf(" ", startIndex);
        // Check if the start and end index are valid
        if (startIndex == 1 || endIndex == -1) {
            // Throw an exception if the start and end index are not valid
            throw new IOException("Invalid y coordinate format");
        }
        // Get the y coordinate string
        String ycoordStr = line.substring(startIndex, endIndex);
        // Return the y coordinate as an integer
        return Integer.parseInt(ycoordStr);
    }

    /**
     * Extract a shield value from the given string. We assume the line has 
     * pattern like 's:DIGITDIGIT ', 
     * so we can use the whitespace on the right and the 's:' on the left to identify the one or 
     * more digits we should be extracting.
     * @param line - line we wish to parse
     * @return shield in this line or throws.
     * @throws IOException if the coordinate format is invalid
     */
    public int parseLineForShields(String line) throws IOException {
        // Get the start and end index of the shield
        int startIndex = line.indexOf("s:") + 2;
        int endIndex = line.indexOf(" ", startIndex);
        // Check if the start and end index are valid
        if (startIndex == 1 || endIndex == -1) {
            // Throw an exception if the start and end index are not valid
            throw new IOException("Invalid shield value format");
        }
        // Get the shield string
        String shieldStr = line.substring(startIndex, endIndex);
        // Return the shield as an integer
        return Integer.parseInt(shieldStr);
    }

    /**
     * Extract an energy value from the given string. We assume the line has 
     * pattern like 'e:DIGITDIGIT ', 
     * so we can use the whitespace on the right and the 'e:' on the left to identify the one or 
     * more digits we should be extracting.
     * 
     * @param line - line we wish to parse
     * @return energy in this line or throws.
     * @throws IOException if the coordinate format is invalid
     */
    public int parseLineForEnergy(String line) throws IOException {
        // Get the start and end index of the energy
        int startIndex = line.indexOf("e:") + 2;
        int endIndex = line.indexOf(" ", startIndex);
        // Check if the start and end index are valid
        if (startIndex == 1 || endIndex == -1) {
            // Throw an exception if the start and end index are not valid
            throw new IOException("Invalid energy value format");
        }
        // Get the energy string
        String energyStr = line.substring(startIndex, endIndex);
        // Return the energy as an integer
        return Integer.parseInt(energyStr);
    }

    /**
     * Extract a torpedo value from the given string. We assume the line has 
     * pattern like 't:DIGITDIGIT ', so we can use the whitespace on the right and 
     * the 't:' on the left to identify the one or more digits we should be extracting.
     * @param line - line we wish to parse
     * @return torpedo in this line or throws.
     * @throws IOException if the coordinate format is invalid
     */
    public int parseLineForTorpedoes(String line) throws IOException {
        // Get the start and end index of the torpedo
        int startIndex = line.indexOf("t:") + 2;
        int endIndex = line.indexOf(" ", startIndex);
        // Check if the start and end index are valid
        if (startIndex == 1 || endIndex == -1) {
            // Throw an exception if the start and end index are not valid
            throw new IOException("Invalid torpedo value format");
        }
        // Get the torpedo string
        String torpedoStr = line.substring(startIndex, endIndex);
        // Return the torpedo as an integer
        return Integer.parseInt(torpedoStr);
    }

    /**
     * Extract how many stars,klingons, and starbases each quadrant should have.
     * @param line - we wish to parse for information
     * @return a hashmap with a 'stars' , 'klingons' and 'starbases' key
     * @throws IOException if the coordinate format is invalid
     * @hint '[q] x:4 y:4 s:201' example expected line shape
     */
    public HashMap<String, Integer> parseLineForQuadrantSymbol(String line) throws IOException {
        // Get the start and end index of the quadrant symbol
        int startIndex = line.indexOf("s:") + 2;
        int endIndex = line.indexOf(" ", startIndex);
        // Check if the start and end index are valid
        if (startIndex == 1 || endIndex == -1) {
            // Throw an exception if the start and end index are not valid
            throw new IOException("Invalid quadrant symbol format");
        }
        // Get the quadrant symbol string
        String symbolStr = line.substring(startIndex, endIndex);
        // Check if the quadrant symbol string length is not 3
        if (symbolStr.length() != 3) {
            // Throw an exception if the quadrant symbol string length is not 3
            throw new IOException("Invalid symbol length - must be 3 digits");
        }
        // Create a new hashmap to store the stars, starbases, and klingons
        HashMap<String, Integer> counts = new HashMap<>();
        // Put the stars, starbases, and klingons into the hashmap
        counts.put("stars", Character.getNumericValue(symbolStr.charAt(0)));
        counts.put("starbases", Character.getNumericValue(symbolStr.charAt(1)));
        counts.put("klingons", Character.getNumericValue(symbolStr.charAt(2)));
        // Return the hashmap
        return counts;
    }

    /**
     * Returns if the GameLoader has been toggled to successful.
     * @return if the GameLoader has been toggled to successful.
     */
    public Boolean success() {
        // Return the load successful flag
        return loadSuccessful;
    }
}
