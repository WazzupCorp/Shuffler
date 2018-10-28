package activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import wazzup.shuffler.Gruppe;
import wazzup.shuffler.R;
import wazzup.shuffler.TestShow;

public class GruppenActivity extends AppCompatActivity {

    private ImageButton Addbutton;
    private Spinner gruppenSpinner;
    private AutoCompleteTextView PEingabe2;
    private ArrayList<Integer> SpinnerArray;
    private ListView PListe2;
    private ArrayList<String> personenliste2 = new ArrayList<>();
    private ArrayList<String> autoTextArray;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> TextAdapter;
    private Button Pspeichern2;
    private  Button Shuffle2;
    private int personen;
    private int groupnumber;
    private int groupsize;
    public static ArrayList<Gruppe> ShuffledGruppe;
    private static final String PREFERENCE_KEY = "name";
    private Toolbar gToolbar;
    private String PERSON_KEY2 = "persons2";
    private ArrayAdapter<Integer> Sadapter;
    private ArrayList<Gruppe> GruppenListe;
    private boolean THEME_MODE;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

            loadThemeMode();
        if(THEME_MODE)
            setTheme(R.style.darkmode);
        else
            setTheme(R.style.standardTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppen);



        loadData();
        initiate();

        if(THEME_MODE)
        {
            PEingabe2.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_dark));
            PEingabe2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_dark));
            //PEingabe2.setTextColor(getResources().getColor(R.color.black));
            gruppenSpinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_dark));

            Pspeichern2.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
            Shuffle2.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
        }
        else
        {
            PEingabe2.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
            PEingabe2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            gruppenSpinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));

            Pspeichern2.setBackground(getResources().getDrawable(R.drawable.round_button));
            Shuffle2.setBackground(getResources().getDrawable(R.drawable.round_button));

        }


        PEingabe2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (PEingabe2.getRight() - PEingabe2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        PEingabe2.getText().clear();

                        return true;

                    }
                }
                return false;
            }
        });


        Pspeichern2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( PEingabe2.getText().toString().length() > 0) {
                    String name =  PEingabe2.getText().toString();

                    //adapter2.add(name);
                    personenliste2.add(name);
                    adapter2.notifyDataSetChanged();
                }
            }
        });

        Shuffle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupShuffler(personenliste2);
            }



        });

        PListe2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter2.remove(adapter2.getItem(i));
                return true;
            }
        });

        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GruppenActivity.this,Save_Activity2.class);
                startActivity(i);
            }
        });
    }

    private void initiate()
    {

        //Spinner für Gruppengröße
        SpinnerArray = new ArrayList<>();
        SpinnerArray.add(2);
        SpinnerArray.add(3);
        SpinnerArray.add(4);
        SpinnerArray.add(5);
        SpinnerArray.add(6);
        SpinnerArray.add(7);
        SpinnerArray.add(8);
        SpinnerArray.add(9);
        SpinnerArray.add(10);

        //Layout bezogene Widgets
        Addbutton = findViewById(R.id.imageButton2);
        PEingabe2 = findViewById(R.id.randompersonfield2);
        gruppenSpinner = findViewById(R.id.gruppenSpinner);
        PListe2 =findViewById(R.id.personen2);
        Shuffle2 = findViewById(R.id.shuffle2);
        Pspeichern2 = findViewById(R.id.speichern2);
        gToolbar= findViewById(R.id.groupToolbar);

        //Adapter Initialisieren
        Sadapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,SpinnerArray); //Adapter für Spinner
        TextAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,autoTextArray); //Adapter für AutoText
        adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,personenliste2); //Adapter für ListView

        //Adapter setzen
        gruppenSpinner.setAdapter(Sadapter);
        PListe2.setAdapter(adapter2);

        //Adapter Eigenschaften setzen
        TextAdapter.setNotifyOnChange(true);
        PEingabe2.setAdapter(TextAdapter);
        Sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        setSupportActionBar(gToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(PERSON_KEY2,personenliste2);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.



    }


    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }

    private void loadData() //Gibt die gespeicherten preferences zurück
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Names_name),Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(PREFERENCE_KEY,null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        autoTextArray = gson.fromJson(json,type);

        if(autoTextArray == null)
        {
            autoTextArray = new ArrayList<>();
        }
    }

    private void groupShuffler(ArrayList<String> p)
    {
        personen = personenliste2.size(); //Anzahl der Personen
        groupnumber = gruppenSpinner.getSelectedItemPosition()+2; //Gruppenanzahl
        int gruppennummer = 1; //Nummer der aktuellen Gruppe
        ShuffledGruppe = new ArrayList<Gruppe>();  //Gruppenliste mit Zufälligen Personen
        groupsize = personen / groupnumber;

        if(personenliste2.size()>= groupnumber) {
            if (personenliste2.size() % groupnumber == 0) // Wenn gleichgroße Gruppen entstehen können
            {
                GruppenListe = new ArrayList<Gruppe>(); //Liste der Gruppen
                for (int i = 0; i < groupnumber; i++) {
                    ArrayList<String> s = new ArrayList<String>();
                    GruppenListe.add(new Gruppe(groupsize, gruppennummer, s));
                    gruppennummer++;
                }
                //Toast.makeText(GruppenActivity.this,String.valueOf(GruppenListe.size()),Toast.LENGTH_SHORT).show();
                int nextgroup = 0;
                ArrayList<String>personcopy = new ArrayList<>(personenliste2);
                while(!personcopy.isEmpty()){

                    Gruppe aktuelleGruppe = GruppenListe.get(nextgroup); // Aktuelle Gruppe
                    int random = getRandom(personcopy.size());
                    String aktuellePerson;
                    if(personcopy.size()>1) {
                        aktuellePerson = personcopy.get(random);
                    }
                    else
                    {
                        aktuellePerson = personcopy.get(0);
                    }



                    if (!aktuelleGruppe.isFull()) {
                        aktuelleGruppe.setPerson(aktuellePerson);
                        personcopy.remove(aktuellePerson);
                        if(aktuelleGruppe.isFull())
                        {
                            ShuffledGruppe.add(aktuelleGruppe);
                            nextgroup++;
                        }
                    }


                }

//return ShuffledGruppe;

                Intent i = new Intent(this, TestShow.class);
                startActivity(i);
                adapter2.notifyDataSetChanged();


            } else if(personenliste2.size() % groupnumber != 0)
            {
                GruppenListe = new ArrayList<Gruppe>(); //Liste der Gruppen
                int überschuss = personen % groupnumber;
                for (int i = 0; i < groupnumber; i++) {
                    if (überschuss > 0) {
                        ArrayList<String> s = new ArrayList<String>();
                        GruppenListe.add(new Gruppe(groupsize + 1, gruppennummer, s));
                        gruppennummer++;
                        überschuss--;
                    }
                    else
                    {
                        ArrayList<String> s = new ArrayList<String>();

                        GruppenListe.add(new Gruppe(groupsize, gruppennummer, s));
                        gruppennummer++;
                    }
                }
                //Toast.makeText(GruppenActivity.this,String.valueOf(GruppenListe.size()),Toast.LENGTH_SHORT).show();
                int nextgroup = 0;
                ArrayList<String>personcopy = new ArrayList<>(personenliste2);
                while(!personcopy.isEmpty()){

                    Gruppe aktuelleGruppe = GruppenListe.get(nextgroup); // Aktuelle Gruppe
                    int random = getRandom(personcopy.size());
                    String aktuellePerson;
                    if(personcopy.size()>1) {
                        aktuellePerson = personcopy.get(random);
                    }
                    else
                    {
                        aktuellePerson = personcopy.get(0);
                    }



                    if (!aktuelleGruppe.isFull()) {
                        aktuelleGruppe.setPerson(aktuellePerson);
                        personcopy.remove(aktuellePerson);
                        if(aktuelleGruppe.isFull())
                        {
                            ShuffledGruppe.add(aktuelleGruppe);
                            nextgroup++;
                        }
                    }
                    else if(!personcopy.isEmpty()){
                        int r = getRandom(ShuffledGruppe.size());
                        ShuffledGruppe.get(random).getPersonen().add(personcopy.get(0));

                    }


                }
                //return ShuffledGruppe;


                Intent i = new Intent(this, TestShow.class);
                startActivity(i);
            }
        }
        // return ShuffledGruppe;
    }

    private int getRandom(int size)
    {
        Random rand = new Random();
        int random = rand.nextInt(size);
        return random;
    }




    public void onResume()
    {
        super.onResume();
        loadData();
        TextAdapter.notifyDataSetChanged();
        TextAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,autoTextArray);
        PEingabe2.setAdapter(TextAdapter);
    }



}
