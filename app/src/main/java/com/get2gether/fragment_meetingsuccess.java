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
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_meetingsuccess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_meetingsuccess extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_RECTANGLES = "rectangles";
    public static final String ARG_PASSTHROUGH = fragment_second.ARG_PASSTHROUGH;

    // TODO: Rename and change types of parameters
    private boolean[][] rectangles;
    private HashMap<String,String> passthrough;

    public fragment_meetingsuccess() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_meetingsuccess.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_meetingsuccess newInstance(Serializable param1, ArrayList<String> param2) {
        fragment_meetingsuccess fragment = new fragment_meetingsuccess();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECTANGLES, param1);
        args.putSerializable(ARG_PASSTHROUGH, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rectangles=null;
        passthrough=null;
        if (getArguments() != null) {
            rectangles = (boolean[][]) getArguments().getSerializable(ARG_RECTANGLES);
            ArrayList<String> pass = getArguments().getStringArrayList(ARG_PASSTHROUGH);
            System.out.println(toTime(rectangles, 6));

            passthrough=new HashMap<>();
            String key,value;
            key=null;
            value=null;
            for (String s: pass) {
                if (key==null) {
                    key = s;
                }
                else if (value==null) {
                    value=s;
                    System.out.println(key+"||"+value);
                    passthrough.put(key,value);
                    key=null;
                    value=null;
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_meetingsuccess, container, false);

        Button goToMainPage = v.findViewById(R.id.goToMainPage);
        goToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fragment_meetingsuccess.this)
                        .navigate(R.id.action_fragment_meetingsuccess_to_FirstFragment);
            }
        });

        if (passthrough!=null) {
            if (passthrough.containsKey("name"));
            String title = passthrough.get("name");
            TextView tv = v.findViewById(R.id.textView4);
            tv.setText(getString(R.string.meeting_succ,title));
        }

        return v;
    }

    public static String toTime(InteractiveRectangle[][] rectangles, int startingHour) {
        String time = "";
        String tempTime = "";
        boolean chainedSquares = false;


        //Loops through each square within the table and converts it to a string that lists the available time
        for (int a = 0; a < rectangles.length; a++) {
            for (int b = 0; b < rectangles[a].length; b++) {
                if (rectangles[a][b].getSelected()) {
                    //If there is not chain going on when a selected square is encountered
                    if (!chainedSquares) {
                        time += startingHour + (a - 1) + ":" + ((b - 1) * 15);
                        chainedSquares = true;
                    }
                    //If there is a chain going on when a selected square is encountered
                    else {
                        tempTime = " - " + startingHour + (a - 1) + ":" + ((b - 1) * 15);
                    }
                }
                else {
                    //If there is a chain going on when a non-selected square is encountered
                    if (chainedSquares) {
                        time += tempTime + ", ";
                        chainedSquares = false;
                    }
                }
            }
        }

        //Removes the extra comma at the end of the time string
        time = time.substring(0, Math.max(time.length() - 2, 0));

        return time;
    }

    public static String toTime(boolean[][] isSelected, int startingHour) {
        String time = "";
        String tempTime = "";
        boolean chainedSquares = false;


        //Loops through each square within the table and converts it to a string that lists the available time
        for (int a = 1; a < isSelected.length; a++) {
            for (int b = 1; b < isSelected[a].length; b++) {
                if (isSelected[a][b]) {
                    //If there is not chain going on when a selected square is encountered
                    if (!chainedSquares) {
                        //If the min time should end in 00
                        if ((b - 1) == 0) {
                            time += startingHour + (a - 1) + ":00";
                        }
                        //If the min time should end in a 15, 30, or 45
                        else if ((b - 1) * 15 < 60) {
                            time += startingHour + (a - 1) + ":" + ((b - 1) * 15);
                        }
                        //If the min time reaches 60 and should reset and increase the hour by 1
                        else {
                            time += startingHour + a + ":00";
                        }

                        chainedSquares = true;
                    }
                    //If there is a chain going on when a selected square is encountered
                    else {
                        if (b == 0) {
                            tempTime = " - " + (startingHour + (a - 1)) + ":00";
                        }
                        //If the min time should end in a 15, 30, or 45
                        else if (b * 15 < 60) {
                            tempTime = " - " + (startingHour + (a - 1)) + ":" + (b * 15);
                        }
                        //If the min time reaches 60 and should reset and increase the hour by 1
                        else {
                            tempTime = " - " + (startingHour + a) + ":00";
                        }
                    }
                }
                else {
                    //If there is a chain going on when a non-selected square is encountered
                    if (chainedSquares) {
                        time += tempTime + ", ";
                        chainedSquares = false;
                    }
                }
            }
        }

        //Removes the extra comma at the end of the time string
        time = time.substring(0, Math.max(time.length() - 2, 0));

        return time;
    }
}
