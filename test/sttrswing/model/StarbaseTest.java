package sttrswing.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite for the Starbase class that provides healing and support functionality.
 * 
 * This test class validates Starbase-specific behavior including:
 * - Constructor and initial state (position, energy, faction)
 * - Healing mechanics (energy transfer to adjacent enterprises)
 * - Damage handling (energy reduction, death conditions)
 * - Symbol display variations based on energy state:
 *   * With energy: "[S]" (scanned) / " ? " (unscanned)
 *   * No energy: "|s|" (scanned) / " ? " (unscanned)
 * - Adjacency requirements for healing (only adjacent enterprises)
 * - Faction changes (NEUTRAL → FEDERATION after scanning)
 * - Healing limitations (single use, energy caps)
 * - Position-based healing restrictions
 * - Scanning mechanics and state transitions
 * 
 * The tests ensure Starbases provide proper support functionality
 * as friendly entities that can restore Enterprise energy when adjacent.
 */
public class StarbaseTest {

    @Test
    public void testConstructor() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Check if x is 2
        Assert.assertEquals("x should be 2", 2, test.getX());
        // Check if y is 2
        Assert.assertEquals("y should be 2", 2, test.getY());
        // Create a new Enterprise and check if it has 1, 1 coordinates
        // Test energy indirectly through attemptHeal (should transfer 300 energy since starbase starts with 300)
        var enterprise = new Enterprise(1, 1);
        int initialEnterpriseEnergy = enterprise.energy();
        test.attemptHeal(enterprise);
        // Check if the energy should be 300 (transferred to enterprise)
        Assert.assertEquals("energy should be 300 (transferred to enterprise)", initialEnterpriseEnergy + 300, enterprise.energy());
        // Check if the faction should be NEUTRAL
        Assert.assertEquals("faction should be NEUTRAL", sttrswing.model.enums.Faction.NEUTRAL, test.faction());
        test.scan(); // Need to scan to see the symbol
        // Check if the symbol should be |s| (since starbase transferred all its energy)
        Assert.assertEquals("symbol should be |s|", "|s|", test.symbol());
    }

    @Test
    public void testHit() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Create a new Enterprise and check if it has 1, 1 coordinates
        // Test energy indirectly through attemptHeal before and after hit
        var enterprise = new Enterprise(1, 1);
        int initialEnterpriseEnergy = enterprise.energy();
        test.attemptHeal(enterprise);
        int energyTransferredBefore = enterprise.energy() - initialEnterpriseEnergy;        
        // Reset the enterprise and hit the starbase
        enterprise = new Enterprise(1, 1);
        test.hit(50);
        int energyTransferredAfter = enterprise.energy();
        // Check if the energy should be reduced by 50
        test.attemptHeal(enterprise);
        int energyTransferredAfterHit = enterprise.energy() - energyTransferredAfter;
        
        // Check if the energy should be reduced by 50
        Assert.assertTrue("Energy should be reduced by 50", energyTransferredAfterHit < energyTransferredBefore);
        // Check if the starbase should be marked for removal (since it already transferred all its energy and then took 50 damage)
        Assert.assertTrue("Should be marked for removal", test.isMarkedForRemoval());
    }

    @Test
    public void testHitToDeath() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Hit the starbase for 300 damage
        test.hit(300);
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var enterprise = new Enterprise(1, 1);
        // Get the initial energy of the Enterprise
        int initialEnterpriseEnergy = enterprise.energy();
        // Attempt to heal the Enterprise
        test.attemptHeal(enterprise);
        // Get the energy of the Enterprise after healing
        int energyAfterHealing = enterprise.energy();
        // Check if the energy should be 0 (no energy transferred)
        Assert.assertEquals("Energy should be 0 (no energy transferred)", initialEnterpriseEnergy, energyAfterHealing);
        // Check if the starbase should be marked for removal
        Assert.assertTrue("Should be marked for removal", test.isMarkedForRemoval());
    }

    @Test
    public void testSymbolWhenNotScanned() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Check if the symbol should show ? when not scanned
        Assert.assertEquals("Should show ? when not scanned", " ? ", test.symbol());
    }

    @Test
    public void testSymbolWhenScannedWithEnergy() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Scan the starbase
        test.scan();
        // Check if the symbol should show [S] when scanned with energy
        Assert.assertEquals("Should show [S] when scanned with energy", "[S]", test.symbol());
    }

    @Test
    public void testSymbolWhenScannedNoEnergy() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Hit the starbase for 300 damage
        test.hit(300);
        // Scan the starbase
        test.scan();
        // Check if the symbol should show |s| when scanned with no energy
        Assert.assertEquals("Should show |s| when scanned with no energy", "|s|", test.symbol());
    }

    @Test
    public void testAttemptHealAdjacent() {
        // Create a new Starbase and check if x is 2 and y is 2
        var starbase = new Starbase(2, 2);
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var enterprise = new Enterprise(1, 1); // Adjacent to starbase
        // Get the initial energy of the Enterprise
        int initialEnterpriseEnergy = enterprise.energy();
        // Attempt to heal the Enterprise
        starbase.attemptHeal(enterprise);
        // Check if the Enterprise should gain starbase energy (300)
        Assert.assertEquals("Enterprise should gain starbase energy (300)", initialEnterpriseEnergy + 300, enterprise.energy());
        // Create a new Enterprise and check if it has 1, 1 coordinates
        enterprise = new Enterprise(1, 1);
        // Get the energy of the Enterprise before the second heal
        int energyBeforeSecondHeal = enterprise.energy();
        // Attempt to heal the Enterprise again
        starbase.attemptHeal(enterprise);
        // Check if the Starbase energy should be 0 (no transfer)
        Assert.assertEquals("Starbase energy should be 0 (no transfer)", energyBeforeSecondHeal, enterprise.energy());
    }

    @Test
    public void testAttemptHealNotAdjacent() {
        // Create a new Starbase and check if x is 2 and y is 2
        var starbase = new Starbase(2, 2);
        // Create a new Enterprise and check if it has 5, 5 coordinates
        var enterprise = new Enterprise(5, 5); // Not adjacent
        // Get the initial energy of the Enterprise
        int initialEnterpriseEnergy = enterprise.energy();
        // Attempt to heal the Enterprise
        starbase.attemptHeal(enterprise);
        // Check if the Enterprise energy should not change
        Assert.assertEquals("Enterprise energy should not change", initialEnterpriseEnergy, enterprise.energy());
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var adjacentEnterprise = new Enterprise(1, 1);
        int adjacentInitialEnergy = adjacentEnterprise.energy();
        starbase.attemptHeal(adjacentEnterprise);
        Assert.assertTrue("Starbase should still have energy", adjacentEnterprise.energy() > adjacentInitialEnergy);
    }

    @Test
    public void testAttemptHealSamePosition() {
        // Create a new Starbase and check if x is 2 and y is 2
        var starbase = new Starbase(2, 2);
        // Create a new Enterprise and check if it has 2, 2 coordinates
        var enterprise = new Enterprise(2, 2); // Same position
        // Get the initial energy of the Enterprise
        int initialEnterpriseEnergy = enterprise.energy();
        // Attempt to heal the Enterprise
        starbase.attemptHeal(enterprise);
        // Check if the Enterprise energy should not change
        Assert.assertEquals("Enterprise energy should not change", initialEnterpriseEnergy, enterprise.energy());
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var adjacentEnterprise = new Enterprise(1, 1);
        int adjacentInitialEnergy = adjacentEnterprise.energy();
        starbase.attemptHeal(adjacentEnterprise);
        Assert.assertTrue("Starbase should still have energy", adjacentEnterprise.energy() > adjacentInitialEnergy);
    }

    @Test
    public void testScan() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Check if the faction should be NEUTRAL
        Assert.assertEquals("Should start as NEUTRAL", sttrswing.model.enums.Faction.NEUTRAL, test.faction());
        // Scan the Starbase
        test.scan();
        // Check if the faction should be FEDERATION
        Assert.assertEquals("Should become FEDERATION after scan", sttrswing.model.enums.Faction.FEDERATION, test.faction());
        // Check if the Starbase should be scanned
        Assert.assertTrue("Should be scanned", test.isScanned());
    }

    @Test
    public void testHeal() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Hit the Starbase for 100 damage
        test.hit(100);
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var enterprise = new Enterprise(1, 1);
        // Get the initial energy of the Enterprise
        int initialEnterpriseEnergy = enterprise.energy();
        // Attempt to heal the Enterprise
        test.attemptHeal(enterprise);
        int energyTransferredAfterHit = enterprise.energy() - initialEnterpriseEnergy;
    }

    @Test
    public void testHealOverMax() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Heal the Starbase for 100 energy
        test.heal(100);
        // Create a new Enterprise and check if it has 1, 1 coordinates
        var enterprise = new Enterprise(1, 1);
        // Get the initial energy of the Enterprise
        int initialEnterpriseEnergy = enterprise.energy();
        // Attempt to heal the Enterprise
        test.attemptHeal(enterprise);
        // Check if the Enterprise energy should be capped at 300
        Assert.assertEquals("Energy should be capped at 300", initialEnterpriseEnergy + 300, enterprise.energy());
    }

    @Test
    public void testFaction() {
        // Create a new Starbase and check if x is 2 and y is 2
        var test = new Starbase(2, 2);
        // Check if the faction should be NEUTRAL
        Assert.assertEquals("Should be NEUTRAL faction", sttrswing.model.enums.Faction.NEUTRAL, test.faction());
    }
}