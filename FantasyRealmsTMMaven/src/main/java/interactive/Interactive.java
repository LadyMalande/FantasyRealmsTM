package interactive;

import sample.BigSwitches;
import sample.Type;

import java.io.Serializable;
import java.util.ArrayList;

public class Interactive{
int priority;
    public String giveListOfTypesWithSeparator(ArrayList<Type> types, String separator){
        String listtypes = "";
        boolean first = true;
        for(Type type: types){
            if(!first){
                listtypes += separator;
            }
            listtypes += BigSwitches.switchTypeForName(type);
            first = false;
        }
        return listtypes;
    }
}
