package wazzup.shuffler;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Objects;

public class FiftyFiftyActivity extends AppCompatActivity {
  private   CheckBox großbuchstaben;
  private CheckBox kleinbuchstaben;
  private CheckBox zahlen;
  private CheckBox sonderzeichen;
  private TextView passwordText;
  private Button generate;
  private Spinner passwordSpinner;
  private ArrayList<Integer> SpinnerArray;
  private ArrayAdapter<Integer> Sadapter;
  private String THEME = "themes";
  private String THEME_KEY = "THEME";
  private TextView passwordlänge;


    private boolean THEME_MODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadThemeMode();

        if(THEME_MODE)
            setTheme(R.style.darkmode);

        else
            setTheme(R.style.standardTheme);






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifty_fifty);
        initiate();


        if(THEME_MODE)
        {

            passwordSpinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_dark));
            großbuchstaben.setTextColor(getResources().getColor(R.color.white));
            kleinbuchstaben.setTextColor(getResources().getColor(R.color.white));
            zahlen.setTextColor(getResources().getColor(R.color.white));
            sonderzeichen.setTextColor(getResources().getColor(R.color.white));


        }
        else
        {

            passwordSpinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
            großbuchstaben.setTextColor(getResources().getColor(R.color.darkgray));
            kleinbuchstaben.setTextColor(getResources().getColor(R.color.darkgray));
            zahlen.setTextColor(getResources().getColor(R.color.darkgray));
            sonderzeichen.setTextColor(getResources().getColor(R.color.darkgray));

        }





    }


    private void initiate()
    {
        SpinnerArray = new ArrayList<>();
        SpinnerArray.add(1);
        SpinnerArray.add(2);
        SpinnerArray.add(3);
        SpinnerArray.add(4);
        SpinnerArray.add(5);
        SpinnerArray.add(6);
        SpinnerArray.add(7);
        SpinnerArray.add(8);
        SpinnerArray.add(9);
        SpinnerArray.add(10);
        SpinnerArray.add(11);
        SpinnerArray.add(12);
        SpinnerArray.add(13);
        SpinnerArray.add(14);
        SpinnerArray.add(15);
        SpinnerArray.add(16);
        SpinnerArray.add(17);
        SpinnerArray.add(18);
        SpinnerArray.add(19);
        SpinnerArray.add(20);
        SpinnerArray.add(21);
        SpinnerArray.add(22);
        SpinnerArray.add(23);
        SpinnerArray.add(24);
        SpinnerArray.add(25);
        SpinnerArray.add(26);
        SpinnerArray.add(27);
        SpinnerArray.add(28);
        SpinnerArray.add(29);
        SpinnerArray.add(30);


        passwordlänge = findViewById(R.id.passwordlength);
        großbuchstaben = findViewById(R.id.großbuchstaben);
        kleinbuchstaben = findViewById(R.id.kleinbuchstaben);
        zahlen = findViewById(R.id.zahlen);
        sonderzeichen = findViewById(R.id.sonderzeichen);
        passwordText = findViewById(R.id.password);
        generate = findViewById(R.id.generate);
        passwordSpinner = findViewById(R.id.passwordspinner);
        Sadapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,SpinnerArray); //Adapter für Spinner
        Sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        passwordSpinner.setAdapter(Sadapter);
        Toolbar fToolbar = findViewById(R.id.FiftyFiftyToolbar);
        setSupportActionBar(fToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePassword(passwordSpinner.getSelectedItemPosition()+1);
            }
        });
    }




    public void loadThemeMode()
    {
        SharedPreferences sharedPref = getSharedPreferences(THEME,MODE_PRIVATE);
        THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
    }



    private static final String GROSSBUCHSTABEN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String KLEINBUCHSTABEN = "abcdefghijklmnopqrstuvwxyz";
    private static final String ZAHLEN= "0123456789";
    private static final String SONDERZEICHEN = ";:_-*+#<>|!§$%&/()=?";


    private void generatePassword(int length) {





        if(großbuchstaben.isChecked() & kleinbuchstaben.isChecked() & zahlen.isChecked() & sonderzeichen.isChecked() ) {
            String password = GROSSBUCHSTABEN + KLEINBUCHSTABEN + ZAHLEN + SONDERZEICHEN;

             passwordText.setText(generateString(password, length));
        }
       else if(großbuchstaben.isChecked() & kleinbuchstaben.isChecked() & zahlen.isChecked())
        {
            String password = GROSSBUCHSTABEN + KLEINBUCHSTABEN + ZAHLEN;

            passwordText.setText(generateString(password, length));
        }
        else if(großbuchstaben.isChecked() & kleinbuchstaben.isChecked() & sonderzeichen.isChecked())
        {
            String password = GROSSBUCHSTABEN + KLEINBUCHSTABEN + SONDERZEICHEN;

            passwordText.setText(generateString(password, length));
        }
        else if( kleinbuchstaben.isChecked() & sonderzeichen.isChecked() & zahlen.isChecked())
        {
            String password = KLEINBUCHSTABEN + ZAHLEN +SONDERZEICHEN;

            passwordText.setText(generateString(password, length));
        }
        else if(großbuchstaben.isChecked() & sonderzeichen.isChecked() & zahlen.isChecked())
        {
            String password = GROSSBUCHSTABEN + SONDERZEICHEN + ZAHLEN;

            passwordText.setText(generateString(password, length));
        }
        else if(großbuchstaben.isChecked() & kleinbuchstaben.isChecked())
        {
            String password = GROSSBUCHSTABEN + KLEINBUCHSTABEN ;

            passwordText.setText(generateString(password, length));
        }
        else if(großbuchstaben.isChecked() & sonderzeichen.isChecked())
        {
            String password = GROSSBUCHSTABEN + SONDERZEICHEN;

            passwordText.setText(generateString(password, length));
        }
        else if(großbuchstaben.isChecked() & zahlen.isChecked())
        {
            String password = GROSSBUCHSTABEN + ZAHLEN;

            passwordText.setText(generateString(password, length));
        }
        else if(kleinbuchstaben.isChecked() & sonderzeichen.isChecked())
        {
            String password = KLEINBUCHSTABEN + SONDERZEICHEN;

            passwordText.setText(generateString(password, length));
        }
        else if(kleinbuchstaben.isChecked() & zahlen.isChecked())
        {
            String password = KLEINBUCHSTABEN + ZAHLEN;

            passwordText.setText(generateString(password, length));
        }
        else if(sonderzeichen.isChecked() & zahlen.isChecked())
        {
            String password = SONDERZEICHEN+ ZAHLEN;

            passwordText.setText(generateString(password, length));
        }
        else if(großbuchstaben.isChecked())
        {
            String password = GROSSBUCHSTABEN;

            passwordText.setText(generateString(password, length));
        }
        else if(kleinbuchstaben.isChecked())
        {
            String password =  KLEINBUCHSTABEN;

            passwordText.setText(generateString(password, length));
        }
        else if(zahlen.isChecked())
        {
            String password =ZAHLEN;

            passwordText.setText(generateString(password, length));
        }
        else if(sonderzeichen.isChecked() )
        {
            String password = SONDERZEICHEN;
            passwordText.setText(generateString(password, length));
        }
        else
        {
            Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
        }



    }


    private String generateString(String pw , int pwlength)
    {
        StringBuilder builder = new StringBuilder();
        while (pwlength-- != 0) {
            int character = (int)(Math.random()*pw.length());
            builder.append(pw.charAt(character));
        }
        return builder.toString();
    }
}
