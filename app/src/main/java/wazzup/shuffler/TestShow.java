package wazzup.shuffler;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import activities.GruppenActivity;

public class TestShow extends AppCompatActivity {

    private ListView groups;
    private ArrayList<String> g;
    private ArrayAdapter<String> testAdapter;
    private ArrayList<Gruppe> gruppe;
    private Toolbar groupsToolbar;
    private boolean THEME_MODE;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadThemeMode();
        if (THEME_MODE)
            setTheme(R.style.darkmode);
        else
            setTheme(R.style.standardTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show);
        groupsToolbar = findViewById(R.id.groupsToolbar);

        setSupportActionBar(groupsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        groups = findViewById(R.id.ShuffledGroups);
        g = new ArrayList<>();
        //  gruppe = GruppenFragment.ShuffledGruppe;
        testAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, g);
        groups.setAdapter(testAdapter);


        for (int i = 0; i < GruppenActivity.ShuffledGruppe.size(); i++) {
            int gruppe = 1 + i;
            g = GruppenActivity.ShuffledGruppe.get(i).getPersonen();
            String s = "Gruppe " + gruppe + ": \n" + Arrays.toString(g.toArray()).replace("[", "").replace("]", "");
            testAdapter.add(s);
        }

    }


    public void loadThemeMode() {
        SharedPreferences sharedPref = getSharedPreferences(THEME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        THEME_MODE = sharedPref.getBoolean(THEME_KEY, true);
    }
}