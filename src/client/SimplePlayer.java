package client;

/**
 * Simplified version of Player class to collect the needed information about other players.
 * @author Tereza Miklóšová
 */
public class SimplePlayer {
    /**
     * Name of the player.
     */
    private String name;

    /**
     * True of the player is current active player. False otherwise.
     */
    private boolean playing;

    /**
     * Constructor for the Simple player. Sets the attributes.
     * @param name Name of the player.
     * @param playing True if the player is starting the game. False otherwise.
     */
    SimplePlayer(String name, boolean playing){
        this.name = name;
        this.playing = playing;
    }

    /**
     * @return {@link SimplePlayer#name}
     */
    public String getName(){
        return name;
    }

    /**
     * @return {@link SimplePlayer#playing}
     */
    public boolean getPlaying(){
        return playing;
    }

    /**
     * Sets {@link SimplePlayer#playing}
     * @param playing {@link SimplePlayer#playing}
     */
    public void setPlaying(boolean playing){
        this.playing = playing;
    }
}
