package sttrswing.model;

import sttrswing.model.enums.Faction;
import sttrswing.model.interfaces.HasFaction;
import sttrswing.model.interfaces.Healable;
import sttrswing.model.interfaces.Hittable;

/**
 * {@link Starbase} is a {@link Hittable} {@link Entity} that can heal the {@link Enterprise} when
 * it is adjacent to the {@link Starbase} in the same {@link Quadrant}.
 */
public class Starbase extends Entity implements Hittable, HasFaction, Healable {

  private final int maxEnergy = 300;
  private final Stat energy = new Stat(maxEnergy, maxEnergy);
  private Faction faction = Faction.NEUTRAL;

  /**
   * Constructs a {@link Starbase} instance at the given coordinates.
   *
   * @param x - horizontal coordinate
   * @param y - vertical coordinate
   */
  public Starbase(final int x, final int y) {
    super(x, y);
    this.setSymbol("[S]");
  }

  /**
   * Return the {@link Faction} this appears to belong to.
   *
   * @return the {@link Faction} this appears to belong to.
   */
  @Override
  public Faction faction() {
    return faction;
  }

  /**
   * Handles returning a 3 character {@link String} representation of the {@link Starbase} with some
   * implicit state communicated based on the output. i.e. if the {@link Starbase} has more than 0
   * energy reserves left, symbol should return [S], if it has 0 energy reserves left it should
   * return |s|.
   *
   * @return a 3 character string representation of the {@link Starbase}.
   */
  @Override
  public String symbol() {
    if (!this.isScanned()) {
      return " ? ";
    }
    if (this.energy.get() > 0) {
      return "[S]";
    }
    return "|s|";
  }

  /**
   * {@link Starbase} handles damage being inflicted on it and determines whether to then mark it
   * for removal.
   *
   * @param damage - amount of damage to inflict to the energy reserves of the {@link Starbase}.
   */
  @Override
  public void hit(final int damage) {
    this.energy.adjust(-damage);
    if (this.energy.get() < 1) {
      this.remove();
    }
  }

  /**
   * Handles checking if the given {@link Enterprise} is adjacent to the {@link Starbase}, and if it
   * is, draining its own energy reserves to give to the {@link Enterprise}.
   *
   * @param enterprise - The {@link Enterprise} to check against the current position of our
   *                   {@link Starbase} to work out if it is adjacent, and call the
   *                   {@link Enterprise}s heal method if so.
   */
  public void attemptHeal(final Enterprise enterprise) {
    int deltaX = Math.abs(this.getX() - enterprise.getX());
    int deltaY = Math.abs(this.getY() - enterprise.getY());
    if (deltaX <= 1 && deltaY <= 1 && !(deltaX == 0 && deltaY == 0)) {
      enterprise.gainEnergy(this.energy.get());
      this.energy.set(0);
    }
  }

  /**
   * Scan this ship and reveal it's {@link Faction}.
   */
  @Override
  public void scan() {
    super.scan();
    this.faction = Faction.FEDERATION;
  }

  /**
   * Replenish the starbases energy by the given amount up to it's maximum.
   *
   * @param energy - the amount of energy we want the Starbase to replenish by.
   */
  @Override
  public void heal(int energy) {
    this.energy.adjust(energy);
  }
}