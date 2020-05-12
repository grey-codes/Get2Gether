package com.get2gether;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    public MeetingNetwork meetingNetwork;

    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInAccount googleAccount;
    NavigationView nv;
    FragmentContainerView fcv;
    private ActionBarDrawerToggle t;

    public static final int ACTION_NONE = 0;
    public static final int ACTION_VIEW_MEETINGS = 1;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = ("AAAAA");
        String description = ("aaaaaaaaaaaaa");
        int importance = NotificationManager.IMPORTANCE_MAX;
        NotificationChannel channel = new NotificationChannel("AAAAA", name, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomNotifications.setupAlarm(getApplicationContext());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        meetingNetwork = new MeetingNetwork(googleAccount);

        DrawerLayout dl = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);

        t.syncState();

        fcv = findViewById(R.id.nav_host_fragment);

        nv = findViewById(R.id.nav_view);


        nv.setNavigationItemSelectedListener(item -> {
            Toast signInToast = Toast.makeText(this.getBaseContext(), "Please sign in!", Toast.LENGTH_LONG);
            int id = item.getItemId();
            switch (id) {
                case R.id.schedule_meeting:
                    if (googleAccount != null)
                        Navigation.findNavController(fcv).navigate(R.id.action_global_fragment_makemeeting);
                    else
                        signInToast.show();
                    dl.closeDrawers();
                    break;
                case R.id.view_meetings:
                    if (googleAccount != null)
                        Navigation.findNavController(fcv).navigate(R.id.action_global_viewMeetingFragment);
                    else
                        signInToast.show();
                    dl.closeDrawers();
                    break;
                case R.id.settings:
                    Navigation.findNavController(fcv).navigate(R.id.action_global_mySettingsFragment);
                    dl.closeDrawers();
                    break;
                case R.id.menu_sign_in_out:
                    if (googleAccount != null)
                        signOut();
                    else
                        Navigation.findNavController(fcv).navigate(R.id.prompt_Login);
                    dl.closeDrawers();
                    break;
                default:
                    return true;
            }


            return true;

        });

        int act = getIntent().getIntExtra("act",ACTION_NONE);
        switch (act) {
            case ACTION_VIEW_MEETINGS:
                Navigation.findNavController(fcv).navigate(R.id.action_global_viewMeetingFragment);
                break;
            default:
                break;
        }

        updateLoginUI();
    }

    public void updateLoginUI() {
        MenuItem signOutItem = nv.getMenu().findItem(R.id.menu_sign_in_out);
        signOutItem.setTitle((googleAccount == null) ? getString(R.string.sign_in) : getString(R.string.sign_out));
    }

    private void signOut() {
        final Activity act = this;
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    googleAccount = null;
                    Toast.makeText(MainActivity.this, "Signed Out...", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(fcv).navigate(R.id.action_goHome);
                    updateLoginUI();
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Navigation.findNavController(fcv).navigate(R.id.action_global_mySettingsFragment);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
