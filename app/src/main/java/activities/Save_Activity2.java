package activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import wazzup.shuffler.R;
import wazzup.shuffler.SwipeController;
import wazzup.shuffler.SwipeControllerItemTouchHelperListener;

public class Save_Activity2 extends AppCompatActivity implements SwipeControllerItemTouchHelperListener {
    private ArrayList<String> preferenceArray;
    private Button SaveButton;
    private Button SortButton;
    private EditText pref;
    private Save_Adapter SaveActivityListAdapter;
    public static final String PREFERENCE_KEY = "name";
    private boolean THEME_MODE;
    protected String THEME_KEY = "THEME";
    private ConstraintLayout rootlayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        loadThemeMode();
        if (THEME_MODE)
            setTheme(R.style.darkmode);
        else
            setTheme(R.style.standardTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_2);
        loadData();
        buildRecyclerView();
        setButtons();

        if (THEME_MODE) {
            pref.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_dark));

            SaveButton.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
            SortButton.setBackground(getResources().getDrawable(R.drawable.round_button_dark));
        } else {
            pref.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            SaveButton.setBackground(getResources().getDrawable(R.drawable.round_button));
            SortButton.setBackground(getResources().getDrawable(R.drawable.round_button));
        }


        pref.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (pref.getRight() - pref.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        pref.getText().clear();
                        return true;
                    }
                }
                return false;
            }
        });

    }


    public void loadThemeMode() {
        String THEME = "themes";
        SharedPreferences sharedPref = getSharedPreferences(THEME, MODE_PRIVATE);
        THEME_MODE = sharedPref.getBoolean(THEME_KEY, true);
    }

    public void insertItem(String s) {
        preferenceArray.add(s);
    }

    public void removeItem(int position) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Names_name), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(PREFERENCE_KEY, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        preferenceArray = gson.fromJson(json, type);


        assert preferenceArray != null;
        preferenceArray.remove(position);

        SaveData();
        buildRecyclerView();

    }

    private void buildRecyclerView() {
        Toolbar sToolbar = findViewById(R.id.saveToolbar2);
        RecyclerView saveActivityList = findViewById(R.id.actionlist);
        SaveButton = findViewById(R.id.RandomSave);
        SortButton = findViewById(R.id.RandomDelete);
        pref = findViewById(R.id.preferences);
        rootlayout = findViewById(R.id.saveLayout2);
        saveActivityList.setHasFixedSize(true);
        RecyclerView.LayoutManager saveActivityListManager = new LinearLayoutManager(Save_Activity2.this);
        SaveActivityListAdapter = new Save_Adapter(Save_Activity2.this, preferenceArray);
        saveActivityList.setLayoutManager(saveActivityListManager);
        saveActivityList.setAdapter(SaveActivityListAdapter);
        setSupportActionBar(sToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        saveActivityList.setItemAnimator(new DefaultItemAnimator());
        saveActivityList.addItemDecoration((new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)));
        saveActivityList.setAdapter(SaveActivityListAdapter);

        //SwipeController

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new SwipeController(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(saveActivityList);



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

    public void setButtons() {
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pref.getText().toString().isEmpty()) {
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

    public void SaveData() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Names_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(preferenceArray);
        editor.putString(PREFERENCE_KEY, json);
        editor.apply();

    }


    public void loadData() //Gibt die gespeicherten preferences zurück
    {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Names_name), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(PREFERENCE_KEY, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        preferenceArray = gson.fromJson(json, type);
        if (preferenceArray == null) {
            preferenceArray = new ArrayList<>();
        }
    }

    public void deleteData() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Names_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.clear();
        editor.apply();
    }

    public void sortList() {
        Collections.sort(preferenceArray, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        SaveData();
        SaveActivityListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof Save_Adapter.ViewHolder) {
             String name = preferenceArray.get(viewHolder.getAdapterPosition());


            final String deletedItem = preferenceArray.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();
            SaveActivityListAdapter.removeItem(deleteIndex);
            SaveData();

            Snackbar snackbar = Snackbar.make(rootlayout,name +" "+ getString(R.string.wurde_entfernt) ,Snackbar.LENGTH_SHORT);
            //Snackbar Farbe auf weiß setzen
            int SnackbarTextID = android.support.design.R.id.snackbar_text;
            View SnackbarView = snackbar.getView();
            TextView textView = (TextView) SnackbarView.findViewById(SnackbarTextID);
            textView.setTextColor(getResources().getColor(R.color.white));
            //Snackbar Zurücksetzen Action
            snackbar.setAction(R.string.zurücksetzen, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SaveActivityListAdapter.restoreItem(deletedItem,deleteIndex);
                    SaveData();
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();



        }
    }
}


