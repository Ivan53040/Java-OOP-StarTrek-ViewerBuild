package sttrswing.model;

import sttrswing.model.interfaces.GameModel;
import sttrswing.model.interfaces.HasFaction;
import sttrswing.model.interfaces.HasPosition;
import sttrswing.model.interfaces.HasSymbol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The main Game class responsible for holding all relevant classes for the game coordinating player
 * and enemy actions.
 */
public class Game implements GameModel {

  private String report = "";
  private Galaxy galaxy;
  private final int startingQuadrantX = 4;
  private final int startingQuadrantY = 4;
  private Quadrant currentQuadrant;
  private Enterprise enterprise;

  /**
   * Construct an instance of {@link Game} with a generated list of 64 {@link Quadrant}s and a
   * current {@link Quadrant} chosen to act as the starting {@link Quadrant}.
   */
  public Game() {
    this.enterprise = new Enterprise(5, 5);

    this.galaxy = new Galaxy();
    this.currentQuadrant = this.getGalaxy().quadrantAt(startingQuadrantX, startingQuadrantY);
  }

  @Override
  public boolean hasWon() {
    return this.enterprise.isAlive() && this.totalKlingonCount() == 0;
  }

  @Override
  public boolean hasLost() {
    return !this.enterprise.isAlive();
  }

  @Override
  public boolean hasSpareTorpedoes() {
    return this.enterprise.hasTorpedoAmmo();
  }

  @Override
  public boolean hasSpareEnergy() {
    return this.enterprise.energy() > 1;
  }

  /**
   * Returns if the {@link Enterprise} can spare the amount of energy requested, (if its energy is
   * at 1, it can't spare any energy as a reminder).
   *
   * @param energy - the amount of energy we want to check if the {@link Enterprise} can spare.
   * @return if the {@link Enterprise} can spare that much energy.
   */
  @Override
  public boolean hasSpareEnergy(int energy) {
    return this.enterprise.energy() > energy && this.enterprise.energy() > 1;
  }

  @Override
  public int playerShields() {
    return this.enterprise.shields();
  }

  @Override
  public HasPosition galaxyPosition() {
    return this.currentQuadrant;
  }

  @Override
  public HasPosition playerPosition() {
    return this.enterprise;
  }

  @Override
  public int spareTorpedoes() {
    return this.enterprise.torpedoAmmo();
  }

  @Override
  public int playerEnergy() {
    return this.enterprise.energy();
  }

  /**
   * Returns how much energy the {@link Enterprise} has spare.
   *
   * @return how much energy the {@link Enterprise} has spare.
   */
  public int spareEnergy() {
    if (this.hasSpareEnergy()) {
      return this.enterprise.energy() - 1;
    }
    return 0;
  }

  /**
   * Returns the {@link Quadrant}s {@link String} symbols around the current {@link Quadrant} in the
   * {@link Galaxy}.
   *
   * <p>The return hashmap will always include the following keys: top topLeft topRight right
   * bottomRight bottom bottomLeft left
   * </p>
   *
   * @return a {@code HashMap<String,String>} with the surrounding {@link Quadrant}s symbols linked
   * to the relevant keys.
   */
  public HashMap<String, String> getSurroundingQuadrants() {
    int quadrantX = currentQuadrant.getX();
    int quadrantY = currentQuadrant.getY();

    HashMap<String, String> surroundingQuadrants = new HashMap<>();
    // Initialize all directions to null
    surroundingQuadrants.put("top", null);
    surroundingQuadrants.put("topLeft", null);
    surroundingQuadrants.put("topRight", null);
    surroundingQuadrants.put("right", null);
    surroundingQuadrants.put("bottomRight", null);
    surroundingQuadrants.put("bottom", null);
    surroundingQuadrants.put("bottomLeft", null);
    surroundingQuadrants.put("left", null);
    surroundingQuadrants.put("current", null);

    List<Quadrant> quadrants = this.getGalaxy().getQuadrantClusterAt(quadrantX, quadrantY);
    for (Quadrant quadrant : quadrants) {
      int deltaX = quadrant.getX() - quadrantX;
      int deltaY = quadrant.getY() - quadrantY;

      if (deltaX == 0 && deltaY == -1) {
        surroundingQuadrants.put("top", quadrant.symbol());
      } else if (deltaX == -1 && deltaY == -1) {
        surroundingQuadrants.put("topLeft", quadrant.symbol());
      } else if (deltaX == 1 && deltaY == -1) {
        surroundingQuadrants.put("topRight", quadrant.symbol());
      } else if (deltaX == 1 && deltaY == 0) {
        surroundingQuadrants.put("right", quadrant.symbol());
      } else if (deltaX == 1 && deltaY == 1) {
        surroundingQuadrants.put("bottomRight", quadrant.symbol());
      } else if (deltaX == 0 && deltaY == 1) {
        surroundingQuadrants.put("bottom", quadrant.symbol());
      } else if (deltaX == -1 && deltaY == 1) {
        surroundingQuadrants.put("bottomLeft", quadrant.symbol());
      } else if (deltaX == -1 && deltaY == 0) {
        surroundingQuadrants.put("left", quadrant.symbol());
      } else if (deltaX == 0 && deltaY == 0) {
        surroundingQuadrants.put("current", quadrant.symbol());
      }
    }

    return surroundingQuadrants;
  }


