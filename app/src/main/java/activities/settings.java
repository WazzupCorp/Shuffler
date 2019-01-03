package activities;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import java.util.Objects;

import wazzup.shuffler.R;

public class settings extends AppCompatActivity {

    private boolean THEME_MODE;
    private Toolbar sToolbar;
    private SwitchCompat sideBarSwitch;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";
    private String SIDEBAR_SETTINGS = "sidebar";
    private String SIDEBAR_KEY = "sidebarkey";
    private boolean SETTINGS_MODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadThemeMode();

        if(THEME_MODE) {
            setTheme(R.style.darkmode);

        }
        else {

            setTheme(R.style.standardTheme);

        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadSettings();
        initiate();

        if(THEME_MODE) {

            sToolbar.setBackgroundColor(getResources().getColor(R.color.darkergray));
            sToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            sideBarSwitch.setTextColor(getResources().getColor(R.color.white));
        }
        else {


            sToolbar.setBackgroundColor(getResources().getColor(R.color.white));
            sToolbar.setTitleTextColor(getResources().getColor(R.color.black));
            sideBarSwitch.setTextColor(getResources().getColor(R.color.black));

        }





    }


    private void initiate()
    {
        sToolbar = findViewById(R.id.settingsToolbar);
        sideBarSwitch= findViewById(R.id.sidebarswitch);

        setSupportActionBar(sToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sideBarSwitch.setChecked(SETTINGS_MODE);

        sideBarSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    saveSettings();

            }
        });



    }

    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }

    public void loadSettings()
    {
        SharedPreferences sharedPref = getSharedPreferences(SIDEBAR_SETTINGS,MODE_PRIVATE);
        SETTINGS_MODE = sharedPref.getBoolean(SIDEBAR_KEY,true);
    }

    public void saveSettings()
    {
        SharedPreferences sharedPref = getSharedPreferences(SIDEBAR_SETTINGS , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SIDEBAR_KEY, sideBarSwitch.isChecked()).apply();
    }
}
