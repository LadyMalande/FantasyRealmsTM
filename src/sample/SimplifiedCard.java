package sample;

public class SimplifiedCard {
    public long serialVersionUID = 11;
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
}