  /**
   * If there is any energy to spare, will spend as much of the requested amount of energy as it can
   * to bolster the {@link Enterprise}s shields.
   *
   * @param requestedEnergySpend - amount of energy requested to spend on the enterprises shields
   */
  @Override
  public void shields(final int requestedEnergySpend) {
    boolean canShield = this.hasSpareEnergy(requestedEnergySpend);
    if (canShield) {
      final int energyTransferred = this.enterprise.transferEnergyToShields(requestedEnergySpend);
      this.report = energyTransferred + " Energy transferred to Shields captain!";
      this.turn();
    } else {
      this.report = "Captain I cannae give anymore to the shields!";
    }
  }

  /**
   * Fires phasers for the given amount of energy in the current {@link Quadrant}.
   *
   * @param energy amount of energy to spend on firing phasers.
   */
  @Override
  public void firePhasers(int energy) {
    int energySpent = this.getEnterprise().drainEnergy(energy);
    this.phasers(energySpent, this.currentQuadrant);
  }

  /**
   * Return how many starbases exist in the games {@link Galaxy}.
   *
   * @return how many starbases exist in the games {@link Galaxy}.
   */
  @Override
  public int totalStarbaseCount() {
    return galaxy.starbaseCount();
  }


  /**
   * Return how many klingons exist in the games {@link Galaxy}.
   *
   * @return how many klingons exist in the games {@link Galaxy}.
   */
  @Override
  public int totalKlingonCount() {
    return galaxy.klingonCount();
  }

  /**
   * Returns the {@link Galaxy} instance being used by this game.
   *
   * @return the {@link Galaxy} class.
   */
  public Galaxy getGalaxy() {
    return this.galaxy;
  }

  /**
   * Returns the currently stored string report for the outcome of the last action.
   *
   * @return the currently stored string report for the outcome of the last action.
   */
  public String lastActionReport() {
    return this.report;
  }

  /**
   * Return the games {@link Enterprise} instance, useful for accessing internal state of the
   * {@link Enterprise} for display.
   *
   * @return the games {@link Enterprise} instance
   */
  public Enterprise getEnterprise() {
    return this.enterprise;
  }

  /**
   * Returns the current {@link Quadrant} the game is taking place in.
   *
   * @return the current {@link Quadrant} the game is taking place in.
   */
  public Quadrant getCurrentQuadrant() {
    return this.currentQuadrant;
  }

  /**
   * Scan the current Quadrant.
   */
  public void scanQuadrant() {
    this.currentQuadrant.scan();
    this.report =
        "Scanned Quadrant(" + this.currentQuadrant.getX() + "," + this.currentQuadrant.getY()
            + ") |";
    this.report += " " + this.currentQuadrant.klingonCount() + " Klingons";
    this.report += " " + this.currentQuadrant.starCount() + " Stars";
    this.report += " " + this.currentQuadrant.starbaseCount() + " Starbases";
  }

  /**
   * Fire a torpedo along the given course (direction).
   *
   * @param course - the course (direction) we wish to fire the torpedo in.
   */
  public void fireTorpedo(final int course) {
    this.torpedoes(course, this.currentQuadrant);
  }

  // JB: This is a bit overkill, but I want to expose them to generics somewhere, and this is the
  // most sensible place I could find in the assessment given how small/focused it is.
  // Realistically it could have just been a ArrayList<Entity> but then we lose
  // the teaching opportunity.

