package sttrswing.model;

import sttrswing.model.enums.Faction;
import sttrswing.model.interfaces.HasFaction;
import sttrswing.model.interfaces.Hittable;

/**
 * {@link Klingon} handles {@link Entity} behaviour plus some extra behaviour including: processing
 * an attack, handling the details of being hit, managing its own energy reserve.
 */
public class Klingon extends Entity implements Hittable, HasFaction {

  private final int maxEnergy = 300;
  private final Stat energy = new Stat(maxEnergy, maxEnergy);
  private Faction faction = Faction.NEUTRAL;
  //until identified the klingon is detected as neutral

  /**
   * Constructs a {@link Klingon} instance at the given X and Y position.
   *
   * @param x - horizontal coordinate
   * @param y - vertical coordinate
   */
  public Klingon(final int x, final int y) {
    super(x, y);
  }

  /**
   * Return the {@link Faction} this belongs to.
   *
   * @return the {@link Faction} this belongs to
   */
  @Override
  public Faction faction() {
    return faction;
  }

  /**
   * Handles this {@link Klingon} attacking the given {@link Hittable} object, the {@link Klingon}
   * deals damage equal to 1 third of its current energy reserves (rounded down).
   * (this does not deplete its energy reserves).
   *
   * @param hittable - something we can hit
   * @return how much damage was dealt by this {@link Klingon} in this attack.
   */
  public int attack(final Hittable hittable) {
    final int thirdDamage = (int) Math.floor(this.energy.get() / 3);
    hittable.hit(thirdDamage);
    return thirdDamage;
  }

  /**
   * Hits the {@link Klingon} for an amount of damage, reducing their internal energy reserves and
   * marking them for removal if they run out of energy.
   *
   * @param damage - amount to reduce the {@link Klingon} energy reserves by.
   */
  public void hit(final int damage) {
    System.out.println("KLINGON HIT FOR" + -damage + " ENERGY:" + this.energy);
    this.energy.adjust(-damage);
    System.out.println("KLINGON NOW HAS ENERGY:" + this.energy);
    if (this.energy.get() < 1) {
      this.remove(); //mark for removal
    }
  }

  /**
   * Returns a 3 character {@link String} representation of the {@link Klingon} with implicit
   * communication of its internal state.
   *
   * @return if not scanned returns " ? " if scanned and energy is less than 1/3rd of starting
   * energy returns "-k-" if scanned and energy is less than 1/2 of starting energy returns "+k+"
   * else if scanned returns "+K+"
   */
  public String symbol() {
    if (!this.isScanned()) {
      return " ? ";
    }
    if (this.energy.get() < this.maxEnergy / 3) { //1/3rd hp or less left
      return "-k-";
    }
    if (this.energy.get() < this.maxEnergy / 2) { //half hp or less left
      return "+k+";
    }
    return "+K+";
  }

  @Override
  public void scan() {
    super.scan();
    this.faction = Faction.KLINGON;
  }
}
