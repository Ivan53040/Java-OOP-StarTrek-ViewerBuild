package sttrswing.model;

import sttrswing.model.interfaces.HasPosition;
import sttrswing.model.interfaces.Hittable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * {@link Quadrant} are sections of space with a randomised mixture of {@link Star}s,
 * {@link Klingon}s and {@link Starbase}s.
 */
public class Quadrant implements Hittable, HasPosition {

  private final List<Star> stars = new ArrayList<>();
  private final List<Klingon> klingons = new ArrayList<>();
  private final List<Starbase> starbases = new ArrayList<>();
  private final XyPair position;
  private final int maxRows = 8;
  private final int maxCols = 8;

  /**
   * Constructs a {@link Quadrant} at the given coordinates.
   *
   * @param galaxyX - horizontal coordinate for this {@link Quadrant} in the {@link Galaxy}.
   * @param galaxyY - vertical coordinate for this {@link Quadrant} in the {@link Galaxy}.
   */
  public Quadrant(final int galaxyX, final int galaxyY) {
    this.position = new XyPair(galaxyX, galaxyY);
    Random random = new Random();
    // Doing 1-100 below is less efficient than 0-99, but makes it easy to see what I am doing
    int enemies = random.nextInt(100) + 1; // pick a random number from 1-100
    // this is not my final answer, I'm just using this variable as a temporary for now
    if (enemies > 97) {
      // 3% chance - very bad luck
      enemies = 3; // 3 Klingons at once! Better have good shields!
    } else if (enemies > 70) {
      // 30% chance - bad luck
      enemies = 2; // 2 Klingons at once. Default shields will take the first blast only.
    } else if (enemies > 40) {
      // 60% chance - more likely than unlikely
      enemies = 1; // 1 Klingon. Easy unless you are in bad shape.
    } else {
      // 40% - slightly less than 50:50
      enemies = 0; // No Klingons. You can relax in this Quadrant.
    }
    // floor(sqrt(0-81) gives 1x0, 3x1, 5x2, 7x3, 9x4, 11x5, 13x6, 15x7, 17x8, 1x9
    // (Yes the count really does increase by 2 for each truncated square root.)
    // So you are most likely to get 1 Star, slightly less 2, less 3, much less 4,
    // unlikely 5, very unlikely 6, rarely 7, almost never 8, and less than a
    // 1 in 90 chance of getting 9 stars or no stars in the Quadrant!
    int stars = 9 - (int) Math.floor(Math.sqrt(random.nextInt(82)));
    // The chances of Starbases in the Quadrant are like Klingons but better odds
    int starbases = random.nextInt(100) + 1; // pick another random number from 1-100
    // again, not my final answer, I'm just using this variable as a temporary for now
    if (starbases > 90) {
      // 1 in 10 chance - very good luck
      starbases = 3;
    } else if (starbases > 70) {
      // 3 in 10 chance - good luck
      starbases = 2;
    } else if (starbases > 30) {
      // 7 in 10 chance - more likely than unlikely
      starbases = 1;
    } else {
      // 3 in 10 chance - bad luck, no help here!
      starbases = 0;
    }
    // Now place all these in random spots in the Quadrant
    for (int y = 0; y < maxRows; y += 1) {
      for (int x = 0; x < maxCols; x += 1) {
        int d6 = random.nextInt(6) + 1; // d6 is a six-sided die (dice)
        if (d6 == 1 && starbases > 0) { // have to thow a "1" on the die to get a Starbase
          starbases -= 1;
          this.starbases.add(new Starbase(x, y));
        } else if (d6 == 2 && enemies > 0) { // or thow a "2" on the die to get a Klingon
          enemies -= 1;
          this.klingons.add(new Klingon(x, y));
        } else if (d6 == 3 && stars > 0) { // or thow a "3" on the die to get a Star
          stars -= 1;
          this.stars.add(new Star(x, y));
        }
      }
    }
  }

