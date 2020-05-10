package com.get2gether;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Date;

public class ViewMeetingFragment extends Fragment {
    private MainActivity parentActivity;
    private ArrayList<Meeting> meetingList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_meeting, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context ctx = getContext();

        parentActivity = (MainActivity) getActivity();

        meetingList = new ArrayList<>();
        meetingList.add(new Meeting(new Date(),"Grey Ruessler","Fuck you","05/23/2000 6:00-9:00"));


        populateMeetings(ctx, view);

    }

    private void populateMeetings(Context ctx, View v) {
        Activity act = getActivity();
        ArrayAdapter adapter = new MeetingAdapter(act,0,meetingList);
        ListView lv = v.findViewById(R.id.meetingListView);
        lv.setAdapter(adapter);
    }
}
