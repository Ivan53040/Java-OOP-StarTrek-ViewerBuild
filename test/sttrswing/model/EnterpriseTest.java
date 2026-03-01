package sttrswing.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Comprehensive test suite for the Enterprise class in the Star Trek game model.
 * 
 * This test class validates all aspects of the Enterprise entity including:
 * - Constructor variations (default, position-based, full parameter)
 * - Energy management (drain, gain, transfer to shields)
 * - Shield operations (hit damage, transfer from energy)
 * - Torpedo system (ammo count, firing mechanics, exhaustion)
 * - Life cycle management (alive status, destruction)
 * - Symbol display (scanned vs unscanned states)
 * - Export functionality for game state persistence
 * - Docking mechanics with starbases
 * - Healing capabilities
 * - Faction identification (FEDERATION)
 * 
 * The tests ensure the Enterprise behaves correctly as the player's main ship,
 * with proper resource management and combat mechanics.
 */
public class EnterpriseTest {

    @Test
    public void testHasTorpedoAmmo() {
        // Create a new Enterprise and check if it has torpedoes on construction
        var test = new Enterprise();
        // Check if the Enterprise has Torpedoes on construction
        Assert.assertTrue("Enterprise should have Torpedoes on construction", test.hasTorpedoAmmo());
    }

    @Test
    public void testTorpedoAmmoCounts() {
        // Create a new Enterprise and check if it has 10 Torpedoes on construction
        var test = new Enterprise();
        // Check if the Enterprise has 10 Torpedoes on construction
        Assert.assertEquals("Enterprise should have 10 Torpedoes on construction", 10, test.torpedoAmmo());
        // Fire a torpedo and check if the Enterprise has 9 Torpedoes left
        test.fireTorpedo();
        // Check if the Enterprise has 9 Torpedoes left
        Assert.assertEquals("Enterprise should have 9 Torpedoes left", 9, test.torpedoAmmo());
    }

    @Test
    public void testShields() {
        // Create a new Enterprise and check if it starts with 500 energy pre-allocated to shields
        var test = new Enterprise();
        // Check if the Enterprise starts with 500 energy pre-allocated to shields
        Assert.assertEquals("Enterprise should start with 500 energy pre-allocated to shields", 500, test.shields());
    }

    @Test
    public void testEnergy() {
        // Create a new Enterprise and check if it starts with 2500 energy
        var test = new Enterprise();
        // Check if the Enterprise starts with 2500 energy
        Assert.assertEquals("Enterprise should start with 2500 energy", 2500, test.energy());
    }

    @Test
    public void testDrainEnergy() {
        // Create a new Enterprise and drain 100 energy
        var test = new Enterprise();
        // Get the initial energy
        int initialEnergy = test.energy();
        test.drainEnergy(100);
        // Get the adjusted energy
        int adjustedEnergy = test.energy();
        // Check if the starting energy should have been drained by 100
        Assert.assertEquals("Starting energy should have been drained by 100", initialEnergy - 100, adjustedEnergy);
    }

    @Test
    public void testTransferEnergyToShields() {
        // Create a new Enterprise and reduce shields to test transfer
        var test = new Enterprise();
        // Reduce shields to test transfer
        test.hit(300);
        // Get the initial energy
        int initialEnergy = test.energy();
        // Get the initial shields
        int initialShields = test.shields();
        test.transferEnergyToShields(300);
        // Get the adjusted energy
        int adjustedEnergy = test.energy();
        // Get the adjusted shields
        int adjustedShields = test.shields();
        Assert.assertEquals("300 energy should have been drained", initialEnergy - 300, adjustedEnergy);
        Assert.assertEquals("300 shields should have been gained", initialShields + 300, adjustedShields);
    }

    @Test
    public void testHit() {
        var test = new Enterprise();
        // Get the initial shields
        int initialShields = test.shields();
        // Hit the Enterprise for 100 damage
        test.hit(100);
        // Get the adjusted shields
        int adjustedShields = test.shields();
        // Check if the Shields should have been drained by the 100 damage dealt
        Assert.assertEquals("Shields should have been drained by the 100 damage dealt", initialShields - 100, adjustedShields);
    }

    @Test
    public void testIsAliveAfterHit() {
        var test = new Enterprise();
        // Check if the Enterprise is alive on construction
        Assert.assertTrue("Enterprise should be alive on construction", test.isAlive());
        // Hit the Enterprise for 999999 damage
        test.hit(999999);
        // Check if the Enterprise should be destroyed but isAlive!
        Assert.assertFalse("Enterprise should be destroyed but isAlive!", test.isAlive());
    }

    @Test
    public void testFireTorpedoNoAmmo() {
        var test = new Enterprise();
        // Create a new Enterprise and fire all torpedoes
        for (int i = 0; i < 10; i++) {
            test.fireTorpedo();
        }
        // Check if the Enterprise should return null when no ammo
        Assert.assertNull("Should return null when no ammo", test.fireTorpedo());
    }

