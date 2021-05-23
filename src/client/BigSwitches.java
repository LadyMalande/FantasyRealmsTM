package client;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class for getting static data in exchange for other static data
 * @author Tereza Miklóšová
 */
public class BigSwitches {
    /**
     * Method for getting suitable image for its id card.
     * @param id The id of the card that demands image.
     * @return Address for loading the image from sources.
     */
    public static String switchImage(int id){
        if(id <55 && id > 0) {
            return "graphics/" + id + ".jpg";
        } else return "graphics/1.jpg";
    }

    /**
     * Method returns name given for selected locale and id of the card.
     * @param id Id of the card in question.
     * @param locale Locale for the language in which we need the name.
     * @return Localized name for the card with given id.
     */
    public static String switchIdForName(int id, Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("client.CardNames", locale);
        switch(id){
            case 1: return  rb.getString("unicorn");
            case 2: return rb.getString("hydra");
            case 3: return rb.getString("basilisk");
            case 4: return rb.getString("warhorse");
            case 5: return rb.getString("dragon");
            case 6: return rb.getString("wand");
            case 7: return rb.getString("dirigible");
            case 8: return rb.getString("warship");
            case 9: return rb.getString("bow");
            case 10: return rb.getString("sword");
            case 11: return rb.getString("beastmaster");
            case 12: return rb.getString("collector");
            case 13: return rb.getString("necromancer");
            case 14: return rb.getString("jester");
            case 15: return rb.getString("enchantress");
            case 16: return rb.getString("warlock");
            case 17: return rb.getString("princess");
            case 18: return rb.getString("warlord");
            case 19: return rb.getString("queen");
            case 20: return rb.getString("king");
            case 21: return rb.getString("empress");
            case 22: return rb.getString("knights");
            case 23: return rb.getString("rangers");
            case 24: return rb.getString("dwarfs");
            case 25: return rb.getString("archers");
            case 26: return rb.getString("cavalry");
            case 27: return rb.getString("shield");
            case 28: return rb.getString("rune");
            case 29: return rb.getString("gem");
            case 30: return rb.getString("tree");
            case 31: return rb.getString("book");
            case 32: return rb.getString("fountain");
            case 33: return rb.getString("greatflood");
            case 34: return rb.getString("welemental");
            case 35: return rb.getString("swamp");
            case 36: return rb.getString("island");
            case 37: return rb.getString("felemental");
            case 38: return rb.getString("lightning");
            case 39: return rb.getString("candle");
            case 40: return rb.getString("forge");
            case 41: return rb.getString("wildfire");
            case 42: return rb.getString("belfry");
            case 43: return rb.getString("eelemental");
            case 44: return rb.getString("cavern");
            case 45: return rb.getString("mountain");
            case 46: return rb.getString("forest");
            case 47: return rb.getString("blizzard");
            case 48: return rb.getString("aelemental");
            case 49: return rb.getString("tornado");
            case 50: return rb.getString("storm");
            case 51: return rb.getString("smoke");
            case 52: return rb.getString("shapeshifter");
            case 53: return rb.getString("mirage");
            case 54: return rb.getString("doppleganger");

            default: return "FAIL";
        }
    }

    /**
     * Method returning name of the card in English for given id.
     * @param id Id if the card in question.
     * @return English name for the card with given id.
     */
    public static String switchIdForSimplifiedName(int id){

        switch(id){
            case 1: return "unicorn";
            case 2: return "hydra";
            case 3: return "basilisk";
            case 4: return "warhorse";
            case 5: return "dragon";
            case 6: return "wand";
            case 7: return "dirigible";
            case 8: return "warship";
            case 9: return "bow";
            case 10: return "sword";
            case 11: return "beastmaster";
            case 12: return "collector";
            case 13: return "necromancer";
            case 14: return "jester";
            case 15: return "enchantress";
            case 16: return "warlock";
            case 17: return "princess";
            case 18: return "warlord";
            case 19: return "queen";
            case 20: return "king";
            case 21: return "empress";
            case 22: return "knights";
            case 23: return "rangers";
            case 24: return "dwarfs";
            case 25: return "archers";
            case 26: return "cavalry";
            case 27: return "shield";
            case 28: return "rune";
            case 29: return "gem";
            case 30: return "tree";
            case 31: return "book";
            case 32: return "fountain";
            case 33: return "greatflood";
            case 34: return "welemental";
            case 35: return "swamp";
            case 36: return "island";
            case 37: return "felemental";
            case 38: return "lightning";
            case 39: return "candle";
            case 40: return "forge";
            case 41: return "wildfire";
            case 42: return "belfry";
            case 43: return "eelemental";
            case 44: return "cavern";
            case 45: return "mountain";
            case 46: return "forest";
            case 47: return "blizzard";
            case 48: return "aelemental";
            case 49: return "tornado";
            case 50: return "storm";
            case 51: return "smoke";
            case 52: return "shapeshifter";
            case 53: return "mirage";
            case 54: return "doppleganger";
            default: return "FAIL";
        }
    }


