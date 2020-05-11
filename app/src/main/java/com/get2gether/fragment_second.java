package com.get2gether;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_second#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_second extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_ACTION = "action";
    public static final String ARG_PASSTHROUGH = "pass";

    public static final int ACTION_DEFAULT = 0;
    public static final int ACTION_MAKEMEETING = 1;
    public static final int ACTION_CONFMEETING = 2;


    private InteractiveRectangle[][] rectangles = new InteractiveRectangle[14][5];

    // TODO: Rename and change types of parameters
    private int action;
    private ArrayList<String> pass;

    public fragment_second() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_second.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_second newInstance(int param1, ArrayList<String> param2) {
        fragment_second fragment = new fragment_second();
        Bundle args = new Bundle();
        args.putInt(ARG_ACTION, param1);
        args.putStringArrayList(ARG_PASSTHROUGH, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            action = getArguments().getInt(ARG_ACTION);
            pass = getArguments().getStringArrayList(ARG_PASSTHROUGH);
        }
    }

    public boolean[][] getSelectedItems() {
        int rows, cols;
        rows = this.rectangles.length;
        cols = this.rectangles[0].length;
        boolean[][] res = new boolean[rows][cols];
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                res[i][j] = rectangles[i][j].getSelected();
            }
        }
        return res;
    }

    boolean status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_second, container, false);

        GridLayout layout = (GridLayout) v.findViewById(R.id.grid);


        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int totalWidth = layout.getMeasuredWidth();
                int totalHeight = layout.getMeasuredHeight();






                for (int a = 0; a < rectangles.length; a++) {
                    rectangles[a][0] = new InteractiveRectangle(getContext(), 0, 0, totalWidth / 5, totalHeight / 14, 4, android.graphics.Color.argb(255, 32, 34, 37), android.graphics.Color.argb(255, 112, 48, 160), android.graphics.Color.argb(255, 112, 48, 160));
                    rectangles[a][0].setCanBeToggled(false);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    params.rowSpec = GridLayout.spec(a);
                    params.columnSpec = GridLayout.spec(0);
                    rectangles[a][0].setLayoutParams(params);
                    layout.addView(rectangles[a][0]);
                }



                for (int b = 1; b < rectangles[0].length; b++) {
                    rectangles[0][b] = new InteractiveRectangle(getContext(), 0, 0, totalWidth / 5, totalHeight / 14, 4, android.graphics.Color.argb(255, 32, 34, 37), android.graphics.Color.argb(255, 112, 48, 160), android.graphics.Color.argb(255, 112, 48, 160));
                    rectangles[0][b].setCanBeToggled(false);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    params.rowSpec = GridLayout.spec(0);
                    params.columnSpec = GridLayout.spec(b);
                    rectangles[0][b].setLayoutParams(params);
                    layout.addView(rectangles[0][b]);
                }

                for (int a = 1; a < rectangles.length; a++) {
                    for (int b = 1; b < rectangles[a].length; b++) {
                        rectangles[a][b] = new InteractiveRectangle(getContext(), 0, 0, totalWidth / 5, totalHeight / 14, 4, android.graphics.Color.argb(255, 32, 34, 37), android.graphics.Color.argb(255, 84, 160, 53), android.graphics.Color.argb(255, 95, 97, 110));
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                        params.rowSpec = GridLayout.spec(a);
                        params.columnSpec = GridLayout.spec(b);
                        rectangles[a][b].setLayoutParams(params);
                        layout.addView(rectangles[a][b]);
                    }
                }

                rectangles[0][1].setText(":00");
                rectangles[0][2].setText(":15");
                rectangles[0][3].setText(":30");
                rectangles[0][4].setText(":45");
                rectangles[1][0].setText("6ₐₘ");
                rectangles[2][0].setText("7");
                rectangles[3][0].setText("8");
                rectangles[4][0].setText("9");
                rectangles[5][0].setText("10");
                rectangles[6][0].setText("11");
                rectangles[7][0].setText("12ₚₘ");
                rectangles[8][0].setText("1");
                rectangles[9][0].setText("2");
                rectangles[10][0].setText("3");
                rectangles[11][0].setText("4");
                rectangles[12][0].setText("5");
                rectangles[13][0].setText("6");



                layout.setOnTouchListener( (View.OnTouchListener) (view, event) -> {
                    GridLayout layout1 = (GridLayout)view;

                    for(int i = 0; i< layout1.getChildCount(); i++) {

                        View rect = layout1.getChildAt(i);
                        Rect outRect = new Rect(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom());
                        if(outRect.contains((int)event.getX(), (int)event.getY()) && rect instanceof InteractiveRectangle) {
                            // over a View
                            InteractiveRectangle irect = (InteractiveRectangle) rect;
                            if (event.getActionMasked()==MotionEvent.ACTION_DOWN) {
                                status = !irect.getSelected();
                            }
                            irect.setSelected(status);
                        }
                    }
                    return true;
                });
            }
        });

        Button goToSuccess = v.findViewById(R.id.goToSuccess);

        switch(action) {
            case ACTION_MAKEMEETING:
                goToSuccess.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(fragment_meetingsuccess.ARG_RECTANGLES,getSelectedItems());
                    bundle.putStringArrayList(fragment_meetingsuccess.ARG_PASSTHROUGH,pass);
                    NavHostFragment.findNavController(fragment_second.this)
                            .navigate(R.id.action_SecondFragment_to_fragment_meetingsuccess,bundle);
                });
                break;
            case ACTION_CONFMEETING:
                goToSuccess.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(fragment_meetingconfirmed.ARG_RECTANGLES,getSelectedItems());
                    bundle.putStringArrayList(fragment_meetingconfirmed.ARG_PASSTHROUGH,pass);
                    NavHostFragment.findNavController(fragment_second.this)
                            .navigate(R.id.action_SecondFragment_to_fragment_meetingconfirmed,bundle);
                });
                break;
        }

        return v;
    }

}
