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
}