    /**
     * Method that returns array with color code and localized name of the color for
     * building the card graphics.
     * @param type Type that we want the color and localized name for.
     * @param locale Locale for wanted language of the type name.
     * @return ArrayList with string of the color code and localized name of type of the card.
     */
    public static ArrayList<String> switchType(Type type, Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("client.CardTypes", locale);
        ArrayList<String> arr = new ArrayList<>();
        switch(type){
            case BEAST:  arr.add("35CA35");
                arr.add(rb.getString("beast"));
                break;
            case ARMY:      arr.add("19190E");
                arr.add(rb.getString("army"));
                break;
            case WEAPON:    arr.add("FCF842");
                arr.add(rb.getString("weapon"));
                break;
            case LEADER:    arr.add("B52F9B");
                arr.add(rb.getString("leader"));
                break;
            case WIZARD:    arr.add("DB3975");
                arr.add(rb.getString("wizard"));
                break;
            case ARTIFACT:  arr.add("FC8F42");
                arr.add(rb.getString("artifact"));
                break;
            case FLOOD:     arr.add("5D3BAD");
                arr.add(rb.getString("flood"));
                break;
            case LAND:     arr.add("4B2600");
                arr.add(rb.getString("land"));
                break;
            case FLAME:      arr.add("FC4242");
                arr.add(rb.getString("flame"));
                break;
            case WEATHER:   arr.add("2B8A9B");
                arr.add(rb.getString("weather"));
                break;
            case WILD:      arr.add("CBCBC0");
                arr.add(rb.getString("wild"));
                break;
            default:        arr.add("FFFFFF");
                arr.add("fail");
        }
        return arr;
    }


    /**
     * Method that returns English version of string of the demanded type.
     * @param type Type we want to translate to string in English.
     * @return English name of the type.
     */
    public static String switchTypeForName(Type type){
        if (type == null) {
            System.out.println("Null pointer v switchTypeForName");
            return null;

        }
        switch(type){
            case ARMY: return "Army";
            case BEAST: return "Creature";
            case LEADER: return "Leader";
            case WIZARD: return "Wizard";
            case FLOOD: return "Flood";
            case LAND: return "Earth";
            case FLAME: return "Fire";
            case WEAPON: return "Weapon";
            case WEATHER: return "Weather";
            case ARTIFACT: return "Artifact";
            case WILD: return "Wild";
            default: return "FAIL";
        }
    }

    /**
     * Method returns localized name of the type.
     * @param type Type we want to translate to language by Locale.
     * @param locale Locale for language we want the type translated to.
     * @return Localized name of the type.
     */
    public static String switchTypeForName(Type type , Locale locale){
        if (type == null) {
            System.out.println("Null pointer v switchTypeForName");
            return null;

        }
        ResourceBundle rb = ResourceBundle.getBundle("client.CardTypes", locale);
        switch(type){
            case ARMY: return rb.getString("army");
            case BEAST: return rb.getString("beast");
            case LEADER: return rb.getString("leader");
            case WIZARD: return rb.getString("wizard");
            case FLOOD: return rb.getString("flood");
            case LAND: return rb.getString("land");
            case FLAME: return rb.getString("flame");
            case WEAPON: return rb.getString("weapon");
            case WEATHER: return rb.getString("weather");
            case ARTIFACT: return rb.getString("artifact");
            case WILD: return rb.getString("wild");
            default: return "FAIL";
        }
    }

