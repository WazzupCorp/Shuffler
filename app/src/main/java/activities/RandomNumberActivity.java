package activities;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

import wazzup.shuffler.R;

public class RandomNumberActivity extends AppCompatActivity {
    private  EditText first;
    private EditText second;
    private Button doIt;
    private Toolbar nToolbar;
    private Dialog myDialog;
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
        setContentView(R.layout.random_number);
        initiate();

        if(THEME_MODE)
        {
            first.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_dark));
            second.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_dark));

            doIt.setBackground(getResources().getDrawable(R.drawable.round_button_dark));

        }
        else
        {
            first.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            second.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));

            doIt.setBackground(getResources().getDrawable(R.drawable.round_button));
        }

    }


    public void initiate()
    {
       first = findViewById(R.id.FirstNumber);
       second = findViewById(R.id.SecondNumber);
        doIt = findViewById(R.id.random_number_shuffle);
        nToolbar = findViewById(R.id.nToolbar);
        myDialog = new Dialog(RandomNumberActivity.this);

        doIt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!first.getText().toString().isEmpty() & !second.getText().toString().isEmpty())
                {
                    int firstNumber = Integer.parseInt(first.getText().toString());
                    int secondNumber = Integer.parseInt(second.getText().toString());

                    if(firstNumber < secondNumber &firstNumber <2147483646 & secondNumber <= 2147483646 )
                    {
                        myDialog.setContentView(R.layout.number_shuffle_popup);
                        RelativeLayout frame = myDialog.findViewById(R.id.popupFrame2);
                        TextView mPerson = myDialog.findViewById(R.id.popupPerson2);
                        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mPerson.setText(String.valueOf(generateRandomNumber()));
                        myDialog.getWindow().setWindowAnimations(R.style.DialogScale);
                        myDialog.show();
                    }
                    else
                    {
                        Toast.makeText(RandomNumberActivity.this,R.string.kleinere_Werte, Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


        setSupportActionBar(nToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public int generateRandomNumber()
    {
            if (!first.getText().toString().isEmpty() & !second.getText().toString().isEmpty())
            {


               int firstNumber = Integer.parseInt(first.getText().toString());
               int secondNumber = Integer.parseInt(second.getText().toString());
                Random r = new Random();
                if(firstNumber < secondNumber)
                {
                    int i = r.nextInt((secondNumber - firstNumber) + 1) + firstNumber;
                  //  Toast.makeText(RandomNumberActivity.this,String.valueOf(i), Toast.LENGTH_SHORT).show();
                    return i;
                }

            }
            else
            {
             Toast.makeText(RandomNumberActivity.this,R.string.leeres_feld, Toast.LENGTH_SHORT).show();
            }
            return 0;
    }

    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }

    }






