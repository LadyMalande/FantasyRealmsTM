package sample;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class BigSwitches {
    public static String switchImage(int id){
        switch(id){
            case 1: return "graphics/1.jpg";
            case 2: return "graphics/icon.jpg";
            case 3: return "graphics/icon.jpg";
            case 4: return "graphics/4.jpg";
            case 5: return "graphics/5.jpg";
            case 6: return "graphics/6.jpg";
            case 7: return "graphics/icon.jpg";
            case 8: return "graphics/icon.jpg";
            case 9: return "graphics/icon.jpg";
            case 10: return "graphics/icon.jpg";
            case 11: return "graphics/11.jpg";
            case 12: return "graphics/12.jpg";
            case 13: return "graphics/13.jpg";
            case 14: return "graphics/icon.jpg";
            case 15: return "graphics/15.jpg";
            case 16: return "graphics/icon.jpg";
            case 17: return "graphics/17.jpg";
            case 18: return "graphics/icon.jpg";
            case 19: return "graphics/19.jpg";
            case 20: return "graphics/icon.jpg";
            case 21: return "graphics/icon.jpg";
            case 22: return "graphics/icon.jpg";
            case 23: return "graphics/icon.jpg";
            case 24: return "graphics/24.jpg";
            case 25: return "graphics/icon.jpg";
            case 26: return "graphics/26.jpg";
            case 27: return "graphics/icon.jpg";
            case 28: return "graphics/icon.jpg";
            case 29: return "graphics/icon.jpg";
            case 30: return "graphics/icon.jpg";
            case 31: return "graphics/icon.jpg";
            case 32: return "graphics/icon.jpg";
            case 33: return "graphics/icon.jpg";
            case 34: return "graphics/icon.jpg";
            case 35: return "graphics/icon.jpg";
            case 36: return "graphics/icon.jpg";
            case 37: return "graphics/37.jpg";
            case 38: return "graphics/icon.jpg";
            case 39: return "graphics/icon.jpg";
            case 40: return "graphics/icon.jpg";
            case 41: return "graphics/41.jpg";
            case 42: return "graphics/icon.jpg";
            case 43: return "graphics/icon.jpg";
            case 44: return "graphics/44.jpg";
            case 45: return "graphics/45.jpg";
            case 46: return "graphics/icon.jpg";
            case 47: return "graphics/icon.jpg";
            case 48: return "graphics/icon.jpg";
            case 49: return "graphics/icon.jpg";
            case 50: return "graphics/icon.jpg";
            case 51: return "graphics/icon.jpg";
            case 52: return "graphics/icon.jpg";
            case 53: return "graphics/icon.jpg";
            case 54: return "graphics/icon.jpg";
            default: return "graphics/icon.jpg";
        }
    }

    public static String switchIdForName(int id){
        switch(id){
            case 1: return "Unicorn";
            case 2: return "Magic Staff";
            case 3: return "Hydra";
            case 4: return "Basilisk";
            case 5: return "Warhorse";
            case 6: return "Dragon";
            case 7: return "Zeppelin";
            case 8: return "Warship";
            case 9: return "Bow";
            case 10: return "Keth Sword";
            case 11: return "Lord of Beasts";
            case 12: return "Collector";
            case 13: return "Necromant";
            case 14: return "Jester";
            case 15: return "Witch";
            case 16: return "Archmage";
            case 17: return "Princess";
            case 18: return "Commander";
            case 19: return "Queen";
            case 20: return "King";
            case 21: return "Empress";
            case 22: return "Swordswomen";
            case 23: return "Striders";
            case 24: return "Dwarf Infantry";
            case 25: return "Elven Bowmen";
            case 26: return "Cavalry";
            case 27: return "Keth Shield";
            case 28: return "Guard Rune";
            case 29: return "Crystal of Order";
            case 30: return "World Tree";
            case 31: return "Book of Spells";
            case 32: return "Fountain of Life";
            case 33: return "Great Flood";
            case 34: return "Elemental of Water";
            case 35: return "Swamp";
            case 36: return "Island";
            case 37: return "Elemental of Fire";
            case 38: return "Lightning";
            case 39: return "Candle";
            case 40: return "Forge";
            case 41: return "Conflagration";
            case 42: return "Belfry";
            case 43: return "Elemental of Earth";
            case 44: return "Cave";
            case 45: return "Mountain";
            case 46: return "Forest";
            case 47: return "Blizzard";
            case 48: return "Elemental of Air";
            case 49: return "Tornado";
            case 50: return "Storm";
            case 51: return "Smoke";
            case 52: return "Skinchanger";
            case 53: return "Doppleganger";
            case 54: return "Mirage";
            default: return "FAIL";
        }
    }

    public static ArrayList<String> switchType(Type type){
        ArrayList<String> arr = new ArrayList<>();
        switch(type){
            case CREATURE:  arr.add("35CA35");
                arr.add("Creature");
                break;
            case ARMY:      arr.add("19190E");
                arr.add("Army");
                break;
            case WEAPON:    arr.add("FCF842");
                arr.add("Weapon");
                break;
            case LEADER:    arr.add("B52F9B");
                arr.add("Leader");
                break;
            case WIZARD:    arr.add("DB3975");
                arr.add("Wizard");
                break;
            case ARTIFACT:  arr.add("FC8F42");
                arr.add("Artifact");
                break;
            case FLOOD:     arr.add("5D3BAD");
                arr.add("Flood");
                break;
            case EARTH:     arr.add("4B2600");
                arr.add("Earth");
                break;
            case FIRE:      arr.add("FC4242");
                arr.add("Fire");
                break;
            case WEATHER:   arr.add("2B8A9B");
                arr.add("Weather");
                break;
            case WILD:      arr.add("CBCBC0");
                arr.add("Wild");
                break;
            default:        arr.add("FFFFFF");
                arr.add("fail");
        }
        return arr;
    }

    public static String switchTypeForName(Type type){
        if (type == null) {
            System.out.println("Null pointer v switchTypeForName");
            return null;

        }
        switch(type){
            case ARMY: return "Army";
            case CREATURE: return "Creature";
            case LEADER: return "Leader";
            case WIZARD: return "Wizard";
            case FLOOD: return "Flood";
            case EARTH: return "Earth";
            case FIRE: return "Fire";
            case WEAPON: return "Weapon";
            case WEATHER: return "Weather";
            case ARTIFACT: return "Artifact";
            case WILD: return "Wild";
            default: return "FAIL";
        }
    }
    public static Type switchNameForType(String name){
        if (name == null) {
            System.out.println("Null pointer v switchNameForType");
            return Type.WILD;

        }
        switch(name){
            case "Army": return Type.ARMY ;
            case "Creature": return Type.CREATURE;
            case "Leader":  return Type.LEADER;
            case "Wizard": return Type.WIZARD;
            case "Flood": return Type.FLOOD;
            case "Earth": return Type.EARTH;
            case "Fire": return Type.FIRE;
            case "Weapon": return Type.WEAPON;
            case "Weather": return Type.WEATHER;
            case "Artifact": return Type.ARTIFACT;
            case "Wild": return Type.WILD;
            default: return Type.WILD;
        }
    }

    public static String switchCardNameForStringType(String name){
        if (name == null) {
            System.out.println("Null pointer v switchCardNameForStringType");
            return "UNKNOWN";

        }
        switch(name){
            case "Swordswomen": return "Army" ;
            case "Striders": return "Army" ;
            case "Dwarf Infantry": return "Army" ;
            case "Elven Bowmen": return "Army" ;
            case "Cavalry": return "Army" ;

            case "Unicorn": return "Creature";
            case "Basilisk": return "Creature";
            case "Dragon": return "Creature";
            case "Warhorse": return "Creature";
            case "Hydra": return "Creature";

            case "Princess":  return "Leader";
            case "Commander":  return "Leader";
            case "Queen":  return "Leader";
            case "Empress":  return "Leader";
            case "King":  return "Leader";

            case "Lord of Beasts": return "Wizard";
            case "Collector": return "Wizard";
            case "Jester": return "Wizard";
            case "Archmage": return "Wizard";
            case "Witch": return "Wizard";
            case "Necromant": return "Wizard";

            case "Fountain of Life": return "Flood";
            case "Great Flood": return "Flood";
            case "Swamp": return "Flood";
            case "Island": return "Flood";
            case "Elemental of Water": return "Flood";

            case "Belfry": return "Earth";
            case "Elemental of Earth": return "Earth";
            case "Mountain": return "Earth";
            case "Cave": return "Earth";
            case "Forest": return "Earth";

            case "Elemental of Fire": return "Fire";
            case "Conflagration": return "Fire";
            case "Forge": return "Fire";
            case "Candle": return "Fire";
            case "Lightning": return "Fire";

            case "Magic Staff": return "Weapon";
            case "Zeppelin": return "Weapon";
            case "Warship": return "Weapon";
            case "Bow": return "Weapon";
            case "Keth Sword": return "Weapon";

            case "Keth Shield": return "Artifact";
            case "Guard Rune": return "Artifact";
            case "Crystal of Order": return "Artifact";
            case "World Tree": return "Artifact";
            case "Book of Spells": return "Artifact";

            case "Elemental of Air": return "Weather";
            case "Smoke": return "Weather";
            case "Blizzard": return "Weather";
            case "Storm": return "Weather";
            case "Tornado": return "Weather";

            case "Skinchanger": return "Wild";
            case "Doppleganger": return "Wild";
            case "Mirage": return "Wild";
            default: return "UNKNOWN";
        }
    }


}
