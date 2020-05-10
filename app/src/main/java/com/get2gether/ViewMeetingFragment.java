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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        meetingList = new ArrayList<>();
        meetingList.add(new Meeting(0,new Date(),"Grey Ruessler","Fuck you","05/23/2000 6:00-9:00"));
        meetingList.add(new Meeting(1,new Date(),"Garry Russel","Fuck you too","05/23/2000 6:00-9:00"));


        populateMeetings(ctx, view);

    }

    private void populateMeetings(Context ctx, View v) {
        RecyclerView recyclerView = v.findViewById(R.id.meetingListView);
        MeetingAdapter mAdapter = new MeetingAdapter(meetingList);
        mAdapter.setAcListener( (acceptButton, position) -> {
            System.out.print("Accepting: ");
            System.out.println(meetingList.get(position).getTitle());
            //TODO: Pipe stuff to web
            meetingList.remove(position);
            recyclerView.removeViewAt(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, meetingList.size());
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
