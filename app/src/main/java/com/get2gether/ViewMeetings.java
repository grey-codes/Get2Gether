package com.get2gether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ViewMeetings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetings);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("act",MainActivity.ACTION_VIEW_MEETINGS);

        startActivity(i);
    }
}
