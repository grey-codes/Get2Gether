package com.get2gether;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment {
    private MainActivity parentActivity;
    private TextView welcomeText;
    private Button loginButton;
    private Button goToSecondFrag;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentActivity = (MainActivity) getActivity();

        welcomeText = view.findViewById(R.id.welcomeView);
        loginButton = view.findViewById(R.id.button_login);
        goToSecondFrag = view.findViewById(R.id.goToSecondFrag);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.prompt_Login);
            }
        });
        goToSecondFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.goToMakeMeeting);
            }
        });

        updateLoginUI();
    }

    private void updateLoginUI() {
        if (parentActivity.googleAccount == null) {
            welcomeText.setVisibility(View.INVISIBLE);
            goToSecondFrag.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        } else {
            welcomeText.setVisibility(View.VISIBLE);
            goToSecondFrag.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            welcomeText.setText(getString(R.string.welcome, parentActivity.googleAccount.getDisplayName()));
        }
        parentActivity.updateLoginUI();
    }
}