  /**
   * Used only for testing, so we can specify a given number of {@link Starbase}, {@link Klingon}
   * and {@link Star} to be generated in a given {@link Quadrant} in a very specific order for
   * predictability when testing.
   *
   * @param galaxyX   - horizontal coordinate
   * @param galaxyY   - vertical coordinate
   * @param starbases - number of {@link Starbase} for the {@link Quadrant} to place on empty
   *                  sectors in this {@link Quadrant}
   * @param klingons  - number of {@link Klingon} for the {@link Quadrant} to place on empty sectors
   *                  in this {@link Quadrant}
   * @param stars     - number of {@link Star} for the {@link Quadrant} to place on empty sectors in
   *                  this {@link Quadrant} Because this constructor is used only for testing, we
   *                  want the {@link Starbase}, {@link Klingon} and {@link Star} placed in a set
   *                  pattern it is expected we will place all the {@link Starbase}, then all the
   *                  {@link Klingon} and then all the {@link Star} going from the top left to the
   *                  bottom right placing in empty x,y coords until we have placed the given number
   *                  of each.
   *
   *                  <p>Ps. As a reminder only one {@link Starbase}, {@link Star} or
   *                  {@link Klingon} should be at a specific x,y coordinate for the
   *                  {@link Quadrant}, they should never overlap.
   *                  </p>
   */
  public Quadrant(final int galaxyX, final int galaxyY, int starbases, int klingons, int stars) {
    this.position = new XyPair(galaxyX, galaxyY);
        /*
        This is the original test code below,
        but YOU still have to modify it to put the objects in random places.
        You will need to import java.util.Random
        Look up how to use java.util.Random in the Java API.
        @todo change this test code to randomise the location of each the objects in the Quadrant
         */
    for (int starbase = 0; starbase < starbases; starbase++) {
      XyPair position = this.getRandomEmptySector();
      this.starbases.add(new Starbase(position.getX(), position.getY()));
    }
    for (int klingon = 0; klingon < klingons; klingon++) {
      XyPair position = this.getRandomEmptySector();
      this.klingons.add(new Klingon(position.getX(), position.getY()));
    }
    for (int star = 0; star < stars; star++) {
      XyPair position = this.getRandomEmptySector();
      this.stars.add(new Star(position.getX(), position.getY()));
    }
  }

  public XyPair getRandomEmptySector() {
    final List<XyPair> fullSectors = new ArrayList<>();

    for (Star star : this.stars) {
      fullSectors.add(new XyPair(star.getX(), star.getY()));
    }
    for (Klingon klingon : this.klingons) {
      fullSectors.add(new XyPair(klingon.getX(), klingon.getY()));
    }
    for (Starbase starbase : this.starbases) {
      fullSectors.add(new XyPair(starbase.getX(), starbase.getY()));
    }

    final List<XyPair> emptySectors = new ArrayList<>();
    for (int y = 0; y < this.maxRows; y++) {
      for (int x = 0; x < this.maxCols; x++) {
        XyPair option = new XyPair(x, y);
        if (!fullSectors.contains(option)) {
          emptySectors.add(option);
        }
      }
    }
    if (emptySectors.isEmpty()) {
      return null;
    }
    Random random = new Random();
    return emptySectors.get(random.nextInt(emptySectors.size()));
  }

  public List<Klingon> klingons() {
    return this.klingons;
  }

  public List<Star> stars() {
    return this.stars;
  }

  public List<Starbase> starbases() {
    return this.starbases;
  }

  /**
   * Returns the number of {@link Klingon} in this {@link Quadrant}.
   *
   * @return the number of {@link Klingon} in this {@link Quadrant}.
   */
  public int klingonCount() {
    return klingons.size();
  }

  /**
   * Returns the number of {@link Starbase} in this {@link Quadrant}.
   *
   * @return the number of {@link Starbase} in this {@link Quadrant}.
   */
  public int starbaseCount() {
    return starbases.size();
  }

  /**
   * Returns the number of {@link Star} in this {@link Quadrant}.
   *
   * @return the number of {@link Star} in this {@link Quadrant}.
   */
  public int starCount() {
    return this.stars.size();
  }


  /**
   * Returns the x-coordinate for this {@link Quadrant}.
   *
   * @return the x-coordinate for this {@link Quadrant}.
   */
  @Override
  public int getX() {
    return this.position.getX();
  }

  /**
   * Returns the y-coordinate for this {@link Quadrant}.
   *
   * @return the y-coordinate for this {@link Quadrant}.
   */
  @Override
  public int getY() {
    return this.position.getY();
  }

  /**
   * Returns the 3 character {@link String} symbol at the given x,y coordinates checking the
   * {@link Star}s, {@link Klingon}s, and {@link Starbase}s for any of those at the given
   * coordinates, if none are returns "   ".
   *
   * @param x - horizontal coordinate.
   * @param y - vertical coordinate.
   * @return 3 character {@link String} symbol.
   */
  public String getSymbolAt(final int x, final int y) {
    for (Star star : stars) {
      if (star.getX() == x && star.getY() == y) {
        return star.symbol();
      }
    }
    for (Klingon ship : klingons) {
      if (ship.getX() == x && ship.getY() == y) {
        return ship.symbol();
      }
    }
    for (Starbase starbase : starbases) {
      if (starbase.getX() == x && starbase.getY() == y) {
        return starbase.symbol();
      }
    }
    return "   ";
  }

