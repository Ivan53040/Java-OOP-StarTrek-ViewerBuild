package sttrswing.model.enums;

/**
 * Enum for use indicating factional allegiance.
 */
public enum Faction {
  /**
   * Faction that indicates that an Entity is known to be allied with the Federation.
   */
  FEDERATION,
  /**
   * Faction that indicates that an Entity is known to be allied with the Klingons. (and not the federation).
   */
  KLINGON,
  /**
   * Faction that indicates that an Entity's allegiances are not known, or known to be unallied.
   */
  NEUTRAL
}
