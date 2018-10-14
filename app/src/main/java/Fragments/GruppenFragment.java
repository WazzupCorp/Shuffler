package Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

import wazzup.shuffler.Gruppe;
import wazzup.shuffler.R;
import wazzup.shuffler.TestShow;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GruppenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GruppenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GruppenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GruppenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GruppenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GruppenFragment newInstance(String param1, String param2) {
        GruppenFragment fragment = new GruppenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

    }

    private Spinner gruppenSpinner;
    private EditText PEingabe2;
    private ArrayList<Integer> SpinnerArray;
    private ListView PListe2;
    private static ArrayList<String> personenliste2;
    private ArrayAdapter<String> adapter2;
    private Button Pspeichern2;
    private  Button Shuffle2;
    private int personen;
    private int groupnumber;
    private int groupsize;
    public static ArrayList<Gruppe> ShuffledGruppe;
    public static  ArrayList<Gruppe> GruppenListe;










    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gruppen, container, false);



        SpinnerArray = new ArrayList<Integer>();
        SpinnerArray.add(2);
        SpinnerArray.add(3);
        SpinnerArray.add(4);
        SpinnerArray.add(5);

        //----------------------//
        /* SpinnerArray.add(6);
        SpinnerArray.add(7);
        SpinnerArray.add(8);
        SpinnerArray.add(9);
        SpinnerArray.add(10);*/
        //----------------------//

        PEingabe2 = view.findViewById(R.id.randompersonfield2);
        gruppenSpinner = view.findViewById(R.id.gruppenSpinner);
        PListe2 = view.findViewById(R.id.personen2);

        if(savedInstanceState !=null)
        {
            personenliste2 =savedInstanceState.getStringArrayList("personenliste2");
        }else {
            personenliste2 = new ArrayList<>();
        }

        Shuffle2 = view.findViewById(R.id.shuffle2);
        Pspeichern2 = view.findViewById(R.id.speichern2);

            adapter2 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,personenliste2);
            PListe2.setAdapter(adapter2);






        ArrayAdapter<Integer> Sadapter = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_list_item_1,SpinnerArray);
        Sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gruppenSpinner.setAdapter(Sadapter);

        Pspeichern2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( PEingabe2.getText().toString().length() > 0) {
                    String name =  PEingabe2.getText().toString();
                    adapter2.add(name);

                }
            }
        });




        Shuffle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






               groupShuffler(personenliste2);
            }



        });


        PListe2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter2.remove(adapter2.getItem(i));
                return true;
            }
        });

        return view;
    }




    @Override
    public void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        outstate.putStringArrayList("personenliste2" , personenliste2);

    }







    private void groupShuffler(ArrayList<String> p)
    {
        personen = personenliste2.size(); //Anzahl der Personen
        groupnumber = gruppenSpinner.getSelectedItemPosition()+2; //Gruppenanzahl
        int gruppennummer = 1; //Nummer der aktuellen Gruppe
        ShuffledGruppe = new ArrayList<Gruppe>();  //Gruppenliste mit Zufälligen Personen
        groupsize = personen / groupnumber;

        if(personenliste2.size()>= groupnumber) {
            if (personenliste2.size() % groupnumber == 0) // Wenn gleichgroße Gruppen entstehen können
            {
                GruppenListe = new ArrayList<Gruppe>(); //Liste der Gruppen
                for (int i = 0; i < groupnumber; i++) {
                    ArrayList<String> s = new ArrayList<String>();
                    GruppenListe.add(new Gruppe(groupsize, gruppennummer, s));
                    gruppennummer++;
                }
                //Toast.makeText(GruppenActivity.this,String.valueOf(GruppenListe.size()),Toast.LENGTH_SHORT).show();
                int nextgroup = 0;
                ArrayList<String>personcopy = new ArrayList<>(personenliste2);
                while(!personcopy.isEmpty()){

                    Gruppe aktuelleGruppe = GruppenListe.get(nextgroup); // Aktuelle Gruppe
                    int random = getRandom(personcopy.size());
                    String aktuellePerson;
                    if(personcopy.size()>1) {
                        aktuellePerson = personcopy.get(random);
                    }
                    else
                    {
                        aktuellePerson = personcopy.get(0);
                    }



                    if (!aktuelleGruppe.isFull()) {
                        aktuelleGruppe.setPerson(aktuellePerson);
                        personcopy.remove(aktuellePerson);
                        if(aktuelleGruppe.isFull())
                        {
                            ShuffledGruppe.add(aktuelleGruppe);
                            nextgroup++;
                        }
                    }


                }

//return ShuffledGruppe;

               Intent i = new Intent(getActivity(), TestShow.class);
                startActivity(i);
                adapter2.notifyDataSetChanged();


            } else if(personenliste2.size() % groupnumber != 0)
            {
                GruppenListe = new ArrayList<Gruppe>(); //Liste der Gruppen
                int überschuss = personen % groupnumber;
                for (int i = 0; i < groupnumber; i++) {
                    if (überschuss > 0) {
                        ArrayList<String> s = new ArrayList<String>();
                        GruppenListe.add(new Gruppe(groupsize + 1, gruppennummer, s));
                        gruppennummer++;
                        überschuss--;
                    }
                    else
                    {
                        ArrayList<String> s = new ArrayList<String>();

                        GruppenListe.add(new Gruppe(groupsize, gruppennummer, s));
                        gruppennummer++;
                    }
                }
                //Toast.makeText(GruppenActivity.this,String.valueOf(GruppenListe.size()),Toast.LENGTH_SHORT).show();
                int nextgroup = 0;
                ArrayList<String>personcopy = new ArrayList<>(personenliste2);
                while(!personcopy.isEmpty()){

                    Gruppe aktuelleGruppe = GruppenListe.get(nextgroup); // Aktuelle Gruppe
                    int random = getRandom(personcopy.size());
                    String aktuellePerson;
                    if(personcopy.size()>1) {
                        aktuellePerson = personcopy.get(random);
                    }
                    else
                    {
                        aktuellePerson = personcopy.get(0);
                    }



                    if (!aktuelleGruppe.isFull()) {
                        aktuelleGruppe.setPerson(aktuellePerson);
                        personcopy.remove(aktuellePerson);
                        if(aktuelleGruppe.isFull())
                        {
                            ShuffledGruppe.add(aktuelleGruppe);
                            nextgroup++;
                        }
                    }
                    else if(!personcopy.isEmpty()){
                        int r = getRandom(ShuffledGruppe.size());
                        ShuffledGruppe.get(random).getPersonen().add(personcopy.get(0));

                    }


                }
                //return ShuffledGruppe;


                Intent i = new Intent(getActivity(), TestShow.class);
                startActivity(i);
            }
        }
       // return ShuffledGruppe;
    }


    public int getRandom(int size)
    {
        Random rand = new Random();
        int random = rand.nextInt(size);
        return random;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
