package activities;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.telecom.Call;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import Fragments.GruppenFragment;
import Fragments.RandomFragment;
import Fragments.RandomNumberFragment;
import wazzup.shuffler.R;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private CardView RandomShuffle;
    private CardView GroupShuffle;
    private CardView RandomNumber;
    private GridLayout gridLayout;
    private NavigationView navigationView;
    private boolean THEME_MODE =false;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        loadThemeMode();
        if(THEME_MODE)
        {
           setTheme(R.style.darkmode);

        }
        else

            {
         setTheme(R.style.standardTheme);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_menu);

        initiate();

        if(THEME_MODE)
        {
            navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_dark));
        }
        else
            {
                navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded));
        }

        //Intents für Activities
        final Intent x = new Intent(this, RandomActivity.class);
        final Intent y = new Intent(this, GruppenActivity.class);
        final Intent z = new Intent(this, RandomNumberActivity.class);

        RandomShuffle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(x);
    }
});
        GroupShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(y);
            }
        });
        RandomNumber.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(z);
    }
});

    }



    private void initiate()
    {


        gridLayout = findViewById(R.id.GridMenu);
        RandomShuffle = findViewById(R.id.RandomCard);
        GroupShuffle = findViewById(R.id.GroupCard);
        RandomNumber = findViewById(R.id.RandomNumberCard);
        navigationView = findViewById(R.id.menu_navigationview);
        mDrawerlayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
    }




/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void selectItemDrawer(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.functions:

            break;
            case R.id.darkmode:
                if (this.getTheme().equals(R.style.standardTheme)
                        ) {
                    setTheme(R.style.darkmode);

                }
                else{
                    setTheme(R.style.standardTheme);
                }
            case R.id.settings:

                break;
            case R.id.about:

                break;
            default:

        }

    }


    private void setupdDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



               selectItemDrawer(item);
                return true;
            }
        });
    }

*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.functions:

                break;
            case R.id.darkmode:


                if(!THEME_MODE) {

                    THEME_MODE = true;
                    saveThemeMode();
                    recreate();
                }else
                {
                    THEME_MODE = false;
                    saveThemeMode();
                    recreate();

                }


            case R.id.settings:

                break;
            case R.id.about:

                break;
            default:

        }

        return true;
    }

    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
      THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }

    public void saveThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(THEME_KEY, THEME_MODE).apply();
    }

}

