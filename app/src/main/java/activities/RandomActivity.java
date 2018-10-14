package activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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


        loadData();
        initiate();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);




        PEingabe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (PEingabe.getRight() - PEingabe.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
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
                public void onClick(View view) {

                    startActivity(i);
                }
            });



        Shuffle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(personenliste.size()>= 2)
                {
                    myDialog.setContentView(R.layout.shuffle_popup);
                    RelativeLayout frame = myDialog.findViewById(R.id.popupFrame);
                    TextView mPerson = myDialog.findViewById(R.id.popupPerson);
                    mPerson.setText(shuffle(personenliste, personenliste.size()));
                    Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();

                }
            }
        });

        PListe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                adapter.remove(adapter.getItem(i));
                return true;
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
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

       if (savedInstanceState != null) {
            // Restore value of members from saved state
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
        AddButton = this.findViewById(R.id.imageButton);
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
    }

    public void ShowPopup(View v)
    {

    }


    public void onResume()
    {
        super.onResume();
     loadData();
     TextAdapter.notifyDataSetChanged();
     TextAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,autoTextArray);
     PEingabe.setAdapter(TextAdapter);
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
