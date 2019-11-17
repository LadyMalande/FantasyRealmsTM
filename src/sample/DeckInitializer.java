package sample;

import bonuses.*;
import maluses.*;
import interactive.*;


import java.io.*;
import java.util.ArrayList;

public class DeckInitializer implements Serializable{
    public void storeDecktoFile() {
        ArrayList<Card> tempDeck = new ArrayList<>();
        tempDeck.add(new Card(1, "Unicorn", 9, Type.CREATURE, null, null, null));
        tempDeck.add( new Card(2,"Magic Staff", 1, Type.WEAPON, null, null, null));
        tempDeck.add(new Card(3,"Hydra",12 , Type.CREATURE, null, null, null));
        tempDeck.add(new Card(4,"Basilisk",35 , Type.CREATURE, null, null, null));
        tempDeck.add(new Card(5,"Warhorse",6 , Type.CREATURE, null, null, null));
        tempDeck.add(new Card(6,"Dragon", 30, Type.CREATURE, null, null, null));
        tempDeck.add(new Card(7,"Zeppelin",35 , Type.WEAPON, null, null, null));
        tempDeck.add(new Card(8,"Warship", 23, Type.WEAPON, null, null, null));
        tempDeck.add(new Card(9,"Bow", 3, Type.WEAPON, null, null, null));
        tempDeck.add(new Card(10,"Keth Sword",7, Type.WEAPON, null, null, null));
        tempDeck.add(new Card(11,"Lord of Beasts",9, Type.WIZARD, null, null, null));
        tempDeck.add(new Card(12,"Collector",7, Type.WIZARD, null, null, null));
        tempDeck.add(new Card(13,"Necromant",3, Type.WIZARD, null, null, null));
        tempDeck.add( new Card(14,"Jester",3, Type.WIZARD, null, null, null));
        tempDeck.add(new Card(15,"Witch",5, Type.WIZARD, null, null, null));
        tempDeck.add(new Card(16,"Archmage",25, Type.WIZARD, null, null, null));
        tempDeck.add(new Card(17,"Princess",2, Type.LEADER, null, null, null));
        tempDeck.add(new Card(18,"Commander",4, Type.LEADER, null, null, null));
        tempDeck.add(new Card(19,"Queen",6, Type.LEADER, null, null, null));
        tempDeck.add(new Card(20,"King",8, Type.LEADER, null, null, null));
        tempDeck.add(new Card(21,"Empress",15, Type.LEADER, null, null, null));
        tempDeck.add(new Card(22,"Swordswomen",20, Type.ARMY, null, null, null));
        tempDeck.add(new Card(23,"Striders",5, Type.ARMY, null, null, null));
        tempDeck.add(new Card(24,"Dwarf Infantry",15, Type.ARMY, null, null, null));
        tempDeck.add(new Card(25,"Elven Bowmen",10, Type.ARMY, null, null, null));
        tempDeck.add(new Card(26,"Cavalry",17, Type.ARMY, null, null, null));
        tempDeck.add(new Card(27,"Keth Shield",4, Type.ARTIFACT, null, null, null));
        tempDeck.add(new Card(28,"Guard Rune",1, Type.ARTIFACT, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}  , null, null));
        tempDeck.add(new Card(29,"Crystal of Order",5, Type.ARTIFACT, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(30,"World Tree",2, Type.ARTIFACT, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(31,"Book of Spells",3, Type.ARTIFACT, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(32,"Fountain of Life",1, Type.FLOOD, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(33,"Great Flood",32, Type.FLOOD, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(34,"Elemental of Water",4, Type.FLOOD, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(35,"Swamp",18, Type.FLOOD, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(36,"Island",14, Type.FLOOD, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(37,"Elemental of Fire",4, Type.FIRE, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(38,"Lightning",11, Type.FIRE, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(39,"Candle",2, Type.FIRE, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(40,"Forge",9, Type.FIRE, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(41,"Conflagration",40, Type.FIRE, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(42,"Belfry",8, Type.EARTH, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(43,"Elemental of Earth",4, Type.EARTH, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(44,"Cave",6, Type.EARTH, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(45,"Mountain",9, Type.EARTH, new ArrayList<Bonus>() {{add(new DeleteAllMaluses());}}, null, null));
        tempDeck.add(new Card(46,"Forest",7, Type.EARTH, null, null, null));
        tempDeck.add(new Card(47,"Blizzard",30, Type.WEATHER, null, null, null));
        tempDeck.add(new Card(48,"Elemental of Air",4, Type.WEATHER, null, null, null));
        tempDeck.add(new Card(49,"Tornado",13, Type.WEATHER, null, null, null));
        tempDeck.add(new Card(50,"Storm",8, Type.WEATHER, null, null, null));
        tempDeck.add(new Card(51,"Smoke",27, Type.WEATHER, null, null, null));
        tempDeck.add(new Card(52,"Skinchanger",0, Type.WILD, null, null, null));
        tempDeck.add(new Card(53,"Doppleganger",0, Type.WILD, null, null, null));
        tempDeck.add(new Card(54,"Mirage",0, Type.WILD, null, null, null));





        try {
            //File file = new File("DefaultGameDeckCardsObjects.txt");
            //System.out.println(file.getAbsolutePath());
            //System.out.println(new File(".").getAbsolutePath());
            FileOutputStream f = new FileOutputStream(new File("DefaultGameDeckCardsObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            for(Card c: tempDeck){
                o.writeObject(c);
            }

            o.close();
            f.close();



        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        }
    }

    public ArrayList<Card> loadDeckFromFile(){
        ArrayList<Card> deck = new ArrayList<>();

        try{
            FileInputStream fi = new FileInputStream(new File("DefaultGameDeckCardsObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            for(int i = 0; i < 54; i++){
                deck.add((Card) oi.readObject());
            }

            oi.close();
            fi.close();
        } catch (IOException e) {
            System.out.println("Error initializing stream while loading");
        }
         catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return deck;
    }
}
