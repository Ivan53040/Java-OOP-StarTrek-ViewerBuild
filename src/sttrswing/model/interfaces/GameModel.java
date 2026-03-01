package sttrswing.model.interfaces;

import sttrswing.model.Enterprise;
import sttrswing.model.Galaxy;
import sttrswing.model.Game;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is for specifying what methods are required specifically for use by the View, not every
 * method in our {@link Game} that needs to be public for testing, needs to be exposed to our View.
 */
public interface GameModel {

  /**
   * Returns if the game has been won by the player.
   *
   * @return if the game has been won by the player
   */
  boolean hasWon();

  /**
   * Returns if the game has been lost by the player.
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
   * Get the current X,Y coordinates for the current {@link sttrswing.model.Quadrant}.
   *
   * @return the position.
   */
  HasPosition galaxyPosition();

  /**
   * Get the current X,Y coordinates for the Player.
   *
   * @return the position.
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

  /**
   * Get a Hashmap for the surrounding {@link sttrswing.model.Quadrant} to the current {@link sttrswing.model.Quadrant}
   * the player is in.
   *
   * @return a Hashmap for the surrounding {@link sttrswing.model.Quadrant} to the current
   * {@link sttrswing.model.Quadrant} * the player is in.
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
   * Have the game take a turn, process actions, have enemies attack you etc.
   */
  void turn();

  /**
   * Return a {@link ArrayList} of Objects that fulfill the {@link HasPosition} {@link HasFaction}
   * {@link HasSymbol} interfaces.
   *
   * @param <T> allows us to access where objects in the quadrant are, who they are loyal to, and
   *            what symbol represents them visually without us having to know any other details.
   * @return a {@link ArrayList} of Objects that fulfill the {@link HasPosition} {@link HasFaction}
   * * {@link HasSymbol} interfaces.
   */
  <T extends HasPosition & HasSymbol & HasFaction> ArrayList<T> getSymbolsForQuadrant();

  /**
   * Move the {@link Enterprise} within the currentQuadrant.
   *
   * @param course   the course(direction) we wish to travel
   * @param distance how far we wish to travel
   */
  void moveWithinQuadrant(int course, double distance);

  /**
   * Request for a warp jump based move between {@link sttrswing.model.Quadrant}s.
   *
   * @param course   the course (direction) we wish to travel
   * @param distance how far we wish to travel
   */
  void moveBetweenQuadrants(int course, double distance);

  /**
   * Takes a newly made {@link Enterprise} and {@link Galaxy} to use for the game and updates all
   * relevant internal state.
   *
   * @param enterprise new enterprise state we want the game to use.
   * @param galaxy     new galaxy state we want the game to use.
   */
  public void load(Enterprise enterprise, Galaxy galaxy);

  /**
   * Export out a stringified representation of the internal state of our Game, specifically exports
   * the
   * {@link Enterprise} x,y,energy, shields, and torpedoes, as well as every {@link sttrswing.model.Quadrant}s
   * x,y, number of stars, klingons and starbases.
   * Example output structure:
   * [e] x:5 y:5 e:2500 s:500 t:10 |
   * [q] x:0 y:0 s:111 |
   * [q] x:0 y:1 s:002 |
   * [q] x:0 y:2 s:110 |
   * [q] x:0 y:3 s:021 |
   * [q] x:0 y:4 s:022 |
   * [q] x:0 y:5 s:120 |
   * [q] x:0 y:6 s:012 |
   * <p>
   * @return a stringified representation of the internal state of our Game.
   */
  String export();
}