  /**
   * Returns a list of all symbols and their positions for the current {@link Quadrant}.
   *
   * @param <T> - Generic type to wrap together the symbol method, the relevant position methods and
   *            the faction method.
   * @return a list of symbols and their position values.
   */
  public <T extends HasPosition & HasSymbol & HasFaction> ArrayList<T> getSymbolsForQuadrant() {
    final ArrayList<T> list = new ArrayList<>();
    list.add((T) enterprise);
    for (Klingon klingon : this.currentQuadrant.klingons()) {
      list.add((T) klingon);
    }
    for (Starbase starbase : this.currentQuadrant.starbases()) {
      list.add((T) starbase);
    }
    for (Star star : this.currentQuadrant.stars()) {
      list.add((T) star);
    }
    return list;
  }

  /**
   * Progresses the game state forward by one turn. Calls .tick() on the current {@link Quadrant}
   * and calls .outOfFocusTick on {@link Quadrant}s that are not the current quadrant.
   */
  public void turn() {
    this.currentQuadrant.tick(this);
    var list = new ArrayList<Quadrant>();
    list.add(this.currentQuadrant);
    galaxy.outOfFocusTick(list, this);
    System.out.println("report " + this.report);
  }

  /**
   * Handles the Phaser action, takes in how much energy the user has spent to fire phasers. Phaser
   * energy is spread equally (rounding down) amongst the {@link Klingon}s in a {@link Quadrant}.
   *
   * @param energy   - how much energy they would like to spend on firing phasers.
   * @param quadrant - the quadrant we want to fire phasers in
   */
  public void phasers(final int energy, final Quadrant quadrant) {
    if (quadrant.klingonCount() < 1) {
      this.report = "Phaser (" + energy + ") energy wasted, there are no valid targets!";
      return;
    }

    final int klingons = quadrant.klingonCount();
    final int damagePerKlingon = (int) Math.floor(energy / klingons);
    quadrant.hit(damagePerKlingon);

    final int klingonsDestroyed = quadrant.klingonsMarkedForRemovalCount();
    this.report =
        "Phaser (" + energy + ") at " + klingons + " targets, " + damagePerKlingon
            + " dmg each, "
            + klingonsDestroyed + " down";
  }

  /**
   * Takes a given course, we wish to fire a torpedo at. Handles a torpedo being fired: travelling
   * across the current {@link Quadrant} sectors until it hits another {@link Entity} or the edge of
   * the {@link Quadrant}.
   *
   * @param course   - course/direction we wish to fire the torpedo along in the current
   *                 {@link Quadrant}.
   * @param quadrant - the {@link Quadrant} we wish to fire the torpedo in.
   */
  public void torpedoes(final int course, final Quadrant quadrant) {
    if (!this.getEnterprise().hasTorpedoAmmo()) {
      this.report = "NO TORPEDOS LEFT TO FIRE CAPTAIN!";
      return;
    }

    final var vector = this.getVectorFrom(course);
    final var torpedo = this.getEnterprise().fireTorpedo(); //handles firing the torpedo
    if (torpedo == null) {
      this.report = "I can't do it captain! We have no Torpedos left!";
      return;
    }
    torpedo.adjustPosition(vector.getX(), vector.getY());

    final int onGridMax = 8;
    final int maxIterations = 999;

    int iterations = 0;
    boolean isGoingToHit = (quadrant.getEntityAt(torpedo.getX(), torpedo.getY()) != null);
    boolean onGrid = (torpedo.getX() >= 0 && torpedo.getY() >= 0 && torpedo.getX() < onGridMax
        && torpedo.getY() < onGridMax);

    while (!isGoingToHit && onGrid && iterations < maxIterations) {
      iterations += 1;
      torpedo.adjustPosition(vector.getX(), vector.getY());
      isGoingToHit = (quadrant.getEntityAt(torpedo.getX(), torpedo.getY()) != null);
      onGrid = (torpedo.getX() > 0 && torpedo.getY() > 0 && torpedo.getX() < onGridMax
          && torpedo.getY() < onGridMax);
    }
    if (iterations == maxIterations) {
      throw new RuntimeException("Hit maximum iterations for Game.torpedos()!");
    }

    final String courseArrow = getDirectionIndicatorArrow(course);
    this.report = "Torpedo fired at course bearing:" + course + courseArrow
        + " flies off into the dark of space.";
    if (isGoingToHit) {
      this.report =
          "Torpedo fired at course bearing:" + course + courseArrow
              + " has hit target! It hit a:";
      Entity entity = quadrant.getEntityAt(torpedo.getX(), torpedo.getY());
      this.report += entity.symbol();
      entity.hit(999999);
    }
  }

