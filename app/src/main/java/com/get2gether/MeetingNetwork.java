package com.get2gether;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeetingNetwork {
    private static final String newEndpoint = "https://google.com/search?q=";
    private static final String retrieveEndpoint = "https://greycodes.net/lab/get2gether/jsontest.php";
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;
    private ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    private Gson gson = new Gson();
    private GoogleSignInAccount googleAccount;

    public MeetingNetwork(GoogleSignInAccount acc) {
        this.googleAccount = acc;
    }

    public void createMeeting(Meeting m) {
        executorService.execute(new CreateMeetingRunnable(m));
    }

    public void getMeetings(MeetingListener cb) {
        executorService.execute(new GetMeetingsRunnable(cb));
    }

    class CreateMeetingRunnable implements Runnable {
        Meeting meeting;

        CreateMeetingRunnable(Meeting m) {
            meeting = m;
        }

        @Override
        public void run() {
            try {
                String jsonToPost = gson.toJson(meeting);
                URL url = new URL(newEndpoint + jsonToPost);
                System.out.println(url);
                System.out.println(googleAccount.getEmail());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    class GetMeetingsRunnable implements Runnable {
        MeetingListener callback;

        GetMeetingsRunnable(MeetingListener callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(retrieveEndpoint);
                URLConnection dc = url.openConnection();
                BufferedReader inputStream = null;

                dc.setConnectTimeout(5000);
                dc.setReadTimeout(5000);

                BufferedReader r = new BufferedReader(new InputStreamReader(dc.getInputStream()));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                String json = total.toString();

                System.out.println("JSON: " + json);
                Meeting[] meetings = gson.fromJson(json, Meeting[].class);
                ArrayList<Meeting> meetingList = new ArrayList<>(Arrays.asList(meetings));
                callback.onMeetingsParse(meetingList);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
