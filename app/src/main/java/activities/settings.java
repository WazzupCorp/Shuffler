package activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;
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
    private Spinner languageSpinner;
    private boolean SETTINGS_MODE;
    private String LANGUAGE = "language";
    private String LANGUAGE_KEY = "languagekey";
    private  ArrayAdapter<String> sAdapter;
    private String CURRENT_LANGUAGE;
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

            sToolbar.setBackgroundColor(getResources().getColor(R.color.darkergrey));
            sToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            sideBarSwitch.setTextColor(getResources().getColor(R.color.white));
        }
        else {


            sToolbar.setBackgroundColor(getResources().getColor(R.color.white));
            sToolbar.setTitleTextColor(getResources().getColor(R.color.black));
            sideBarSwitch.setTextColor(getResources().getColor(R.color.black));

        }

        setLanguage();




    }


    private void initiate()
    {
        sToolbar = findViewById(R.id.settingsToolbar);
        sideBarSwitch= findViewById(R.id.sidebarswitch);
        languageSpinner = findViewById(R.id.languageswitch);

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

        languageSpinner();
        sAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,languages);
        languageSpinner.setAdapter(sAdapter);
        sAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        languageSpinner.setSelection(sAdapter.getPosition(CURRENT_LANGUAGE));

    }

    ArrayList<String> languages;
    public void languageSpinner()
    {
        languages = new ArrayList<>();
        languages.add("de");
        languages.add("en");
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

        SharedPreferences sharedPrefL = getSharedPreferences(LANGUAGE,MODE_PRIVATE);
        CURRENT_LANGUAGE = sharedPrefL.getString(LANGUAGE_KEY,"de");
    }

    public void saveSettings()
    {
        SharedPreferences sharedPref = getSharedPreferences(SIDEBAR_SETTINGS , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SIDEBAR_KEY, sideBarSwitch.isChecked()).apply();

        SharedPreferences sharedPrefL = getSharedPreferences(LANGUAGE, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPrefL.edit();
        editor1.putString(LANGUAGE_KEY,(String)languageSpinner.getSelectedItem()).apply();
    }

    public void setLanguage()
    {
        if(CURRENT_LANGUAGE.equals("de"))
        {
            Locale locale = new Locale(CURRENT_LANGUAGE);
            Locale.setDefault(locale);
            Configuration conf = getBaseContext().getResources().getConfiguration();
            conf.locale = locale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }else if(CURRENT_LANGUAGE.equals("en"));
        {
            Locale locale = new Locale(CURRENT_LANGUAGE);
            Locale.setDefault(locale);
            Configuration conf = getBaseContext().getResources().getConfiguration();
            conf.locale = locale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MenuActivity.class));
        finish();

    }





    @Override
    protected void onDestroy()
    {
        super.onDestroy();
       saveSettings();
       setLanguage();
    }
}
