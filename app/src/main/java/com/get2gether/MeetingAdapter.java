package com.get2gether;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {
    private ArrayList<Meeting> mDataset;
    private ClickListener acListener;
    private  ClickListener dcListener;

    public interface ClickListener {
        void onClick(View v, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView display_name;
        TextView display_time;
        ImageButton acceptBtn;
        ImageButton declineBtn;
        MyViewHolder(View v, ClickListener acceptListener, ClickListener declineListener) {
            super(v);
            //super(display_name);
            this.display_name = v.findViewById(R.id.entry_meeting_title);
            this.display_time = v.findViewById(R.id.entry_meeting_datetime);
            this.acceptBtn = v.findViewById(R.id.entry_meeting_accept);
            this.declineBtn = v.findViewById(R.id.entry_meeting_decline);
            this.acceptBtn.setOnClickListener(view -> {
                acceptListener.onClick(v, getAdapterPosition());
            });
            this.declineBtn.setOnClickListener(view -> {
                declineListener.onClick(v, getAdapterPosition());
            });
        }
    }

    public ClickListener getAcListener() {
        return acListener;
    }

    public void setAcListener(ClickListener acListener) {
        this.acListener = acListener;
    }

    public ClickListener getDcListener() {
        return dcListener;
    }

    public void setDcListener(ClickListener dcListener) {
        this.dcListener = dcListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MeetingAdapter(ArrayList<Meeting> myDataset, ClickListener acListener, ClickListener dcListener) {
        mDataset = myDataset;
        this.acListener = acListener;
        this.dcListener = dcListener;
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MeetingAdapter(ArrayList<Meeting> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MeetingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.entry_meeting, parent,false);

        MyViewHolder vh = new MyViewHolder(v,acListener,dcListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.display_name.setText(mDataset.get(position).getTitle());
        holder.display_time.setText(mDataset.get(position).getTimeString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

/*
public class MeetingAdapter extends ArrayAdapter<Meeting> {
    private Activity activity;
    private ArrayList<Meeting> lMeeting;
    private static LayoutInflater inflater = null;

    public MeetingAdapter(Activity activity, int textViewResourceId,ArrayList<Meeting> _lMeeting) {
        super(activity, textViewResourceId, _lMeeting);
        try {
            this.activity = activity;
            this.lMeeting = _lMeeting;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lMeeting.size();
    }

    public Meeting getItem(int position) {
        return lMeeting.get(position);
    }

    public long getItemId(Meeting m) {
        return lMeeting.indexOf(m);
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_time;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.entry_meeting, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.entry_meeting_title);
                holder.display_time = (TextView) vi.findViewById(R.id.entry_meeting_datetime);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(lMeeting.get(position).getTitle());
            holder.display_time.setText(lMeeting.get(position).getTimeString());


        } catch (Exception e) {


        }
        return vi;
    }
}*/