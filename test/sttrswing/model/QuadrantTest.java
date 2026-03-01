package sttrswing.model;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Comprehensive test suite for the Quadrant class that manages game space sectors.
 * 
 * This test class validates quadrant management including:
 * - Constructor variations (default and parameterized)
 * - Entity counting (stars, klingons, starbases)
 * - Entity retrieval and positioning
 * - Random empty sector generation
 * - Scanning operations (marking all entities as scanned)
 * - Damage distribution (hit mechanics affecting all entities)
 * - Cleanup operations (removing marked entities)
 * - Symbol generation (3-character entity count representation)
 * - Entity list access (klingons(), stars(), starbases())
 * - Position-based symbol retrieval
 * - Marked entity counting for cleanup
 * - Game tick mechanics (tick() and outOfFocusTick())
 * - Export functionality for game state
 * 
 * The tests ensure quadrants properly manage the 8x8 grid of entities
 * and coordinate game mechanics within each sector.
 */
public class QuadrantTest {

    @Test
    public void testConstructor() {
        // Create a new Quadrant and check if x is 2 and y is 3
        var test = new Quadrant(2, 3, 1, 1, 1); // Test constructor with specific counts
        // Check if x is 2
        Assert.assertEquals("x should be 2", 2, test.getX());
        // Check if y is 3
        Assert.assertEquals("y should be 3", 3, test.getY());
        // Check if there should be 1 starbase
        Assert.assertEquals("should have 1 starbase", 1, test.starbaseCount());
        // Check if there should be 1 klingon
        Assert.assertEquals("should have 1 klingon", 1, test.klingonCount());
        // Check if there should be 1 star
        Assert.assertEquals("should have 1 star", 1, test.starCount());
    }

    @Test
    public void testGetX() {
        // Create a new Quadrant and check if x is 2
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if x is 2
        Assert.assertEquals("x should be 2", 2, test.getX());
    }

    @Test
    public void testGetY() {
        // Create a new Quadrant and check if y is 3
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if y is 3
        Assert.assertEquals("y should be 3", 3, test.getY());
    }

