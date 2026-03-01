package sttrswing.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite for the Klingon enemy class in the Star Trek game model.
 * 
 * This test class validates Klingon-specific behavior including:
 * - Constructor and initial state (position, energy, faction)
 * - Attack mechanics (damage calculation, energy consumption)
 * - Damage handling (energy reduction, death conditions)
 * - Symbol display variations based on energy levels:
 *   * High energy: "+K+" (scanned) / " ? " (unscanned)
 *   * Half energy: "+k+" (scanned) / " ? " (unscanned)  
 *   * Low energy: "-k-" (scanned) / " ? " (unscanned)
 * - Faction changes (NEUTRAL → KLINGON after scanning)
 * - Scanning mechanics and state transitions
 * 
 * The tests ensure Klingons provide appropriate challenge as enemy units
 * with dynamic threat levels based on their energy state.
 */
public class KlingonTest {

    @Test
    public void testConstructor() {
        // Create a new Klingon and check if x is 2 and y is 3
        var test = new Klingon(2, 3);
        // Check if x is 2
        Assert.assertEquals("x should be 2", 2, test.getX());
        // Check if y is 3
        Assert.assertEquals("y should be 3", 3, test.getY());
        // Create a new Enterprise and check if it has 1, 1 coordinates
        // Test energy indirectly through attack damage (should be 100 for 300 energy)
        var target = new Enterprise(1, 1);
        // Attack the Enterprise and check if the damage is 100
        int damage = test.attack(target);
        // Check if the damage is 100
        Assert.assertEquals("energy should be 300 (damage = energy/3 = 100)", 100, damage);
        // Check if the faction is NEUTRAL
        Assert.assertEquals("faction should be NEUTRAL", sttrswing.model.enums.Faction.NEUTRAL, test.faction());
    }

    @Test
    public void testAttack() {
        // Create a new Klingon and check if it has 2, 3 coordinates
        var klingon = new Klingon(2, 3);
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var target = new Enterprise(1, 1);
        // Get the initial shields of the Enterprise
        int initialShields = target.shields();
        // Attack the Enterprise and check if the damage is 100
        int damage = klingon.attack(target);
        // Check if the damage is 100
        Assert.assertEquals("Damage should be 100 (300/3)", 100, damage);
        // Check if the Target shields should be reduced by 100
        Assert.assertEquals("Target shields should be reduced by 100", initialShields - 100, target.shields());
        // Test that energy is not depleted by attacking again
        int damage2 = klingon.attack(target);
        // Check if the Klingon energy should not be depleted (damage should still be 100)
        Assert.assertEquals("Klingon energy should not be depleted (damage should still be 100)", 100, damage2);
    }

    @Test
    public void testHit() {
        // Create a new Klingon and check if it has 2, 3 coordinates
        var test = new Klingon(2, 3);
        // Test energy indirectly through attack damage before and after hit
        var target = new Enterprise(1, 1);
        // Get the initial damage of the Enterprise
        int initialDamage = test.attack(target);
        // Hit the Klingon for 50 damage
        test.hit(50);
        // Get the damage after the hit
        int damageAfterHit = test.attack(target);
        // Check if the Energy should be reduced (damage should be less)
        Assert.assertTrue("Energy should be reduced (damage should be less)", damageAfterHit < initialDamage);
        // Check if the Klingon should not be marked for removal
        Assert.assertFalse("Should not be marked for removal", test.isMarkedForRemoval());
        
        // Check if the Energy should be reduced (damage should be less)
        Assert.assertTrue("Energy should be reduced (damage should be less)", damageAfterHit < initialDamage);
        Assert.assertFalse("Should not be marked for removal", test.isMarkedForRemoval());
    }

    @Test
    public void testHitToDeath() {
        var test = new Klingon(2, 3);
        // Hit the Klingon for 300 damage
        test.hit(300);
        // Test that energy is 0 by checking attack damage (should be 0)
        var target = new Enterprise(1, 1);
        // Attack the Enterprise and check if the damage is 0
        int damage = test.attack(target);
        // Check if the Energy should be 0 (damage should be 0)
        Assert.assertEquals("Energy should be 0 (damage should be 0)", 0, damage);
        // Check if the Klingon should be marked for removal
        Assert.assertTrue("Should be marked for removal", test.isMarkedForRemoval());
    }

    @Test
    public void testSymbolWhenNotScanned() {
        var test = new Klingon(2, 3);
        // Check if the symbol should show ? when not scanned
        Assert.assertEquals("Should show ? when not scanned", " ? ", test.symbol());
    }

    @Test
    public void testSymbolWhenScannedHighEnergy() {
        var test = new Klingon(2, 3);
        // Scan the Klingon and check if the symbol should show +K+ when scanned with high energy
        test.scan();
        Assert.assertEquals("Should show +K+ when scanned with high energy", "+K+", test.symbol());
    }

    @Test
    public void testSymbolWhenScannedHalfEnergy() {
        var test = new Klingon(2, 3);
        // Hit the Klingon for 151 damage (below half energy)
        test.hit(151); // Below half energy (149 left)
        test.scan();
        Assert.assertEquals("Should show +k+ when scanned with half energy", "+k+", test.symbol());
    }

    @Test
    public void testSymbolWhenScannedLowEnergy() {
        var test = new Klingon(2, 3);
        // Hit the Klingon for 201 damage (less than 1/3 energy)
        test.hit(201); // Less than 1/3 energy (99 left)
        test.scan();
        Assert.assertEquals("Should show -k- when scanned with low energy", "-k-", test.symbol());
    }

    @Test
    public void testScan() {
        var test = new Klingon(2, 3);
        // Check if the faction should start as NEUTRAL
        Assert.assertEquals("Should start as NEUTRAL", sttrswing.model.enums.Faction.NEUTRAL, test.faction());
        test.scan();
        Assert.assertEquals("Should become KLINGON after scan", sttrswing.model.enums.Faction.KLINGON, test.faction());
        // Check if the Klingon should be scanned
        Assert.assertTrue("Should be scanned", test.isScanned());
    }

    @Test
    public void testFaction() {
        var test = new Klingon(2, 3);
        // Check if the faction should be NEUTRAL
        Assert.assertEquals("Should be NEUTRAL faction", sttrswing.model.enums.Faction.NEUTRAL, test.faction());
    }
}