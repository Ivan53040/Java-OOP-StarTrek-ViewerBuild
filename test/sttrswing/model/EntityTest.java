package sttrswing.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite for the base Entity class that serves as the foundation for all game entities.
 * 
 * This test class covers the core functionality that all entities inherit:
 * - Position management (get/set X and Y coordinates)
 * - Position adjustment operations
 * - Scanning mechanics (marking entities as scanned)
 * - Removal marking (flagging entities for cleanup)
 * - Symbol display system (3-character format with scanned/unscanned states)
 * - Hit damage handling (basic damage without removal)
 * - String representation (toString method)
 * - State tracking (scanned status)
 * 
 * These tests ensure the fundamental entity behavior works correctly
 * for all subclasses (Enterprise, Klingon, Starbase, Star).
 */
public class EntityTest {

    @Test
    public void testGetX() {
        // Create a new Entity and check if x is 6
        var test = new Entity(6, 4);
        // Check if x is 6
        Assert.assertEquals("x should be 6", 6, test.getX());
    }

    @Test
    public void testGetY() {
        // Create a new Entity and check if y is 4
        var test = new Entity(6, 4);
        // Check if y is 4
        Assert.assertEquals("y should be 4", 4, test.getY());
    }

    @Test
    public void testSetX() {
        // Create a new Entity and check if x is 6
        var test = new Entity(6, 4);
        // Check if x is 6
        Assert.assertEquals("x should be 6", 6, test.getX());
        test.setX(3);
        // Check if x was 6, should have been set to 3
        Assert.assertEquals("x was 6, should have been set to 3", 3, test.getX());
    }

    @Test
    public void testSetY() {
        // Create a new Entity and check if y is 4
        var test = new Entity(6, 4);
        // Check if y is 4
        Assert.assertEquals("y should be 4", 4, test.getY());
        test.setY(7);
        // Check if y was 4, should have been set to 7
        Assert.assertEquals("y was 4, should have been set to 7", 7, test.getY());
    }

    @Test
    public void testScanned() {
        // Create a new Entity and check if it is scanned
        var test = new Entity(0, 0);
        // Scan the Entity
        test.scan();
        // Check if the Entity should return as scanned
        Assert.assertTrue("after .scan() is called a entity should return as scanned", test.isScanned());
    }

    @Test
    public void testRemoved() {
        // Create a new Entity and check if it is marked for removal
        var test = new Entity(0, 0);
        // Remove the Entity
        test.remove();
        // Check if the Entity should return as marked for removal
        Assert.assertTrue("after .remove() is called a entity should return as marked for removal", test.isMarkedForRemoval());
    }

    @Test
    public void testAdjust() {
        // Create a new Entity and check if x is 1 and y is 2
        var test = new Entity(1, 2);
        // Check if x is 1
        Assert.assertEquals("Cannot test - start wrong - x should be 1", 1, test.getX());
        // Check if y is 2
        Assert.assertEquals("Cannot test - start wrong - y should be 2", 2, test.getY());
        // Adjust the Entity by 4 units in the x direction and 0 units in the y direction
        test.adjustPosition(4, 0);
        // Adjust the Entity by 0 units in the x direction and 2 units in the y direction
        test.adjustPosition(0, 2);
        // Check if x should have been changed from 1 to 5 by adjust position
        Assert.assertEquals("x should have been changed from 1 to 5 by adjust position", 5, test.getX());
        // Check if y should have been changed from 2 to 4 by adjust position
        Assert.assertEquals("y should have been changed from 2 to 4 by adjust position", 4, test.getY());
    }

    @Test
    public void testSetSymbol() {
        // Create a new Entity and check if the symbol is set to +++
        var test = new Entity(0, 0);
        // Scan the Entity
        test.scan();
        // Set the symbol to +++
        test.setSymbol("+++");
        // Check if the symbol should have been set to +++
        Assert.assertEquals("Should have set symbol to +++", "+++", test.symbol());
    }

    @Test
    public void testSymbolWhenNotScanned() {
        // Create a new Entity and check if the symbol is set to ?
        var test = new Entity(0, 0);
        // Set the symbol to +++
        test.setSymbol("<insert>");
        // Check if the symbol should show ? when not scanned
        Assert.assertEquals("Should show ? when not scanned", " ? ", test.symbol());
    }

    @Test
    public void testHit() {
        // Create a new Entity and check if it is not marked for removal after hitting it for 100 damage
        var test = new Entity(0, 0);
        // Hit the Entity for 100 damage
        test.hit(100);
        // Check if the Entity should not be marked for removal after hitting it for 100 damage
        Assert.assertFalse("Entity should not be marked for removal after hit", test.isMarkedForRemoval());
    }

    @Test
    public void testToString() {
        // Create a new Entity and check if the toString returns Entity[x:3, y:4, scanned:true, markedForRemoval:true]
        var test = new Entity(3, 4);
        // Scan the Entity
        test.scan();
        // Remove the Entity
        test.remove();
        // Check if the toString returns Entity[x:3, y:4, scanned:true, markedForRemoval:true]
        String result = test.toString().replaceAll("\\s", "").replaceAll("isMarkedForRemoval", "markedForRemoval");
        Assert.assertEquals("toString() should return Entity[x:3, y:4, scanned:true, markedForRemoval:true]", 
            "Entity[x:3,y:4,scanned:true,markedForRemoval:true]", result);
    }

    @Test
    public void testIsScanned() {
        // Create a new Entity and check if it is not scanned initially
        var test = new Entity(0, 0);
        // Check if the Entity should not be scanned initially
        Assert.assertFalse("Entity should not be scanned initially", test.isScanned());
        
        // Scan the Entity
        test.scan();
        // Check if the Entity should be scanned after scan()
        Assert.assertTrue("Entity should be scanned after scan()", test.isScanned());
    }

    @Test
    public void testHitMultipleTimes() {
        // Create a new Entity and test multiple hits
        var test = new Entity(0, 0);
        // Hit the Entity multiple times
        test.hit(50);
        test.hit(25);
        test.hit(100);
        // Check if the Entity should not be marked for removal (basic Entity hit does nothing)
        Assert.assertFalse("Entity should not be marked for removal after multiple hits", test.isMarkedForRemoval());
    }

    @Test
    public void testHitZeroDamage() {
        // Create a new Entity and test zero damage hit
        var test = new Entity(0, 0);
        // Hit the Entity for 0 damage
        test.hit(0);
        // Check if the Entity should not be marked for removal
        Assert.assertFalse("Entity should not be marked for removal after zero damage hit", test.isMarkedForRemoval());
    }

    @Test
    public void testHitNegativeDamage() {
        // Create a new Entity and test negative damage hit
        var test = new Entity(0, 0);
        // Hit the Entity for negative damage
        test.hit(-50);
        // Check if the Entity should not be marked for removal
        Assert.assertFalse("Entity should not be marked for removal after negative damage hit", test.isMarkedForRemoval());
    }

    @Test
    public void testInitialState() {
        // Create a new Entity and check initial state
        var test = new Entity(5, 7);
        // Check if the Entity starts with correct position
        Assert.assertEquals("Entity should start at position (5,7)", 5, test.getX());
        Assert.assertEquals("Entity should start at position (5,7)", 7, test.getY());
        // Check if the Entity starts not scanned
        Assert.assertFalse("Entity should not be scanned initially", test.isScanned());
        // Check if the Entity starts not marked for removal
        Assert.assertFalse("Entity should not be marked for removal initially", test.isMarkedForRemoval());
        // Check if the Entity starts with default symbol
        Assert.assertEquals("Entity should start with default symbol", " ? ", test.symbol());
    }
}