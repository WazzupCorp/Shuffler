package activities;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import wazzup.shuffler.FiftyFiftyActivity;
import wazzup.shuffler.R;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private ImageButton RandomShuffle;
    private ImageButton GroupShuffle;
    private ImageButton RandomNumber;
    private ImageButton FiftyFifty;
    private GridLayout gridLayout;
    public NavigationView navigationView;
    private boolean THEME_MODE =false;
    private String THEME = "themes";
    private String THEME_KEY = "THEME";
    private String SIDEBAR_SETTINGS = "sidebar";
    private String SIDEBAR_KEY = "sidebarkey";
    private String MENU_BUTTONS = "menu";
    private String MENU_BUTTONS_KEY = "MENU_BUTTONS";
    private String LAYOUT = "Layout_State";
    private String LAYOUT_KEY = "saved_layout_key";
    private boolean SETTINGS_MODE;




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

        loadSettings();

        super.onCreate(savedInstanceState);

       if(savedInstanceState!= null)
       {

       }




        setContentView(R.layout.activity_main_menu);
        initiate();
        setNavigationViewTheme();


        //Intents für Activities
        final Intent x = new Intent(this, RandomActivity.class);
        final Intent y = new Intent(this, GruppenActivity.class);
        final Intent z = new Intent(this, RandomNumberActivity.class);
        final Intent a = new Intent(this, FiftyFiftyActivity.class);
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
        FiftyFifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(a);
            }
        });


        RandomShuffle.setOnLongClickListener(longClickListener);
        RandomShuffle.setOnDragListener(dragListener);

        GroupShuffle.setOnLongClickListener(longClickListener);
        GroupShuffle.setOnDragListener(dragListener);

        RandomNumber.setOnLongClickListener(longClickListener);
        RandomNumber.setOnDragListener(dragListener);

        FiftyFifty.setOnLongClickListener(longClickListener);
        FiftyFifty.setOnDragListener(dragListener);


    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder menuShadowBuilder = new View.DragShadowBuilder(view);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                    view.startDragAndDrop(data, menuShadowBuilder, view, View.DRAG_FLAG_OPAQUE);
            }
            else {
                view.startDrag(data, menuShadowBuilder, view, 0);
                     }
            return true;
        }
    };

View.OnDragListener dragListener = new View.OnDragListener() {
    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        int dragEve = dragEvent.getAction();
        final View v = (View) dragEvent.getLocalState();

        ImageButton dropTarget =(ImageButton) view;
        ImageButton dropped = (ImageButton) v;
        switch (dragEve)
        {
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;

            case  DragEvent.ACTION_DROP:
                        dropped.animate().x(dropTarget.getX()).y(dropTarget.getY()).setDuration(400).start();
                        dropTarget.animate().x(dropped.getX()).y(dropped.getY()).setDuration(400).start();
               break;

            case  DragEvent.ACTION_DRAG_STARTED:

                if(v.getId() == R.id.RandomCard)
                {
                    v.setAlpha(0);
                }
                else if(v.getId() == R.id.GroupCard)
                {
                    v.setAlpha(0);
                }
                else if(v.getId() == R.id.RandomNumberCard)
                {
                    v.setAlpha(0);
                }
                else if(v.getId() == R.id.FiftyFifty)
                {
                    v.setAlpha(0);
                }
                break;

            case  DragEvent.ACTION_DRAG_ENDED:

                if(v.getId() == R.id.RandomCard)
                {
                   v.setAlpha(1);
                }
                else if(v.getId() == R.id.GroupCard)
                {
                   v.setAlpha(1);
                }
                else if(v.getId() == R.id.RandomNumberCard)
                {
              v.setAlpha(1);
                }
                else if(v.getId() == R.id.FiftyFifty)
                {
               v.setAlpha(1);
                }
                break;
        }
        return true;
    }
};


    private void initiate()
    {


       // gridLayout = findViewById(R.id.GridMenu);
        RandomShuffle = findViewById(R.id.RandomCard);
        GroupShuffle = findViewById(R.id.GroupCard);
        RandomNumber = findViewById(R.id.RandomNumberCard);
        FiftyFifty = findViewById(R.id.FiftyFifty);
        navigationView = findViewById(R.id.menu_navigationview);
        mDrawerlayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);





        //NavigationView Seite
        if(SETTINGS_MODE) // Linke Seite
        {

            mDrawerlayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }
       else //Rechte Seite
        {
            mDrawerlayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }


        if(THEME_MODE)
        {
            if(SETTINGS_MODE)
                navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_dark_right));
            else
                navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_dark));
        }
        else
        {
            if(SETTINGS_MODE)
                navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_right));
            else
                navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded));
        }
    }


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
                break;

            case R.id.settings:
                    Intent i = new Intent(this, settings.class);
                    startActivity(i);
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
      THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }

    public void saveThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(THEME_KEY, THEME_MODE).apply();
    }


    public void loadSettings()
    {
        SharedPreferences sharedPref = getSharedPreferences(SIDEBAR_SETTINGS,MODE_PRIVATE);
        SETTINGS_MODE = sharedPref.getBoolean(SIDEBAR_KEY,true);
    }

public void setNavigationViewTheme()
{
    if(THEME_MODE)
    {
        if(SETTINGS_MODE)
            navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_dark_right));
        else
            navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_dark));
    }
    else
    {
        if(SETTINGS_MODE)
            navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded_right));
        else
            navigationView.setBackground(getResources().getDrawable(R.drawable.navigationview_rounded));
    }
}





    @Override
    protected void onPause()
    {
        super.onPause();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

    }
}

