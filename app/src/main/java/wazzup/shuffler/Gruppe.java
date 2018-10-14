package wazzup.shuffler;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Daniel on 27.05.2018.
 */

public class Gruppe {

    int größe;
    int gruppennummer;
    ArrayList<String> personen;
    public Gruppe(int g , int gn , ArrayList<String> p){
        größe = g;
        gruppennummer = gn;
        personen = p;
    }



    public int getGröße()
    {
        return this.größe;
    }

    public int getGruppennummer()
    {
        return this.gruppennummer;
    }

    public ArrayList getPersonen()
    {
        return this.personen;
    }


    public boolean isFull()
    {
       return getGröße() == 0;

    }
    public void setPerson(String p)
    {

           this.personen.add(p);
           this.einPlatzweniger();

    }
    public String toString()
    {

        String s = "";
        for(int i = 0 ; i <getPersonen().size();i++) {
            s += getPersonen().get(0) + " ,";
        }

        return s;
    }


    public void einPlatzweniger()
    {
        this.größe--;
    }

}
