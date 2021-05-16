package client;

import java.util.ArrayList;

public class BigSwitches {
    public static String switchImage(int id){
        switch(id){
            case 1: return "graphics/1.jpg";
            case 2: return "graphics/2.jpg";
            case 3: return "graphics/3.jpg";
            case 4: return "graphics/4.jpg";
            case 5: return "graphics/5.jpg";
            case 6: return "graphics/6.jpg";
            case 7: return "graphics/7.jpg";
            case 8: return "graphics/8.jpg";
            case 9: return "graphics/9.jpg";
            case 10: return "graphics/10.jpg";
            case 11: return "graphics/11.jpg";
            case 12: return "graphics/12.jpg";
            case 13: return "graphics/13.jpg";
            case 14: return "graphics/14.jpg";
            case 15: return "graphics/15.jpg";
            case 16: return "graphics/16.jpg";
            case 17: return "graphics/17.jpg";
            case 18: return "graphics/18.jpg";
            case 19: return "graphics/19.jpg";
            case 20: return "graphics/20.jpg";
            case 21: return "graphics/21.jpg";
            case 22: return "graphics/22.jpg";
            case 23: return "graphics/23.jpg";
            case 24: return "graphics/24.jpg";
            case 25: return "graphics/25.jpg";
            case 26: return "graphics/26.jpg";
            case 27: return "graphics/27.jpg";
            case 28: return "graphics/28.jpg";
            case 29: return "graphics/29.jpg";
            case 30: return "graphics/30.jpg";
            case 31: return "graphics/31.jpg";
            case 32: return "graphics/32.jpg";
            case 33: return "graphics/33.jpg";
            case 34: return "graphics/34.jpg";
            case 35: return "graphics/35.jpg";
            case 36: return "graphics/36.jpg";
            case 37: return "graphics/37.jpg";
            case 38: return "graphics/38.jpg";
            case 39: return "graphics/39.jpg";
            case 40: return "graphics/40.jpg";
            case 41: return "graphics/41.jpg";
            case 42: return "graphics/42.jpg";
            case 43: return "graphics/43.jpg";
            case 44: return "graphics/44.jpg";
            case 45: return "graphics/45.jpg";
            case 46: return "graphics/46.jpg";
            case 47: return "graphics/47.jpg";
            case 48: return "graphics/48.jpg";
            case 49: return "graphics/49.jpg";
            case 50: return "graphics/50.jpg";
            case 51: return "graphics/51.jpg";
            case 52: return "graphics/52.jpg";
            case 53: return "graphics/53.jpg";
            case 54: return "graphics/54.jpg";
            default: return "graphics/icon.jpg";
        }
    }

    public static String switchIdForName(int id){
        switch(id){
            case 1: return "Unicorn";
            case 2: return "Hydra";
            case 3: return "Basilisk";
            case 4: return "Warhorse";
            case 5: return "Dragon";
            case 6: return "Magic Wand";
            case 7: return "Zeppelin";
            case 8: return "Warship";
            case 9: return "Bow";
            case 10: return "Sword";
            case 11: return "Beastmaster";
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
            case 22: return "Knights";
            case 23: return "Striders";
            case 24: return "Dwarf Infantry";
            case 25: return "Archers";
            case 26: return "Cavalry";
            case 27: return "Shield";
            case 28: return "Guard Rune";
            case 29: return "Gem of Order";
            case 30: return "World Tree";
            case 31: return "Spellbook";
            case 32: return "Fountain of Life";
            case 33: return "Great Flood";
            case 34: return "Water Elemental";
            case 35: return "Swamp";
            case 36: return "Island";
            case 37: return "Fire Elemental";
            case 38: return "Lightning";
            case 39: return "Candle";
            case 40: return "Forge";
            case 41: return "Wildfire";
            case 42: return "Belfry";
            case 43: return "Earth Elemental";
            case 44: return "Cave";
            case 45: return "Mountain";
            case 46: return "Forest";
            case 47: return "Blizzard";
            case 48: return "Air Elemental";
            case 49: return "Tornado";
            case 50: return "Storm";
            case 51: return "Smoke";
            case 52: return "Skinchanger";
            case 53: return "Mirage";
            case 54: return "Doppleganger";
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
            default: return Type.WILD;
        }
    }

    public static String switchCardNameForStringType(String name){
        if (name == null) {
            System.out.println("Null pointer v switchCardNameForStringType");
            return "UNKNOWN";

        }
        switch(name){
            case "Knights":
            case "Striders":
            case "Dwarf Infantry":
            case "Archers":
            case "Cavalry":
                return "Army" ;

            case "Unicorn":
            case "Dragon":
            case "Basilisk":
            case "Warhorse":
            case "Hydra":
                return "Creature";

            case "Princess":
            case "Commander":
            case "Queen":
            case "Empress":
            case "King":
                return "Leader";

            case "Beastmaster":
            case "Collector":
            case "Jester":
            case "Archmage":
            case "Witch":
            case "Necromant":
                return "Wizard";

            case "Fountain of Life":
            case "Great Flood":
            case "Swamp":
            case "Island":
            case "Water Elemental":
                return "Flood";

            case "Belfry":
            case "Earth Elemental":
            case "Mountain":
            case "Cave":
            case "Forest": return "Earth";

            case "Fire Elemental":
            case "Wildfire":
            case "Forge":
            case "Candle":
            case "Lightning": return "Fire";

            case "Magic Wand":
            case "Zeppelin":
            case "Warship":
            case "Bow":
            case "Sword":
                return "Weapon";

            case "Shield":
            case "Guard Rune":
            case "Gem of Order":
            case "World Tree":
            case "Spellbook": return "Artifact";

            case "Air Elemental":
            case "Smoke":
            case "Blizzard":
            case "Storm":
            case "Tornado":
                return "Weather";

            case "Skinchanger":
            case "Doppleganger":
            case "Mirage":
                return "Wild";
            default: return "UNKNOWN";
        }
    }


}
