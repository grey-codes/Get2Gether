package com.get2gether;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MeetingNetwork {
    private static final String newEndpoint = "https://greycodes.net/lab/get2gether/createmeeting.php";
    private static final String accEndpoint = "https://greycodes.net/lab/get2gether/acceptmeeting.php";
    private static final String decEndpoint = "https://greycodes.net/lab/get2gether/declinemeeting.php";
    private static final String retrieveEndpoint = "https://greycodes.net/lab/get2gether/getmeetings.php";
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;
    private ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    private Gson gson = new Gson();
    private GoogleSignInAccount googleAccount;

    MeetingNetwork(GoogleSignInAccount acc) {
        this.googleAccount = acc;
    }

    void acceptMeeting(Meeting m) {
        executorService.execute(new AcceptMeetingRunnable(m));
    }

    void createMeeting(Meeting m) {
        executorService.execute(new CreateMeetingRunnable(m));
    }

    void declineMeeting(Meeting m) {
        executorService.execute(new DeleteMeetingRunnable(m));
    }

    void getMeetings(MeetingListListener cb) {
        executorService.execute(new GetMeetingsRunnable(cb));
    }

    class AcceptMeetingRunnable implements Runnable {
        Meeting meeting;

        AcceptMeetingRunnable(Meeting m) {
            meeting = m;
        }

        @Override
        public void run() {
            try {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("google_id", googleAccount.getId());
                hm.put("google_token", googleAccount.getServerAuthCode());
                hm.put("meeting_json", gson.toJson(meeting));

                String jsonToPost = gson.toJson(hm);

                URL url = new URL(accEndpoint);
                System.out.println(jsonToPost);
                System.out.println(googleAccount.getEmail());

                HttpURLConnection dc = (HttpURLConnection) url.openConnection();

                dc.setRequestMethod("POST");
                dc.setRequestProperty("Content-Type", "application/json; utf-8");
                dc.setRequestProperty("Accept", "application/json");

                dc.setConnectTimeout(5000);
                dc.setReadTimeout(5000);
                dc.setDoOutput(true);

                try (OutputStream os = dc.getOutputStream()) {
                    byte[] input = jsonToPost.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                BufferedReader r = new BufferedReader(new InputStreamReader(dc.getInputStream()));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                r.close();
                String json = total.toString();

                System.out.println("JSON: " + json);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class CreateMeetingRunnable implements Runnable {
        Meeting meeting;

        CreateMeetingRunnable(Meeting m) {
            meeting = m;
        }

        @Override
        public void run() {
            try {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("google_id", googleAccount.getId());
                hm.put("google_token", googleAccount.getServerAuthCode());
                hm.put("meeting_json", gson.toJson(meeting));

                String jsonToPost = gson.toJson(hm);

                URL url = new URL(newEndpoint);
                System.out.println(jsonToPost);
                System.out.println(googleAccount.getEmail());

                HttpURLConnection dc = (HttpURLConnection) url.openConnection();

                dc.setRequestMethod("POST");
                dc.setRequestProperty("Content-Type", "application/json; utf-8");
                dc.setRequestProperty("Accept", "application/json");

                dc.setConnectTimeout(5000);
                dc.setReadTimeout(5000);
                dc.setDoOutput(true);

                try (OutputStream os = dc.getOutputStream()) {
                    byte[] input = jsonToPost.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                BufferedReader r = new BufferedReader(new InputStreamReader(dc.getInputStream()));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                r.close();
                String json = total.toString();

                System.out.println("JSON: " + json);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class DeleteMeetingRunnable implements Runnable {
        Meeting meeting;

        DeleteMeetingRunnable(Meeting m) {
            meeting = m;
        }

        @Override
        public void run() {
            try {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("google_id", googleAccount.getId());
                hm.put("google_token", googleAccount.getServerAuthCode());
                hm.put("meeting_json", gson.toJson(meeting));

                String jsonToPost = gson.toJson(hm);

                URL url = new URL(decEndpoint);
                System.out.println(jsonToPost);
                System.out.println(googleAccount.getEmail());

                HttpURLConnection dc = (HttpURLConnection) url.openConnection();

                dc.setRequestMethod("POST");
                dc.setRequestProperty("Content-Type", "application/json; utf-8");
                dc.setRequestProperty("Accept", "application/json");

                dc.setConnectTimeout(5000);
                dc.setReadTimeout(5000);
                dc.setDoOutput(true);

                try (OutputStream os = dc.getOutputStream()) {
                    byte[] input = jsonToPost.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                BufferedReader r = new BufferedReader(new InputStreamReader(dc.getInputStream()));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                r.close();
                String json = total.toString();

                System.out.println("JSON: " + json);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class GetMeetingsRunnable implements Runnable {
        MeetingListListener callback;

        GetMeetingsRunnable(MeetingListListener callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(retrieveEndpoint);
                HttpURLConnection dc = (HttpURLConnection) url.openConnection();

                dc.setRequestMethod("POST");
                dc.setRequestProperty("Content-Type", "application/json; utf-8");
                dc.setRequestProperty("Accept", "application/json");

                dc.setConnectTimeout(5000);
                dc.setReadTimeout(5000);
                dc.setDoOutput(true);

                HashMap<String, String> hm = new HashMap<>();
                hm.put("google_id", googleAccount.getId());
                hm.put("google_token", googleAccount.getServerAuthCode());

                String jsonToPost = gson.toJson(hm);

                try (OutputStream os = dc.getOutputStream()) {
                    byte[] input = jsonToPost.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                BufferedReader r = new BufferedReader(new InputStreamReader(dc.getInputStream()));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                r.close();
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
