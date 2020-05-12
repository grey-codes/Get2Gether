package com.get2gether.data;

/**
 * Created by grey on 5/9/2020.
 */

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {
    // ContentProvider's name: typically the package name
    public static final String AUTHORITY =
            "com.get2gether.data";

    // base URI used to interact with the ContentProvider
    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    // nested class defines contents of the recipes table
    public static final class Notification implements BaseColumns {
        public static final String TABLE_NAME = "notificationLog"; // table's name

        // Uri for the recipes table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for recipes table's columns
        public static final String COLUMN_ID = "remoteid";

        // creates a Uri for a specific recipe
        public static Uri buildRecipeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
