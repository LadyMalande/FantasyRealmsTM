package client;

import java.io.Serializable;

/**
 * Enum type with colors from the game to differentiate efficiently between them.
 * @author Tereza Miklóšová
 */
public enum Type implements Serializable {
    FLOOD, FLAME, LAND, WEATHER, ARMY, WEAPON, ARTIFACT, WIZARD, LEADER, BEAST, WILD
}
