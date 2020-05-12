package com.get2gether;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ViewMeetingFragment extends Fragment {
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

        meetingList = new ArrayList<>();
        meetingList.add(new Meeting(0,targetDate,"Steven Eldridge","CS533 Group Project","7:15-8:45, 10:00-11:30, 14:30-17:15", participantList));


        populateMeetings(ctx, view);

    }

    private void populateMeetings(Context ctx, View v) {
        RecyclerView recyclerView = v.findViewById(R.id.meetingListView);
        MeetingAdapter mAdapter = new MeetingAdapter(meetingList);
        mAdapter.setAcListener( (acceptButton, position) -> {
            Meeting m = meetingList.get(position);

            System.out.print("Accepting: ");
            System.out.println(m.getTitle());
            //TODO: Pipe stuff to web
            meetingList.remove(position);
            recyclerView.removeViewAt(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, meetingList.size());

            Bundle bundle = new Bundle();
            bundle.putInt(fragment_second.ARG_ACTION,fragment_second.ACTION_CONFMEETING);

            ArrayList<String> parts = m.getParticipants();

            LocalDate ld = m.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ArrayList<String> al = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            al.add("day");
            al.add(Integer.toString(ld.getDayOfMonth()));
            al.add("month");
            al.add(Integer.toString(ld.getMonthValue()));
            al.add("year");
            al.add(Integer.toString(ld.getYear()));
            al.add("name");
            al.add(m.getTitle());
            al.add("time");
            al.add(m.getTimeString());
            for (int i=0; i<parts.size(); i++) {
                sb.append(parts.get(i));
                if (i<parts.size()-1)
                    sb.append(", ");
            }

            al.add("participants");
            al.add(sb.toString());
            bundle.putStringArrayList(fragment_second.ARG_PASSTHROUGH, al);

            NavHostFragment.findNavController(ViewMeetingFragment.this)
                    .navigate(R.id.action_ViewMeetingFragment_to_SecondFragment,bundle);
        });
        mAdapter.setDcListener( (declineButton, position) -> {
            System.out.print("Declining: ");
            System.out.println(meetingList.get(position).getTitle());
            //TODO: Pipe stuff to web
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
