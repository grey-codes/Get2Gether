package com.get2gether;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_makemeeting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_makemeeting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_makemeeting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_makemeeting.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_makemeeting newInstance(String param1, String param2) {
        fragment_makemeeting fragment = new fragment_makemeeting();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_makemeeting, container, false);



        String[] monthCategories = new String[] {"January", "February", "March", "April", "May",
                    "June", "July", "August", "September", "October", "November", "December"};

        String[] dayCategories = new String[31];
        for (int i = 1; i <= 31; i++) {
            dayCategories[i - 1] = "" + i;
        }

        String[] yearCategories = new String[11];
        for (int i = 2020; i <= 2030; i++) {
            yearCategories[i - 2020] = "" + i;
        }


        Log.println(Log.ERROR,"ASDF","FUCK YOU INITIALIZE ME");

        Spinner spinnerMonth = (Spinner) v.findViewById(R.id.spinnerMonth);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, monthCategories);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);

        Spinner spinnerDay = (Spinner) v.findViewById(R.id.spinnerDay);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, dayCategories);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);

        Spinner spinnerYear = (Spinner) v.findViewById(R.id.spinnerYear);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, yearCategories);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);

        Button goToSecondFrag = v.findViewById(R.id.goToSecondFrag);

        goToSecondFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinnerDay = view.getRootView().findViewById(R.id.spinnerDay);
                Spinner spinnerMonth = view.getRootView().findViewById(R.id.spinnerMonth);
                Spinner spinnerYear = view.getRootView().findViewById(R.id.spinnerYear);
                TextView textViewMeeting = view.getRootView().findViewById(R.id.projectName);
                TextView textViewParticipants = view.getRootView().findViewById(R.id.participants);

                String dayValue = spinnerDay.getSelectedItem().toString();
                String monthValue = spinnerMonth.getSelectedItem().toString();
                String yearValue = spinnerYear.getSelectedItem().toString();
                String meetingName = textViewMeeting.getText().toString();
                String participants = textViewParticipants.getText().toString();




                NavHostFragment.findNavController(fragment_makemeeting.this)
                        .navigate(R.id.makeMeetingToTimes);
            }
        });


        return v;
    }
}
