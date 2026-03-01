package sttrswing.model.interfaces;

import sttrswing.model.enums.Faction;

/**
 * Indicates an Object implementing {@link HasFaction} can indicate that it belongs to a
 * {@link Faction} and can indicate which one it belongs to.
 */
public interface HasFaction {

  /**
   * Returns which {@link Faction} the implementing Object belongs to.
   *
   * @return which {@link Faction} the implementing Object belongs to.
   */
  Faction faction();
}