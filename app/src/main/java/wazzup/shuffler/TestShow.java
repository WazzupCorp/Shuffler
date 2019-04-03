package wazzup.shuffler;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import activities.GruppenActivity;

public class TestShow extends AppCompatActivity {

    private ArrayList<Gruppe> gruppe;
    private boolean THEME_MODE;
    private TextView gruppenNummer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadThemeMode();
        if (THEME_MODE)
            setTheme(R.style.darkmode);
        else
            setTheme(R.style.standardTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show);

        gruppenNummer = findViewById(R.id.groupItemNumber);
        ArrayList g = new ArrayList<>();
        ArrayList<String> finalGroup = new ArrayList<>();
        //Toolbar
        Toolbar groupsToolbar = findViewById(R.id.groupsToolbar);
        setSupportActionBar(groupsToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView.Adapter groupAdapter = new GroupAdapter(TestShow.this,finalGroup);

        RecyclerView groups = findViewById(R.id.ShuffledGroups);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TestShow.this);
        groups.setLayoutManager(layoutManager);
        groups.setAdapter(groupAdapter);





        for (int i = 0; i < GruppenActivity.ShuffledGruppe.size(); i++) {
            int gruppe = 1 + i;
            g = GruppenActivity.ShuffledGruppe.get(i).getPersonen();
          String s= Arrays.toString(g.toArray()).replace("[", "").replace("]", "");
            finalGroup.add(s);




        }






       // ArrayAdapter testAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, g);





    }


    public void loadThemeMode() {
        String THEME = "themes";
        SharedPreferences sharedPref = getSharedPreferences(THEME, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();
        String THEME_KEY = "THEME";
        THEME_MODE = sharedPref.getBoolean(THEME_KEY, true);
    }
}