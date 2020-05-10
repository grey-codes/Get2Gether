package com.get2gether;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
}