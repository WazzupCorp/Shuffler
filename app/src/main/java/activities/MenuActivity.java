package activities;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import Fragments.GruppenFragment;
import Fragments.RandomFragment;
import Fragments.RandomNumberFragment;
import wazzup.shuffler.R;


public class MenuActivity extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private CardView RandomShuffle;
    private CardView GroupShuffle;
    private CardView RandomNumber;
    private GridLayout gridLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_menu);
        initiate();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


/*
    public void selectItemDrawer(MenuItem item)
    {
        Fragment myFragment = null;
        Class fragmentclass;
        switch (item.getItemId())
        {
            case R.id.random:
            fragmentclass = RandomFragment.class;
            break;
            case R.id.gruppe:
                fragmentclass = GruppenFragment.class;
                break;
            case R.id.random_number :
                fragmentclass = RandomNumberFragment.class;
                break;
            default:
                fragmentclass = RandomFragment.class;
        }
        try{
            myFragment = (Fragment) fragmentclass.newInstance();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.content,myFragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawerlayout.closeDrawers();
    }
*/
/*
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

}

