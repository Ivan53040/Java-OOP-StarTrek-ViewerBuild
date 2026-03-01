package sttrswing.model.interfaces;

import sttrswing.model.Game;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is for specifying what methods are required specifically for use by the View, not every
 * method in our {@link Game} that needs to be public for testing, needs to be exposed to our View.
 */
public interface GameView {

  /**
   * Returns if the game has been won by the player
   *
   * @return if the game has been won by the player
   */
  boolean hasWon();

  /**
   * Returns if the game has been lost by the player
   *
   * @return if the game has been lost by the player
   */
  boolean hasLost();

  /**
   * Returns a string summary of the last game actions that occurred.
   *
   * @return a string summary of the last game actions that occurred.
   */
  String lastActionReport();

  /**
   * Get the current X,Y coordinates for the current {@link sttrswing.model.Quadrant}
   */
  HasPosition galaxyPosition();

  /**
   * Get the current X,Y coordinates for the Player
   */
  HasPosition playerPosition();

  /**
   * Returns how many spare torpedoes the player has.
   *
   * @return how many spare torpedoes the player has.
   */
  int spareTorpedoes();

  /**
   * Returns if the player has spare torpedoes.
   *
   * @return if the player has spare torpedoes.
   */
  boolean hasSpareTorpedoes();

  /**
   * Returns how much energy the player has allocated to shields currently.
   *
   * @return how much energy the player has allocated to shields currently.
   */
  int playerShields();

  /**
   * Returns how much energy the player has.
   *
   * @return how much energy the player has.
   */
  int playerEnergy();

  /**
   * Returns how much energy the player can spare for actions without dying.
   *
   * @return how much energy the player can spare for actions without dying.
   */
  int spareEnergy();

  /**
   * Returns if the player has enough energy to do an action that costs energy.
   *
   * @return if the player has enough energy to do an action that costs energy.
   */
  boolean hasSpareEnergy();

  /**
   * Returns if the player has enough energy to pay the provided energy cost.
   *
   * @param energy the amount of energy that would need to be spent.
   * @return if the player has enough energy to pay the provided energy cost.
   */
  boolean hasSpareEnergy(int energy);

  //TODO finish this
  /**
   * Get a Hashmap for
   *
   * @return
   */
  HashMap<String, String> getSurroundingQuadrants();

  /**
   * Adjust the players shields by the given amount of energy.
   *
   * @param requestedEnergyToSpend the amount of energy to adjust the players shields by.
   */
  void shields(int requestedEnergyToSpend);

  /**
   * Request to fire the players phasers with the given amount of energy spent.
   *
   * @param energy amount of energy to spend on firing phasers.
   */
  void firePhasers(int energy);

  /**
   * Request to fire a torpedo along the given course.
   *
   * @param course - the course (direction) we wish to fire the torpedo in.
   */
  void fireTorpedo(int course);

  /**
   * Scan the current quadrant and all entities within it.
   */
  void scanQuadrant();

  /**
   * Returns how many starbases are left in the Galaxy.
   *
   * @return How many starbases are left in the Galaxy.
   */
  int totalStarbaseCount();

  /**
   * Returns how many klingons are left in the Galaxy.
   *
   * @return how many klingons are left in the Galaxy.
   */
  int totalKlingonCount();

  /**
   * Have the game take a turn, process actions etc
   */
  void turn();

  <T extends HasPosition & HasSymbol & HasFaction> ArrayList<T> getSymbolsForQuadrant();

  void moveWithinQuadrant(int course, double distance);

  /**
   * Request for a warp jump based move between {@link sttrswing.model.Quadrant}s.
   *
   * @param course   - the course (direction) we wish to travel
   * @param distance - how far we wish to travel
   */
  void moveBetweenQuadrants(int course, double distance);
}
