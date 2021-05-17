package client;

import java.util.Comparator;

/**
 * SimplifiedCard represents a simplified version of the server counterpart Card.
 * The main difference is that this card only has TEXT instead of precisely implemented Bonuses, Maluses or Interactives.
 * Since all counting of score is done on the server part, there is no need for those objects here.
 * @author Tereza Miklóšová
 */
public class SimplifiedCard implements Comparable<SimplifiedCard>{
    /**
     * Unique id of the card.
     */
    private int id;

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Strength of the card.
     */
    private int strength;

    /**
     * Type (Color) of the card.
     */
    private String type;

    /**
     * Text of all the bonuses, interactives and penalties the card contains.
     */
    private String allText;

    /**
     * Constructor for the simplified counterpart of the Card used in server.
     * @param id Unique id of the card.
     * @param name Name of the card.
     * @param strength Strength of the card.
     * @param type Type (color) of the card.
     * @param allText Text of all bonuses and penaltied on the card.
     */
    SimplifiedCard(int id,String name, int strength, String type, String allText){
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.type = type;
        this.allText = allText;
    }

    /**
     * Sets {@link SimplifiedCard#name}
     * @param name {@link SimplifiedCard#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets {@link SimplifiedCard#strength}
     * @param strength {@link SimplifiedCard#strength}
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Sets {@link SimplifiedCard#type}
     * @param type {@link SimplifiedCard#type}
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets {@link SimplifiedCard#allText}
     * @param allText {@link SimplifiedCard#allText}
     */
    public void setAllText(String allText) {
        this.allText = allText;
    }

    /**
     * @return {@link SimplifiedCard#id}
     */
    public int getId() {
        return id;
    }

    /**
     * @return {@link SimplifiedCard#name}
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return {@link SimplifiedCard#type}
     */
    public String getType(){
        return this.type;
    }

    /**
     * @return {@link SimplifiedCard#strength}
     */
    public int getStrength(){
        return this.strength;
    }

    /**
     * @return {@link SimplifiedCard#allText}
     */
    public String getAllText() {
        return allText;
    }

    /**
     * Used to compare the cards primarily by type, then by strength, then by name.
     * @param card Card to compare to.
     * @return Returns the order of the cards.
     */
    @Override
    public int compareTo(SimplifiedCard card){
        return Comparator.comparing(SimplifiedCard::getType)
                .thenComparingInt(SimplifiedCard::getStrength)
                .thenComparing(SimplifiedCard::getName)
                .compare(this, card);
    }
}