    /**
     * Method returns enum type of the type given in localized name.
     * @param name Localized name of the type.
     * @return Enum Type by the given string in parameter.
     */
    public static Type switchNameForType(String name){
        if (name == null) {
            System.out.println("Null pointer v switchNameForType");
            return Type.WILD;

        }
        switch(name){
            case "Army":
            case "Armáda": return Type.ARMY ;
            case "Beast":
            case "Tvor":   return Type.BEAST;
            case "Leader":
            case "Vůdce":   return Type.LEADER;
            case "Wizard":
            case "Čaroděj":   return Type.WIZARD;
            case "Flood":
            case "Potopa":   return Type.FLOOD;
            case "Land":
            case "Země":   return Type.LAND;
            case "Flame":
            case "Oheň":   return Type.FLAME;
            case "Weapon":
            case "Zbraň":   return Type.WEAPON;
            case "Weather":
            case "Počasí":   return Type.WEATHER;
            case "Artifact":
            case "Artefakt":   return Type.ARTIFACT;
            default: return Type.WILD;
        }
    }

    /**
     * Method returns localized name of the type of the demanded name of card.
     * @param name Localized name of the card.
     * @param locale Locale for language we want the type name in.
     * @return Localized type name by localized card name.
     */
    public static String switchCardNameForStringType(String name, Locale locale){
        if (name == null) {
            System.out.println("Null pointer v switchCardNameForStringType(String name, Locale locale)");
            return "UNKNOWN";

        }
        ResourceBundle rb = ResourceBundle.getBundle("client.CardTypes", locale);
        switch(name){
            case "Knights":
            case "Rangers":
            case "Dwarvish Infantry":
            case "Elven Archers":
            case "Light Cavalry":
            case "Rytíři":
            case "Hraničáři":
            case "Trpasličí pěchota":
            case "Lučištníci":
            case "Těžká jízda":
                return rb.getString("army");

            case "Unicorn":
            case "Dragon":
            case "Basilisk":
            case "Warhorse":
            case "Hydra":
            case "Jednorožec":
            case "Drak":
            case "Bazilišek":
            case "Válečný oř":
                return rb.getString("beast");

            case "Princess":
            case "Warlord":
            case "Queen":
            case "Empress":
            case "King":
            case "Princezna":
            case "Velitel":
            case "Královna":
            case "Král":
            case "Císařovna":
                return rb.getString("leader");

            case "Beastmaster":
            case "Collector":
            case "Jester":
            case "Warlock Lord":
            case "Enchantress":
            case "Necromancer":
            case "Sběratel":
            case "Pán šelem":
            case "Šašek":
            case "Nejvyšší mág":
            case "Kouzelnice":
            case "Nekromant":
                return rb.getString("wizard");

            case "Fountain of Life":
            case "Great Flood":
            case "Swamp":
            case "Island":
            case "Water Elemental":
            case "Fontána života":
            case "Stoletá voda":
            case "Bažina":
            case "Ostrov":
            case "Elementál vody":
                return rb.getString("flood");

            case "Bell Tower":
            case "Earth Elemental":
            case "Mountain":
            case "Cavern":
            case "Forest":
            case "Zvonice":
            case "Elementál země":
            case "Hora":
            case "Jeskyně":
            case "Les":
                return rb.getString("land");

            case "Fire Elemental":
            case "Wildfire":
            case "Forge":
            case "Candle":
            case "Lightning":
            case "Elementál ohně":
            case "Požár":
            case "Kovárna":
            case "Svíčka":
            case "Blesk":
                return rb.getString("flame");
            case "Magic Wand":
            case "War Dirigible":
            case "Warship":
            case "Elven Longbow":
            case "Sword":
            case "Magická hůl":
            case "Vzducholoď":
            case "Válečná loď":
            case "Elfský luk":
            case "Meč":
                return rb.getString("weapon");

            case "Shield":
            case "Protection Rune":
            case "Gem of Order":
            case "World Tree":
            case "Book of Changes":
            case "Štít":
            case "Ochranná runa":
            case "Krystal řádu":
            case "Strom světa":
            case "Kniha proměn":
                return rb.getString("artifact");

            case "Air Elemental":
            case "Smoke":
            case "Blizzard":
            case "Rainstorm":
            case "Whirlwind":
            case "Elementál vzduchu":
            case "Kouř":
            case "Sněhová vánice":
            case "Tornádo":
            case "Bouře":
                return rb.getString("weather");

            case "Shapeshifter":
            case "Dopplegänger":
            case "Mirage":
            case "Měňavec":
            case "Dvojník":
            case "Přelud":
                return rb.getString("wild");
            default: return "UNKNOWN";
        }
    }






}
