package com.get2gether;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
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

        parentActivity = (MainActivity) getActivity();
        Context ctx = getContext();

        ArrayList<String> participantList = new ArrayList<>();
        participantList.add("Steven Eldridge");

        String currentDateString = "05/23/2020 18:00:00";
        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date targetDate = new Date();
        try {
            targetDate = sd.parse(currentDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //meetingList = new ArrayList<>();
        //meetingList.add(new Meeting(0,targetDate,"Steven Eldridge","CS533 Group Project","7:15-8:45, 10:00-11:30, 14:30-17:15", participantList));


        //populateMeetings(ctx, view);

        parentActivity.meetingNetwork.getMeetings(meetings -> {
            meetingList = meetings;
            parentActivity.runOnUiThread(() -> populateMeetings(ctx, view));
        });
    }

    private void populateMeetings(Context ctx, View v) {
        RecyclerView recyclerView = v.findViewById(R.id.meetingListView);
        MeetingAdapter mAdapter = new MeetingAdapter(meetingList);
        mAdapter.setAcListener( (acceptButton, position) -> {
            Meeting m = meetingList.get(position);

            System.out.print("Accepting: ");
            System.out.println(m.getTitle());

            parentActivity.meetingNetwork.acceptMeeting(m);

            meetingList.remove(position);
            recyclerView.removeViewAt(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, meetingList.size());

            Bundle bundle = new Bundle();
            bundle.putInt(fragment_second.ARG_ACTION,fragment_second.ACTION_CONFMEETING);
            bundle.putSerializable(fragment_second.ARG_MEETING, m);

            NavHostFragment.findNavController(ViewMeetingFragment.this)
                    .navigate(R.id.action_ViewMeetingFragment_to_SecondFragment,bundle);
        });
        mAdapter.setDcListener( (declineButton, position) -> {
            Meeting m = meetingList.get(position);

            System.out.print("Declining: ");
            System.out.println(m.getTitle());

            parentActivity.meetingNetwork.declineMeeting(m);

            meetingList.remove(position);
            recyclerView.removeViewAt(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, meetingList.size());
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);
    }
}
