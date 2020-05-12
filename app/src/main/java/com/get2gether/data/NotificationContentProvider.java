package com.get2gether.data;

/**
 * Created by grey on 5/9/2020.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.get2gether.R;


public class NotificationContentProvider extends ContentProvider {
    // UriMatcher helps ContentProvider determine operation to perform
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);
    // constants used with UriMatcher to determine operation to perform
    private static final int ONE_NOTIF = 1; // manipulate one recipe
    private static final int NOTIFS = 2; // manipulate recipes table

    // static block to configure this ContentProvider's UriMatcher
    static {
        // Uri for Recipe with the specified id (#)
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Notification.TABLE_NAME + "/#", ONE_NOTIF);

        // Uri for Recipes table
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Notification.TABLE_NAME, NOTIFS);
    }

    // used to access the database
    private NotificationDatabaseHelper dbHelper;

    // called when the AddressBookContentProvider is created
    @Override
    public boolean onCreate() {
        // create the AddressBookDatabaseHelper
        dbHelper = new NotificationDatabaseHelper(getContext());
        return true; // ContentProvider successfully created
    }

    // required method: Not used in this app, so we return null
    @Override
    public String getType(Uri uri) {
        return null;
    }

    // query the database
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        // create SQLiteQueryBuilder for querying recipes table
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseDescription.Notification.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ONE_NOTIF: // recipe with specified id will be selected
                queryBuilder.appendWhere(
                        DatabaseDescription.Notification._ID + "=" + uri.getLastPathSegment());
                break;
            case NOTIFS: // all recipes will be selected
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_query_uri) + uri);
        }

        // execute the query to select one or all recipes
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);

        // configure to watch for content changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // insert a new recipe in the database
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newRecipeUri = null;

        switch (uriMatcher.match(uri)) {
            case NOTIFS:
                // insert the new recipe--success yields new recipe's row id
                long rowId = dbHelper.getWritableDatabase().insert(
                        DatabaseDescription.Notification.TABLE_NAME, null, values);

                // if the recipe was inserted, create an appropriate Uri;
                // otherwise, throw an exception
                if (rowId > 0) { // SQLite row IDs start at 1
                    newRecipeUri = DatabaseDescription.Notification.buildRecipeUri(rowId);

                    // notify observers that the database changed
                    getContext().getContentResolver().notifyChange(uri, null);
                } else
                    throw new SQLException(
                            getContext().getString(R.string.insert_failed) + uri);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_insert_uri) + uri);
        }

        return newRecipeUri;
    }

    // update an existing recipe in the database
    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int numberOfRowsUpdated; // 1 if update successful; 0 otherwise

        switch (uriMatcher.match(uri)) {
            case ONE_NOTIF:
                // get from the uri the id of recipe to update
                String id = uri.getLastPathSegment();

                // update the recipe
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                        DatabaseDescription.Notification.TABLE_NAME, values, DatabaseDescription.Notification._ID + "=" + id,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_update_uri) + uri);
        }

        // if changes were made, notify observers that the database changed
        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsUpdated;
    }

    // delete an existing recipe from the database
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numberOfRowsDeleted;

        switch (uriMatcher.match(uri)) {
            case ONE_NOTIF:
                // get from the uri the id of recipe to update
                String id = uri.getLastPathSegment();

                // delete the recipe
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.Notification.TABLE_NAME, DatabaseDescription.Notification._ID + "=" + id, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_delete_uri) + uri);
        }

        // notify observers that the database changed
        if (numberOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }


}
