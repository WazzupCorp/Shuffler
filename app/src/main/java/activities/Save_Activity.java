package activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import wazzup.shuffler.R;

public class Save_Activity extends AppCompatActivity {
    private ArrayList<String> preferenceArray;
    private RecyclerView SaveActivityList;
    private Button SaveButton;
    private Button SortButton;
    private EditText pref;
    private Save_Adapter SaveActivityListAdapter;
    private RecyclerView.LayoutManager SaveActivityListManager;
    private String[] mDataset;
    private static String FILE_NAME = "preferences.txt";
    public static final String PREFERENCE_KEY ="pref";
    private ImageView deleteImage;
    private AdapterView.OnItemClickListener itemClickListener;
    private Toolbar sToolbar;
    private boolean THEME_MODE;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadThemeMode();
        if(THEME_MODE)
            setTheme(R.style.darkmode);
        else
            setTheme(R.style.standardTheme);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_activity);
        loadData();
        buildRecyclerView();
        setButtons();




        if(THEME_MODE)
        {
            pref.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_dark));

            SaveButton.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
            SortButton.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
        }
        else
        {
           pref.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));

            SaveButton.setBackground(getResources().getDrawable(R.drawable.round_button));
            SortButton.setBackground(getResources().getDrawable(R.drawable.round_button));
        }


    }

    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }

    public void insertItem(String s)
    {
    preferenceArray.add(s);
    }

    public void removeItem(int position)
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Preferences_name),MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        Gson gson = new Gson();
        String json = sharedPref.getString(PREFERENCE_KEY,null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        preferenceArray = gson.fromJson(json,type);
        preferenceArray.remove(position);
        SaveData();
        buildRecyclerView();

    }


    private void buildRecyclerView()
    {
        SaveActivityList = findViewById(R.id.actionlist);
        SaveActivityList.setHasFixedSize(true);
        SaveActivityListManager = new LinearLayoutManager(Save_Activity.this);
        SaveActivityListAdapter = new Save_Adapter(Save_Activity.this,preferenceArray);
        sToolbar = findViewById(R.id.saveToolbar);
        SaveActivityList.setLayoutManager(SaveActivityListManager);
        SaveActivityList.setAdapter(SaveActivityListAdapter);
        deleteImage = findViewById(R.id.image_delete);
        SaveButton = findViewById(R.id.RandomSave);
        SortButton = findViewById(R.id.RandomDelete);
        pref = findViewById(R.id.preferences);

        setSupportActionBar(sToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SaveActivityListAdapter.setOnItemClickListener(new Save_Adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
removeItem(position);
            }
        });


    }

    public void SaveData()
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Preferences_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(preferenceArray);
        editor.putString(PREFERENCE_KEY,json);
        editor.commit();
    }

    public void loadData() //Gibt die gespeicherten preferences zurück
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Preferences_name),Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(PREFERENCE_KEY,null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        preferenceArray = gson.fromJson(json,type);
        if(preferenceArray == null)
        {
            preferenceArray = new ArrayList<>();
        }
    }

    public void deleteData()
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Preferences_name),MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        Gson gson = new Gson();
        editor.clear();
        editor.commit();
    }

    public void setButtons()
    {
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // writeToFile(pref.getText().toString(),Save_Activity.this);

                // preferenceArray = getPreferences(Save_Activity.this);
                if(!pref.getText().toString().isEmpty()) {
                    preferenceArray.add(pref.getText().toString());
                    SaveData();
                    SaveActivityListAdapter.updateList(preferenceArray);
                }

            }

        });

        SortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               sortList();
            }
        });
    }

    public static Context getContext()
    {
        return Save_Activity.getContext();
    }

    public ArrayList<String> getPreferenceArray()
    {

        return this.preferenceArray;
    }

    public void sortList()
    {

        Collections.sort(preferenceArray, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        SaveData();
        SaveActivityListAdapter.notifyDataSetChanged();
    }

}