  /**
   * Returns a string representation of which direction a given course will go using utf 8 arrows.
   * 1:"→", 2:"↗", 3:"↑", 4:"↖", 5:"←", 6:"↙", 7:"↓", 8:"↘", otherwise: "X"
   *
   * @param course - given course value indicating direction
   * @return a string representation of which direction a given course will go using utf 8 arrows.
   */
  public String getDirectionIndicatorArrow(final int course) {
    return switch (course) {
      case 1 -> "→";
      case 2 -> "↗";
      case 3 -> "↑";
      case 4 -> "↖";
      case 5 -> "←";
      case 6 -> "↙";
      case 7 -> "↓";
      case 8 -> "↘";
      default -> "X";
    };
  }

  /**
   * Returns a {@link XyPair} int representation of the direction of movement required to meet a
   * given course.
   *
   * @param course a double between 1 and 8.9
   * @return a {@link XyPair} representation of the direction of movement required.
   */
  public XyPair getVectorFrom(final double course) {
    final int direction = (int) Math.round(course);

    return switch (direction) {
      case 1 -> new XyPair(1, 0);
      case 2 -> new XyPair(1, -1);
      case 3 -> new XyPair(0, -1);
      case 4 -> new XyPair(-1, -1);
      case 5 -> new XyPair(-1, 0);
      case 6 -> new XyPair(-1, 1);
      case 7 -> new XyPair(0, 1);
      case 8 -> new XyPair(1, 1);
      default -> new XyPair(0, 0);
    };
  }

  /**
   * Move the given {@link Entity} within the {@link Quadrant}, by the course and distance given or
   * until another {@link Entity} or edge of the {@link Quadrant} would be hit.
   *
   * @param course   the course/direction we wish the given {@link Entity} to move within the given
   *                 {@link Quadrant}
   * @param distance how many sectors in the {@link Quadrant} to attempt to move
   */
  public void moveWithinQuadrant(final int course, final double distance) {
    //attempt to move distance in that sector updating the enterprise x,
    // stopping at the last viable move
    final int maxIterations = 999; //while loop safety, probably overkill! But I am paranoid.
    final XyPair vector = this.getVectorFrom(course);
    System.out.println("COURSE IS: " + course);
    System.out.println("VECTOR IS: " + vector);
    System.out.println("BEFORE MOVE ENTERPRISE IS AT");
    System.out.println(this.getEnterprise().getX());
    System.out.println(this.getEnterprise().getY());
    boolean nextSectorIsValid = true;
    int iterations = 0;
    int jumps = (int) Math.floor(distance);
    while (nextSectorIsValid && jumps > 0 && iterations < maxIterations) {
      jumps -= 1;
      iterations += 1;
      nextSectorIsValid = attemptMoveInQuadrant(this.currentQuadrant, this.getEnterprise(), vector);
    }

    this.report =
        "Moved" + this.getDirectionIndicatorArrow(course) + "(" + (int) Math.floor(distance)
            + ") within Quadrant(" + this.currentQuadrant.getX() + "," + this.currentQuadrant.getY()
            + ")";

    if (iterations == maxIterations) {
      throw new RuntimeException("Hit maximum iterations for Game.moveWithinQuadrant()!");
    }
  }

  /**
   * Attempt to move the given {@link Entity} in the given {@link Quadrant}, adjusting the
   * {@link Entity} position by the given {@link XyPair} vector.
   *
   * <p>If an edge or other {@link Entity} is encountered at the proposed new location, the
   * report for the turn is updated and  false is returned.</p>
   *
   * <p>If the proposed adjustment would be successful the {@link Entity} has its position set to
   * the new position and true is returned.</p>
   *
   * @param quadrant - {@link Quadrant} we wish to attempt to move the given {@link Entity} in.
   * @param entity   - {@link Entity} we wish to attempt to move within the given {@link Quadrant}
   * @param vector   - the adjustment we wish to make ot the {@link Entity}'s position
   * @return whether the attempted in {@link Quadrant} move was successful or not.
   */
  public boolean attemptMoveInQuadrant(final Quadrant quadrant, final Entity entity,
      final XyPair vector) {
    final int proposedX = entity.getX() + vector.getX();
    final int proposedY = entity.getY() + vector.getY();

    final boolean isatxedge = (proposedX < 0 || proposedX > 7);
    if (isatxedge) {
      this.report = "HIT THE EDGE OF THIS QUADRANT.";
      return false;
    }

    final boolean isatyedge = (proposedY < 0 || proposedY > 7);
    if (isatyedge) {
      this.report = "HIT THE EDGE OF THIS QUADRANT.";
      return false;
    }

    Entity quadrantEntity = quadrant.getEntityAt(proposedX, proposedY);
    if (quadrantEntity != null) { // Oh, no! there is something already there!!!
      this.report = "ENCOUNTERED A ENTITY:" + quadrantEntity.symbol() + "EN ROUTE.";
      return false;
    }

    //It's all clear to move, so lets move!
    entity.setX(proposedX);
    entity.setY(proposedY);
    return true;
  }

