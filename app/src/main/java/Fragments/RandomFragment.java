package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import wazzup.shuffler.R;


public class RandomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RandomFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and chnge types and number of parameters
    public static RandomFragment newInstance(String param1, String param2) {
        RandomFragment fragment = new RandomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private EditText PEingabe;
    private Button Pspeichern;
   private Button Shuffle;
   private ListView PListe;
   private ArrayList<String> personenliste;
   private ArrayAdapter<String> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_random, container, false);
        PEingabe= view.findViewById(R.id.randompersonfield);
        Pspeichern = view.findViewById(R.id.r_speichern);
       Shuffle = view.findViewById(R.id.shuffle);
        PListe = view.findViewById(R.id.personen);
       personenliste = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,personenliste);
       PListe.setAdapter(adapter);

        Pspeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PEingabe.getText().toString().length() > 0) {
                    String name = PEingabe.getText().toString();
                    adapter.add(name);
                }

            }
        });


        Shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personenliste.size()>= 2) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.shuffle_popup, null);
                    TextView mPerson = mView.findViewById(R.id.popupPerson);
                    mPerson.setText(shuffle(personenliste, personenliste.size()));
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            }
        });

        PListe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.remove(adapter.getItem(i));
                return true;
            }
        });







        return view;
    }



    public String shuffle(ArrayList<String> namen , int size)
    {
        Random rand = new Random();
        int random = rand.nextInt(size);
        return namen.get(random).toString();
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
