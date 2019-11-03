package sample;

import java.util.List;

class Card {
    String name;
    int strength;
    Type type;
    List<Bonus> bonuses;
    List<Malus> maluses;

    Card(String name, int strength, Type type, List<Bonus> bonuses, List<Malus> maluses){
        this.name = name;
        this.strength = strength;
        this.type = type;
        this.bonuses = bonuses;
        this.maluses = maluses;
    }
}
