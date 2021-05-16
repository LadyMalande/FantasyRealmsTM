package client;

import java.util.Comparator;

public class SimplifiedCard implements Comparable<SimplifiedCard>{
    public int id;
    public String name;
    public int strength;
    public String type;
    public String allText;

    SimplifiedCard(int id,String name, int strength, String type, String allText){
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.type = type;
        this.allText = allText;
    }

    public String getName(){
        return this.name;
    }
    public String getType(){return this.type;}
    public int getStrength(){ return this.strength;    }

    @Override
    public int compareTo(SimplifiedCard card){
        return Comparator.comparing(SimplifiedCard::getType)
                .thenComparingInt(SimplifiedCard::getStrength)
                .thenComparing(SimplifiedCard::getName)
                .compare(this, card);
    }
}