    @Test
    public void testGainEnergy() {
        var test = new Enterprise();
        // Get the initial energy
        int initialEnergy = test.energy();
        // Gain 100 energy
        test.gainEnergy(100);
        // Get the adjusted energy
        int adjustedEnergy = test.energy();
        // Check if the Energy should increase by 100
        Assert.assertEquals("Energy should increase by 100", initialEnergy + 100, test.energy());
    }

    @Test
    public void testFaction() {
        var test = new Enterprise();
        // Check if the Enterprise should be FEDERATION faction
        Assert.assertEquals("Enterprise should be FEDERATION faction", sttrswing.model.enums.Faction.FEDERATION, test.faction());
    }

    @Test
    public void testDefaultConstructor() {
        // Test default constructor
        var test = new Enterprise();
        // Check if the Enterprise has default values
        Assert.assertEquals("Enterprise should start with 2500 energy", 2500, test.energy());
        Assert.assertEquals("Enterprise should start with 500 shields", 500, test.shields());
        Assert.assertEquals("Enterprise should start with 10 torpedoes", 10, test.torpedoAmmo());
        Assert.assertEquals("Enterprise should be at position (0,0)", 0, test.getX());
        Assert.assertEquals("Enterprise should be at position (0,0)", 0, test.getY());
    }

    @Test
    public void testConstructorWithPosition() {
        // Test constructor with position
        var test = new Enterprise(5, 7);
        // Check if the Enterprise is at the correct position
        Assert.assertEquals("Enterprise should be at position (5,7)", 5, test.getX());
        Assert.assertEquals("Enterprise should be at position (5,7)", 7, test.getY());
        // Check if it has default energy/shields/torpedoes
        Assert.assertEquals("Enterprise should start with 2500 energy", 2500, test.energy());
        Assert.assertEquals("Enterprise should start with 500 shields", 500, test.shields());
        Assert.assertEquals("Enterprise should start with 10 torpedoes", 10, test.torpedoAmmo());
    }

    @Test
    public void testConstructorWithAllParameters() {
        // Test constructor with all parameters
        var test = new Enterprise(3, 4, 2000, 400, 8);
        // Check if the Enterprise has the specified values
        Assert.assertEquals("Enterprise should be at position (3,4)", 3, test.getX());
        Assert.assertEquals("Enterprise should be at position (3,4)", 4, test.getY());
        Assert.assertEquals("Enterprise should have 2000 energy", 2000, test.energy());
        Assert.assertEquals("Enterprise should have 400 shields", 400, test.shields());
        Assert.assertEquals("Enterprise should have 8 torpedoes", 8, test.torpedoAmmo());
    }

    @Test
    public void testSymbol() {
        var test = new Enterprise();
        // Check if the symbol should show complex symbol when not scanned (Enterprise shows Ë in center when not scanned)
        Assert.assertEquals("Should show complex symbol when not scanned", "{Ë}", test.symbol());
        
        // Scan the Enterprise
        test.scan();
        // Check if the symbol should show complex symbol when scanned (Enterprise has custom symbol logic, stays same after scan)
        Assert.assertEquals("Should show complex symbol when scanned", "{Ë}", test.symbol());
    }

    @Test
    public void testExport() {
        var test = new Enterprise(2, 3, 2000, 400, 8);
        // Get the export string
        String export = test.export();
        // Check if the export string contains expected values
        Assert.assertTrue("Export should contain x coordinate", export.contains("x:2"));
        Assert.assertTrue("Export should contain y coordinate", export.contains("y:3"));
        Assert.assertTrue("Export should contain energy", export.contains("e:2000"));
        Assert.assertTrue("Export should contain shields", export.contains("s:400"));
        Assert.assertTrue("Export should contain torpedoes", export.contains("t:8"));
    }

    @Test
    public void testDocked() {
        var test = new Enterprise(2, 2);
        // Create a list of starbases
        var starbases = new java.util.ArrayList<Starbase>();
        
        // Test when not docked (no starbases)
        Assert.assertFalse("Should not be docked with no starbases", test.docked(starbases));
        
        // Add a starbase at the same position
        starbases.add(new Starbase(2, 2));
        Assert.assertTrue("Should be docked with starbase at same position", test.docked(starbases));
        
        // Add a starbase at different position
        starbases.clear();
        starbases.add(new Starbase(5, 5));
        Assert.assertFalse("Should not be docked with starbase at different position", test.docked(starbases));
    }

    @Test
    public void testHeal() {
        var test = new Enterprise();
        // Hit the Enterprise to reduce shields
        test.hit(200);
        int shieldsAfterHit = test.shields();
        
        // Heal the Enterprise
        test.heal(150);
        
        // Check if shields increased
        Assert.assertTrue("Shields should increase after healing", test.shields() > shieldsAfterHit);
        Assert.assertEquals("Shields should increase by 150", shieldsAfterHit + 150, test.shields());
    }
}