  /**
   * Moves which {@link Quadrant} the {@link Game} is handling using the given course, and given
   * number of quadrants to jump (rounded down).
   *
   * @param course   - the direction of the jump: 1:→, 2:↗, 3:↑, 4:↖, 5:←, 6:↙, 7:↓, 8:↘
   * @param distance - how many quadrants to jump through (we drop the decimal for when jumping
   *                 between quadrants)
   */
  public void moveBetweenQuadrants(final int course, final double distance) {
    XyPair vector = this.getVectorFrom(course);

    boolean nextQuadrantIsValid = true;
    final int maxIterations = 999; //while loop safety
    int iterations = 0;
    int jumps = (int) Math.floor(distance);
    String report = "";
    while (nextQuadrantIsValid && jumps > 0 && iterations < maxIterations) {
      jumps -= 1;
      iterations += 1;
      nextQuadrantIsValid = attemptMoveBetweenQuadrants(vector);
    }
    if (iterations == maxIterations) {
      throw new RuntimeException("Hit maximum iterations for Game.moveBetweenQuadrants()!");
    }
    if (nextQuadrantIsValid) {
      /* @todo confirm this is actually firing when I think it should */
      XyPair newPosition = this.currentQuadrant.getRandomEmptySector();
      report += "Moved" + this.getDirectionIndicatorArrow(course) + "(" + (int) Math.floor(distance)
          + ") between quadrants, arrived at quadrant (" + newPosition.getX() + ", "
          + newPosition.getY() + ")";
      this.report = report;

      System.out.println("ENTERPRISE ON ENTERING QUADRANT PLACED ON RANDOM EMPTY SECTOR");
      this.enterprise.setX(newPosition.getX());
      this.enterprise.setY(newPosition.getY());
    }
  }

  @Override
  public void load(Enterprise enterprise, Galaxy galaxy) {
    int x = this.currentQuadrant.getX();
    int y = this.currentQuadrant.getY();
    this.enterprise = enterprise;
    this.galaxy = galaxy;
    this.currentQuadrant = this.getGalaxy().quadrantAt(x, y);
  }

  /**
   * Will attempt to move using a given vector one quadrant and returns a boolean value indicating
   * if it succeeded or failed.
   *
   * @param vector - vector indicating how we wish to adjust the x and y of our position
   * @return if the attempted move between quadrants was successful
   */
  public boolean attemptMoveBetweenQuadrants(final XyPair vector) {
    Quadrant proposedQuadrant = this.getGalaxy()
        .quadrantAt(this.currentQuadrant.getX() + vector.getX(),
            this.currentQuadrant.getY() + vector.getY());
    if (proposedQuadrant == null) {
      return false;
    }

    this.currentQuadrant = proposedQuadrant;
    return true;
  }

  /**
   * Export out a stringified representation of the internal state of our Game, specifically exports
   * the
   * {@link Enterprise} x,y,energy, shields, and torpedoes, as well as every {@link sttrswing.model.Quadrant}s
   * x,y, number of stars, klingons and starbases. Example output structure: [e] x:5 y:5 e:2500
   * s:500 t:10 | [q] x:0 y:0 s:111 | [q] x:0 y:1 s:002 | [q] x:0 y:2 s:110 | [q] x:0 y:3 s:021 |
   * [q] x:0 y:4 s:022 | [q] x:0 y:5 s:120 | [q] x:0 y:6 s:012 |
   * <p>
   * @return a stringified representation of the internal state of our Game.
   */
  @Override
  public String export() {
    String exportedEnterprise = this.enterprise.export();
    String exportedGalaxy = this.galaxy.export();
    return exportedEnterprise + exportedGalaxy;
  }
}
