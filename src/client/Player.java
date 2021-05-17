package client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 * Player holds all the information needed to know about the player.
 * @author Tereza Miklóšová
 */
public class Player {
    /**
     * ArrayList to hold all the simplified cards the player has in his hand.
     */
    public ArrayList<SimplifiedCard> simhand;

    /**
     * Player's name.
     */
    private String name;

    /**
     * Maximal number of players the player wants to play with.
     */
    private int maxPlayers;

    /**
     * True if the player wants to play with random deck. False if he wants to play with original deck.
     */
    private boolean randomDeck;

    /**
     * Locale for the language the player chose in the main menu.
     */
    private Locale locale;

    /**
     * How many AI to include in this game.
     */
    private int numberOfAI;

    /**
     * @return {@link Player#name}
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return {@link Player#maxPlayers}
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     *
     * @return {@link Player#randomDeck}
     */
    public boolean isRandomDeck() {
        return randomDeck;
    }

    /**
     *
     * @return {@link Player#locale}
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     *
     * @return {@link Player#numberOfAI}
     */
    public int getNumberOfAI() {
        return numberOfAI;
    }

    /**
     * Constructor for the Player object. Reads the config file the player has crated in main menu.
     */
    Player(){
        initializeFromConfigFile();
        simhand = new ArrayList<>();
        System.out.println("Player created.");
    }

    /**
     * Loads the set values from config file that player has created by filling the dialogs in the main menu.
     */
    private void initializeFromConfigFile(){
        Properties props = new Properties();
        File configFile = new File("config.properties");
        try{
            FileReader reader = new FileReader(configFile);
            // load the properties file:
            props.load(reader);

            name = props.getProperty("name");
            maxPlayers = Integer.parseInt(props.getProperty("menu_numberofplayers"));
            randomDeck = Boolean.parseBoolean(props.getProperty("randomdeck"));
            locale = new Locale(props.getProperty("locale"));
            numberOfAI = Integer.parseInt(props.getProperty("numberofai"));
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "client settings");

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
