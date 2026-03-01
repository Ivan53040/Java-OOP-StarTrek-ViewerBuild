package sttrswing.model;

import sttrswing.model.enums.Faction;
import sttrswing.model.interfaces.HasFaction;

/**
 * {@link Star} is a {@link Entity} that starts already scanned as it is pretty hard to not detect a
 * {@link Star} in a {@link Quadrant}.
 */
public class Star extends Entity implements HasFaction {
  private final Faction faction = Faction.NEUTRAL;

  /**
   * Constructs a {@link Star} instance at the given coordinates.
   *
   * @param x - horizontal coordinate
   * @param y - vertical coordinate
   */
  public Star(final int x, final int y) {
    super(x, y);
    this.setSymbol(" * ");
    this.scan();
  }

  /**
   * Return the {@link Faction} this belongs to.
   *
   * @return the {@link Faction} this belongs to
   */
  public Faction faction() {
    return faction;
  }
}
