package activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.Objects;
import java.util.Random;

import wazzup.shuffler.Person;
import wazzup.shuffler.R;


public class RandomActivity extends AppCompatActivity{

    private AutoCompleteTextView PEingabe;
    private Button Pspeichern;
    private Button Shuffle;
    private ImageButton AddButton;
    private ListView PListe;
    private ArrayList<String> personenliste;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> TextAdapter;
    private ArrayList<String> autoTextArray;
    public  final String PREFERENCE_KEY ="pref";
    private Toolbar rToolbar;
    private Dialog myDialog;
    private final String PERSON_KEY = "persons";
    private boolean THEME_MODE;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";
    private Context context;
    private Button addList;
    private Button clearButton;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        loadThemeMode();
        if(THEME_MODE) //DarkMode
            setTheme(R.style.darkmode);
        else //DayMode
            setTheme(R.style.standardTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            personenliste=savedInstanceState.getStringArrayList(PERSON_KEY);

        }
        else
        {
            personenliste =new ArrayList<String>();
        }


        loadData(); //Gibt die gespeicherten Preferences zurück
        initiate(); //Initialisiert Buttons etc.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        if(THEME_MODE)//DarkMode
        {

            PEingabe.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_dark));
            PEingabe.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_dark));

            Pspeichern.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
            Shuffle.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
        }
        else //DayMode
        {
            PEingabe.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
            PEingabe.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));

            Pspeichern.setBackground(getResources().getDrawable(R.drawable.round_button));
            Shuffle.setBackground(getResources().getDrawable(R.drawable.round_button));
        }


        PEingabe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { //
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (PEingabe.getRight() - PEingabe.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        PEingabe.getText().clear();
                        return true;
                    }
                }
                return false;
            }
        });

        Pspeichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(PEingabe.getText().toString().length() > 0)
                {
                    String name = PEingabe.getText().toString();
                    adapter.add(name);
                }

            }
        });




            final Intent i = new Intent(this, Save_Activity.class);
            AddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //Startet Save_Activity

                    startActivity(i);
                }
            });



        Shuffle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            { // Wählt random Item aus der Liste
                if(personenliste.size()>= 2)
                {
                    myDialog.setContentView(R.layout.shuffle_popup);
                    RelativeLayout frame = myDialog.findViewById(R.id.popupFrame);
                    TextView mPerson = myDialog.findViewById(R.id.popupPerson);
                    mPerson.setText(shuffle(personenliste, personenliste.size()));
                    Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.getWindow().setWindowAnimations(R.style.DialogScale);
                    myDialog.show();

                }
            }
        });

        PListe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            { //Löscht Item aus der Liste bei gedrückt halten
                adapter.remove(adapter.getItem(i));
                return true;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Löscht alles aus der  Liste
                personenliste.clear();
                adapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(PERSON_KEY,personenliste);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

       if (savedInstanceState != null) {

            personenliste=savedInstanceState.getStringArrayList(PERSON_KEY);
        }
        else
        {
            personenliste =new ArrayList<String>();
        }

    }


    private void loadData() //Gibt die gespeicherten preferences zurück
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Preferences_name),Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(PREFERENCE_KEY,null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
       autoTextArray = gson.fromJson(json,type);

        if(autoTextArray == null)
        {
            autoTextArray = new ArrayList<>();
        }
    }


    private String shuffle(ArrayList<String> namen , int size)
    {
        Random rand = new Random();
        int random = rand.nextInt(size);
        return namen.get(random).toString();
    }

    private void initiate()
    {
        rToolbar = findViewById(R.id.randomToolbar);
        PEingabe= findViewById(R.id.randompersonfield);
        Pspeichern = findViewById(R.id.r_speichern);
        Shuffle = findViewById(R.id.shuffle);
        PListe = findViewById(R.id.personen);
        addList = findViewById(R.id.addListButton2);
        AddButton = this.findViewById(R.id.imageButton);
        clearButton = findViewById(R.id.clearButton2);
        personenliste = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,personenliste);
        TextAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,autoTextArray);
        PListe.setAdapter(adapter);
        PEingabe.setAdapter(TextAdapter);
        TextAdapter.setNotifyOnChange(true);
        myDialog = new Dialog(RandomActivity.this);




        setSupportActionBar(rToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        AddList();
    }

    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }


    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private boolean [] checkedItems;
    private String [] listItems;

    public void AddList() //Startet Dialog mit der man Items auswählen kann und sie der Liste hinzufügen kann
    {

        listItems = new String[autoTextArray.size()]; //Array für die Items

        for(int i = 0; i<autoTextArray.size();i++)
        {
            listItems[i] = autoTextArray.get(i);
        }

        checkedItems = new boolean[listItems.length]; //Array für die Items die ausgewählt wurden

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder;


                if(THEME_MODE) {

                    mBuilder = new AlertDialog.Builder(RandomActivity.this,R.style.darkmode);

                }
                else
                {
                    mBuilder = new AlertDialog.Builder(RandomActivity.this,R.style.standardTheme);
                }

                mBuilder.setTitle(R.string.füge_der_Liste_hinzu);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) { //Checked items werden der Liste hinzugefügt oder gelöscht
                        if(isChecked)
                        {
                            if(!mUserItems.contains(position))
                            {
                                mUserItems.add(position);

                            }

                        }
                        else if (mUserItems.contains(position))
                        {

                            mUserItems.remove(mUserItems.indexOf(position));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) { //Bestätigen Button für den Dialog


                        for (int i = 0; i < mUserItems.size(); i++)
                        {
                            if(!personenliste.contains(listItems[mUserItems.get(i)]))
                                personenliste.add(listItems[mUserItems.get(i)]);
                        }

                        adapter.notifyDataSetChanged();

                    }
                });

                mBuilder.setNegativeButton(R.string.zurück, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                mBuilder.setNeutralButton(R.string.lösche_alles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i = 0; i< checkedItems.length;i++)
                        {
                            checkedItems[i] = false;
                            mUserItems.clear();

                        }
                    }
                });

                final AlertDialog mDialog = mBuilder.create();



                mDialog.show(); //Dialog wird ausgeführt






            }
        });
    }


    public void onResume()
    {
        super.onResume();
     loadData();
     TextAdapter.notifyDataSetChanged();
     TextAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,autoTextArray);
     PEingabe.setAdapter(TextAdapter);
     AddList();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        loadData();
        TextAdapter.notifyDataSetChanged();


    }

    @Override
    public void onStop() {
        super.onStop();
        loadData();



    }
}
