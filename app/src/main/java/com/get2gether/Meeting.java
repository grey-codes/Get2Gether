package com.get2gether;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Meeting implements java.io.Serializable {
    public static final int GRID_ROWS = 14; //starting at 6
    public static final int GRID_COLUMNS = 5; //blank,15,30,45,60
    public static final int GRID_STARTING_HOUR = 6;

    public static final int STATUS_UNCOMFIRMED = 0;
    public static final int STATUS_CONFIRMED = 1;

    private int id;
    private int status;
    private java.util.Date date;
    private String owner;
    private String title;
    private boolean[][] timeGrid;
    private ArrayList<String> participants;
    private String idealTime = "";

    public Meeting(int id, Date date, String owner, String title, String timeString, ArrayList<String> participants) {
        this.id = id;
        this.date = date;
        this.owner = owner;
        this.title = title;
        this.timeGrid = stringToGrid(timeString, GRID_STARTING_HOUR);
        this.participants = participants;
    }

    public Meeting(int id, Date date, String owner, String title, boolean[][] timeGrid, ArrayList<String> participants) {
        this.id = id;
        this.date = date;
        this.owner = owner;
        this.title = title;
        this.timeGrid = timeGrid;
        this.participants = participants;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public static double timeToDouble(String t) {
        List<String> CSV = Arrays.asList(t.split(":"));
        String hour = CSV.get(0).trim();
        String minutes = "0";
        if (CSV.size() > 1) {
            minutes = CSV.get(1).trim();
        }
        try {
            Double.parseDouble(hour);
        } catch (Exception e) {
            return GRID_STARTING_HOUR;
        }
        return Double.parseDouble(hour) + Double.parseDouble(minutes) / 60;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public int[] getDateArray() {
        LocalDate ld = getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int[] ar = new int[3];
        ar[0] = ld.getDayOfMonth();
        ar[1] = ld.getMonthValue();
        ar[2] = ld.getYear();
        return ar;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDay() {
        return Integer.toString(getDateArray()[0]);
    }

    public String getMonth() {
        return Integer.toString(getDateArray()[1]);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static void displayGrid(boolean[][] g) {
        for (int i = 0; i < g.length; i++) {
            boolean[] row = g[i];
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] ? "1    " : "0    ");
            }
            System.out.println();
        }
    }

    public String getYear() {
        return Integer.toString(getDateArray()[2]);
    }

    public static boolean[][] stringToGrid(String str, int startingHour) {
        boolean[] ar1d = new boolean[(GRID_ROWS - 1) * (GRID_COLUMNS - 1)];
        boolean[][] ar2d = new boolean[GRID_ROWS][GRID_COLUMNS];

        String[] CSV = str.split(",");
        for (String timeRangeString : CSV) {
            List<String> range = Arrays.asList(timeRangeString.split("-"));
            String t1 = range.get(0).trim();
            double t1dub = timeToDouble(t1);
            int startIndex = (int) (t1dub * (GRID_COLUMNS - 1)) - startingHour * (GRID_COLUMNS - 1);
            int length;
            if (range.size() > 1) {
                String t2 = range.get(1).trim();
                double t2dub = timeToDouble(t2);
                length = (int) ((t2dub - t1dub) * (GRID_COLUMNS - 1));
            } else { //single value for time (e.g. 15 minute increment)
                length = 1;
            }
            for (int i = startIndex; i < startIndex + length; i++) {
                ar1d[i] = true;
            }
            //System.out.println("Start index: " + startIndex);
            //System.out.println("L: " + length);
        }

        for (int i = 0; i < ar1d.length; i++) {
            int row = (i / (GRID_COLUMNS - 1)) + 1;
            int col = (i % (GRID_COLUMNS - 1)) + 1;
            ar2d[row][col] = ar1d[i];
        }

        return ar2d;
    }

    public static String gridToString(InteractiveRectangle[][] rectangles, int startingHour) {
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
                } else {
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

    public static String gridToString(boolean[][] isSelected, int startingHour) {
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
                } else {
                    //If there is a chain going on when a non-selected square is encountered
                    if (chainedSquares) {
                        time += tempTime + ", ";
                        chainedSquares = false;
                    }
                }
            }
        }

        if (chainedSquares) {
            time += " - " + (startingHour + isSelected.length - 1) + ":00, ";
        }

        //Removes the extra comma at the end of the time string
        time = time.substring(0, Math.max(time.length() - 2, 0));

        return time;
    }

    public String getTimeString() {
        return gridToString(timeGrid, GRID_STARTING_HOUR);
    }

    public void setTimeString(String timeString) {
        this.timeGrid = stringToGrid(timeString, GRID_STARTING_HOUR);
    }

    public boolean[] getTimeRow() {
        boolean[] ar1d = new boolean[(GRID_ROWS - 1) * (GRID_COLUMNS - 1)];
        for (int i = 1; i < this.timeGrid.length; i++) {
            boolean[] row = this.timeGrid[i];
            for (int j = 1; j < row.length; j++) {
                ar1d[(i - 1) * (GRID_COLUMNS - 1) + (j - 1)] = this.timeGrid[i][j];
            }
        }
        return ar1d;
    }

    public void setTimeRow(boolean[] ar1d) {
        for (int i = 0; i < ar1d.length; i++) {
            int row = (i / (GRID_COLUMNS - 1)) + 1;
            int col = (i % (GRID_COLUMNS - 1)) + 1;
            this.timeGrid[row][col] = ar1d[i];
        }
    }

    public boolean[] getTimeRow(boolean[][] ar2d) {
        boolean[] ar1d = new boolean[(GRID_ROWS - 1) * (GRID_COLUMNS - 1)];
        for (int i = 1; i < ar2d.length; i++) {
            boolean[] row = ar2d[i];
            for (int j = 1; j < row.length; j++) {
                ar1d[(i - 1) * (GRID_COLUMNS - 1) + (j - 1)] = ar2d[i][j];
            }
        }
        return ar1d;
    }

    public void setTimeRow(boolean[] ar1d, boolean[][] target) {
        for (int i = 0; i < ar1d.length; i++) {
            int row = (i / (GRID_COLUMNS - 1)) + 1;
            int col = (i % (GRID_COLUMNS - 1)) + 1;
            target[row][col] = ar1d[i];
        }
    }

    public boolean[][] getTimeGrid() {
        return timeGrid;
    }

    public void setTimeGrid(boolean[][] timeGrid) {
        this.timeGrid = timeGrid;
    }

    public void updateIdealTime(boolean[][] andGrid, int span) { //meeting span in blocks
        boolean[] myRow = getTimeRow();
        boolean[] theirRow = getTimeRow(andGrid);
        for (int i = 0; i < myRow.length; i++) {
            myRow[i] = myRow[i] && theirRow[i];
        }

        boolean[][] tmpGrid = new boolean[andGrid.length][andGrid[0].length];
        setTimeRow(myRow, tmpGrid);

        System.out.println("ASDF: " + gridToString(andGrid, GRID_STARTING_HOUR));
        System.out.println("ASDF MINE: " + gridToString(timeGrid, GRID_STARTING_HOUR));
        System.out.println("ASDF TMP: " + gridToString(tmpGrid, GRID_STARTING_HOUR));

        for (int i = 0; i < myRow.length; i++) {
            boolean succ = true;
            for (int j = 0; j < span; j++) {
                if (!myRow[i + j]) {
                    succ = false;
                    break;
                }
            }
            if (succ) {
                boolean[] succAr1d = new boolean[myRow.length];
                boolean[][] succAr2d = new boolean[andGrid.length][andGrid[0].length];
                for (int j = i; j < i + span; j++) {
                    succAr1d[j] = true;
                }
                setTimeRow(succAr1d, succAr2d);

                setIdealTime(gridToString(succAr2d, GRID_STARTING_HOUR));
                return;
            }
        }
        setIdealTime("N/A");
    }

    public void updateIdealTime(boolean[][] andGrid) {
        updateIdealTime(andGrid, 4);
    }

    public String getIdealTime() {
        return idealTime;
    }

    public void setIdealTime(String idealTime) {
        this.idealTime = idealTime;
    }
}