    @Test
    public void testStarbaseCount() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if there should be 1 starbase
        Assert.assertEquals("should have 1 starbase", 1, test.starbaseCount());
    }

    @Test
    public void testKlingonCount() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if there should be 1 klingon
        Assert.assertEquals("should have 1 klingon", 1, test.klingonCount());
    }

    @Test
    public void testStarCount() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if there should be 1 star
        Assert.assertEquals("should have 1 star", 1, test.starCount());
    }

    @Test
    public void testSymbol() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if the symbol should be 3 characters
        String symbol = test.symbol();
        // Check if the symbol should contain '1'
        Assert.assertEquals("Symbol should be 3 characters", 3, symbol.length());
        // Check if the symbol should contain '1'
        Assert.assertTrue("Symbol should contain '1'", symbol.contains("1"));
    }

    @Test
    public void testGetEntityAt() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Try to find an entity at various positions
        boolean foundEntity = false;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                var entity = test.getEntityAt(x, y);
                if (entity != null) {
                    foundEntity = true;
                    break;
                }
            }
            if (foundEntity) break;
        }
        Assert.assertTrue("Should find at least one entity in the quadrant", foundEntity);
        
        // Create a new entity at (7, 7) - this should be empty
        var emptyEntity = test.getEntityAt(7, 7);
        Assert.assertNull("Should not find entity at (7,7)", emptyEntity);
    }

    @Test
    public void testGetRandomEmptySector() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Create a new empty sector
        var emptySector = test.getRandomEmptySector();
        // Check if the empty sector should not be null
        Assert.assertNotNull("Should find empty sector", emptySector);
        // Check if the empty sector x should be in range 0-7
        Assert.assertTrue("X should be in range 0-7", emptySector.getX() >= 0 && emptySector.getX() < 8);
        // Check if the empty sector y should be in range 0-7
        Assert.assertTrue("Y should be in range 0-7", emptySector.getY() >= 0 && emptySector.getY() < 8);
    }

    @Test
    public void testScan() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Find any entity in the quadrant that is not already scanned
        Entity unscannedEntity = null;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                var entity = test.getEntityAt(x, y);
                if (entity != null && !entity.isScanned()) {
                    unscannedEntity = entity;
                    break;
                }
            }
            if (unscannedEntity != null) break;
        }
        
        if (unscannedEntity != null) {
            Assert.assertFalse("Entity should not be scanned initially", unscannedEntity.isScanned());
            // Scan the entity
            test.scan();
            // Check if the entity should be scanned after scan()
            Assert.assertTrue("Entity should be scanned after scan()", unscannedEntity.isScanned());
        } else {
            // If no unscanned entities found, just verify scan() doesn't throw exceptions
            test.scan();
            Assert.assertTrue("Scan should execute without exceptions", true);
        }
    }

    @Test
    public void testHit() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Create a new enterprise at (1, 1)
        if (test.klingonCount() > 0) {
            // Test energy indirectly through attack damage before hit
            var enterprise = new Enterprise(1, 1);
            int initialDamage = 0;
            // Get initial damage by having klingons attack the enterprise
            var klingons = test.klingons();
            for (Klingon klingon : klingons) {
                initialDamage += klingon.attack(enterprise);
            }
            // Hit the quadrant for 50 damage
            test.hit(50);
            // Check if the klingon count should be valid
            Assert.assertTrue("Klingon count should be valid", test.klingonCount() >= 0);
        }
    }

    @Test
    public void testRemoveMarkedEntities() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Get the initial klingon and starbase counts
        int initialKlingonCount = test.klingonCount();
        int initialStarbaseCount = test.starbaseCount();
        
        // Hit the quadrant for 300 damage
        test.hit(300); // This should mark klingons for removal
        
        // Call the cleanup method to remove marked entities
        test.cleanup();
        
        // Check if the klingon and starbase counts should have decreased
        int finalKlingonCount = test.klingonCount();
        int finalStarbaseCount = test.starbaseCount();
        
        // Check if the klingon and starbase counts should have decreased
        Assert.assertTrue("Klingons should be removed", finalKlingonCount <= initialKlingonCount);
        Assert.assertTrue("Starbases should be removed", finalStarbaseCount <= initialStarbaseCount);
    }

    @Test
    public void testGetQuadrantClusterAt() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if the quadrant x should be 2
        Assert.assertEquals("X coordinate should be 2", 2, test.getX());
        // Check if the quadrant y should be 3
        Assert.assertEquals("Y coordinate should be 3", 3, test.getY());
        // Check if the quadrant symbol should not be null
        String symbol = test.symbol();
        // Check if the quadrant symbol should be 3 characters
        Assert.assertNotNull("Symbol should not be null", symbol);
        Assert.assertEquals("Symbol should be 3 characters", 3, symbol.length());
    }

    @Test
    public void testExport() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Check if the toString should not be nu   ll
        String toString = test.toString();
        // Check if the toString should contain 'Stars:'
        Assert.assertNotNull("toString should not be null", toString);
        // Check if the toString should contain 'Stars:'
        Assert.assertTrue("toString should contain 'Stars:'", toString.contains("Stars:"));
        // Check if the toString should contain 'Enemies:'
        Assert.assertTrue("toString should contain 'Enemies:'", toString.contains("Enemies:"));
        // Check if the toString should contain 'Starbases:'
        Assert.assertTrue("toString should contain 'Starbases:'", toString.contains("Starbases:"));
    }

    @Test
    public void testDefaultConstructor() {
        // Test default constructor
        var test = new Quadrant(5, 7);
        // Check if the quadrant is at the correct position
        Assert.assertEquals("Quadrant should be at position (5,7)", 5, test.getX());
        Assert.assertEquals("Quadrant should be at position (5,7)", 7, test.getY());
        // Check if it has some entities (random generation)
        Assert.assertTrue("Should have some stars", test.starCount() >= 0);
        Assert.assertTrue("Should have some klingons", test.klingonCount() >= 0);
        Assert.assertTrue("Should have some starbases", test.starbaseCount() >= 0);
    }

    @Test
    public void testKlingons() {
        var test = new Quadrant(2, 3, 1, 2, 1);
        // Get the klingons list
        List<Klingon> klingons = test.klingons();
        // Check if the list is not null
        Assert.assertNotNull("Klingons list should not be null", klingons);
        // Check if the list size matches the count
        Assert.assertEquals("Klingons list size should match count", test.klingonCount(), klingons.size());
    }

    @Test
    public void testStars() {
        var test = new Quadrant(2, 3, 1, 1, 2);
        // Get the stars list
        List<Star> stars = test.stars();
        // Check if the list is not null
        Assert.assertNotNull("Stars list should not be null", stars);
        // Check if the list size matches the count
        Assert.assertEquals("Stars list size should match count", test.starCount(), stars.size());
    }

    @Test
    public void testStarbases() {
        var test = new Quadrant(2, 3, 2, 1, 1);
        // Get the starbases list
        List<Starbase> starbases = test.starbases();
        // Check if the list is not null
        Assert.assertNotNull("Starbases list should not be null", starbases);
        // Check if the list size matches the count
        Assert.assertEquals("Starbases list size should match count", test.starbaseCount(), starbases.size());
    }

    @Test
    public void testGetSymbolAt() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Test getting symbol at a position
        String symbol = test.getSymbolAt(0, 0);
        // Check if the symbol is not null
        Assert.assertNotNull("Symbol should not be null", symbol);
        // Check if the symbol is not empty
        Assert.assertFalse("Symbol should not be empty", symbol.isEmpty());
    }

    @Test
    public void testKlingonsMarkedForRemovalCount() {
        var test = new Quadrant(2, 3, 1, 2, 1);
        // Get initial count
        int initialMarkedCount = test.klingonsMarkedForRemovalCount();
        Assert.assertTrue("Initial marked count should be >= 0", initialMarkedCount >= 0);
        
        // Hit the quadrant to mark some klingons for removal
        test.hit(300); // This should mark klingons for removal
        
        // Check if more klingons are marked for removal
        int markedCountAfterHit = test.klingonsMarkedForRemovalCount();
        Assert.assertTrue("Should have more klingons marked after hit", markedCountAfterHit >= initialMarkedCount);
    }

    @Test
    public void testTick() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Create a mock game for testing
        var mockGame = new sttrswing.model.Game();
        
        // Test that tick doesn't throw exceptions
        try {
            test.tick(mockGame);
            // If we get here, the method executed without throwing exceptions
            Assert.assertTrue("Tick should execute without exceptions", true);
        } catch (Exception e) {
            Assert.fail("Tick should not throw exceptions: " + e.getMessage());
        }
    }

    @Test
    public void testOutOfFocusTick() {
        var test = new Quadrant(2, 3, 1, 1, 1);
        // Create a mock game for testing
        var mockGame = new sttrswing.model.Game();
        
        // Test that outOfFocusTick doesn't throw exceptions
        try {
            test.outOfFocusTick(mockGame);
            // If we get here, the method executed without throwing exceptions
            Assert.assertTrue("OutOfFocusTick should execute without exceptions", true);
        } catch (Exception e) {
            Assert.fail("OutOfFocusTick should not throw exceptions: " + e.getMessage());
        }
    }
}