  /**
   * Searches the {@link Quadrant} for any {@link Entity}s at the given location.
   *
   * @param x - horizontal coordinate
   * @param y - vertical coordinate
   * @return - if there is an {@link Entity} at the given x, y coordinate in the {@link Quadrant}
   * return that, otherwise return null.
   */
  public Entity getEntityAt(final int x, final int y) {
    for (Starbase starbase : this.starbases) {
      if (starbase.getX() == x && starbase.getY() == y) {
        return starbase;
      }
    }
    for (Entity star : this.stars) {
      if (star.getX() == x && star.getY() == y) {
        return star;
      }
    }
    for (Klingon klingon : this.klingons) {
      if (klingon.getX() == x && klingon.getY() == y) {
        return klingon;
      }
    }
    return null;
  }

  /**
   * Returns how many {@link Klingon}s are currently marked for removal.
   *
   * @return how many {@link Klingon}s are currently marked for removal.
   */
  public int klingonsMarkedForRemovalCount() {
    int totalDestroyed = 0;
    for (Klingon klingon : this.klingons) {
      if (klingon.isMarkedForRemoval()) {
        totalDestroyed += 1;
      }
    }
    return totalDestroyed;
  }

  @Override
  public String toString() {
    return "Quadrant\n"
        + "Stars: " + this.stars.size() + "\n"
        + "Enemies: " + this.klingons.size() + "\n"
        + "Starbases: " + this.starbases.size() + "\n";
  }

  /**
   * Scan every {@link Klingon}, {@link Starbase} and {@link Star} in this {@link Quadrant}.
   */
  public void scan() {
    for (Klingon klingon : this.klingons) {
      klingon.scan();
    }
    for (Starbase starbase : this.starbases) {
      starbase.scan();
    }
    for (Star star : this.stars) {
      star.scan();
    }
  }

  /**
   * Returns a {@link String} representation of the {@link Quadrant} formatted as DIGITDIGITDIGIT
   * i.e. 123
   * <ul>
   * <li>the first number is how many {@link Star} there are in this {@link Quadrant}.</li>
   * <li>the second number is how many {@link Starbase} there are in this {@link Quadrant}.</li>
   * <li>the third number is how many {@link Klingon} there are in this {@link Quadrant}.</li>
   * </ul>
   *
   * <p>So a {@link Quadrant} with 2 {@link Star}s, 0 {@link Starbase}s and 3 {@link Klingon}s
   * would have a {@link String}
   * representation of 203.</p>
   *
   * @return a {@link String} representation of the {@link Quadrant}
   */
  public String symbol() {
    return "" + this.stars.size() + this.starbases.size() + this.klingons.size();
  }

  /**
   * Handles a 'turn' of the {@link Game} for the {@link Starbase}s and {@link Klingon}s in this
   * {@link Quadrant}. Intended to be called only if the {@link Quadrant} is currently active.
   *
   * @param game - the current {@link Game} state for us to manipulate.
   */
  public void tick(final Game game) {
    Enterprise enterprise = game.getEnterprise();
    for (Starbase starbase : this.starbases) {
      starbase.attemptHeal(enterprise);
    }
    int totalDamage = 0;
    for (Klingon klingon : this.klingons) {
      boolean isDocked = enterprise.docked((ArrayList<Starbase>) this.starbases);
      if (isDocked) {
        System.out.println("ship was docked with a nearby starbase and thus safe from attack!");
      } else {
        totalDamage += klingon.attack(enterprise);
      }
    }
    this.cleanup();
  }

  /**
   * Intended to be called when you are not in this {@link Quadrant}. If a sector has 2 or less
   * {@link Klingon}s in it, any {@link Starbase} in the {@link Quadrant} will restore 10 energy. If
   * there are 3 or more {@link Klingon}s in the sector instead the starbase should lose 1 energy as
   * its supply lines are cut off by {@link Klingon} raids.
   *
   * @param game - game state we want to manipulate
   */
  public void outOfFocusTick(final Game game) {
    for (Starbase starbase : this.starbases) {
      if (this.klingonCount() > 2) {
        starbase.hit(1);
      } else {
        starbase.heal(10);
      }
    }
  }

  /**
   * Hit every {@link Hittable} {@link Klingon} in this {@link Quadrant}.
   *
   * @param damage - how much damage to do to EACH {@link Klingon}.
   */
  @Override
  public void hit(final int damage) {
    for (Klingon klingon : this.klingons) {
      klingon.hit(damage);
    }
  }

  /**
   * Removes any {@link Klingon} and {@link Starbase} that have been marked for removal from their
   * respective {@link List}. Only public for testing purposes.
   */
  public void cleanup() {
    for (int i = this.klingons.size() - 1; i >= 0; i -= 1) {
      if (this.klingons.get(i).isMarkedForRemoval()) {
        this.klingons.remove(i);
      }
    }
    for (int i = this.starbases.size() - 1; i >= 0; i -= 1) {
      if (this.starbases.get(i).isMarkedForRemoval()) {
        this.starbases.remove(i);
      }
    }
  }
}