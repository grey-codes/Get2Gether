package com.get2gether;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_meetingconfirmed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_meetingconfirmed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_RECTANGLES = "rectangles";
    public static final String ARG_MEETING = fragment_second.ARG_MEETING;

    // TODO: Rename and change types of parameters
    private boolean[][] rectangles;
    private Meeting meeting;

    public fragment_meetingconfirmed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_meetingconfirmed.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_meetingsuccess newInstance(Serializable param1, ArrayList<String> param2) {
        fragment_meetingsuccess fragment = new fragment_meetingsuccess();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECTANGLES, param1);
        args.putSerializable(ARG_MEETING, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rectangles=null;
        meeting = null;
        if (getArguments() != null) {
            rectangles = (boolean[][]) getArguments().getSerializable(ARG_RECTANGLES);
            meeting = (Meeting) getArguments().getSerializable(ARG_MEETING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meetingconfirmed, container, false);

        TextView bodyText = v.findViewById(R.id.meetingConfirmBodyText);
        //TODO: convert time string into bool array, then AND with rectangles array, then find first time and use that
        MainActivity parentActivity = (MainActivity) getActivity();
        parentActivity.meetingNetwork.acceptMeeting(meeting);

        meeting.updateIdealTime(rectangles, 4);
        bodyText.setText(getString(R.string.meetingConfirmBody, meeting.getTitle(), meeting.getIdealTime(), meeting.getDay(), meeting.getMonth(), meeting.getYear()));

        Button goToMainPage = v.findViewById(R.id.goToMainPage);
        goToMainPage.setOnClickListener(view -> NavHostFragment.findNavController(fragment_meetingconfirmed.this)
                .navigate(R.id.action_fragment_meetingconfirmed_to_FirstFragment));

        return v;
    }
}
