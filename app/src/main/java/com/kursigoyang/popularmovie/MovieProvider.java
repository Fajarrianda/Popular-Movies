package com.kursigoyang.popularmovie;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Fajar Rianda on 31/07/2017.
 */
public class MovieProvider extends ContentProvider {

  public static final int MOVIES = 100;
  public static final int MOVIES_WITH_ID = 101;
  private static final UriMatcher URI_MATCHER = buildUriMatcher();

  public SQLiteHelper sqLiteHelper;

  public static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /* directory */
    uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);

    /* single item */
    uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES+"/#", MOVIES_WITH_ID);


    return uriMatcher;
  }

  @Override public boolean onCreate() {

    Context context = getContext();
    sqLiteHelper = new SQLiteHelper(context);

    return true;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    final SQLiteDatabase sqLiteDatabase = sqLiteHelper.getReadableDatabase();

    int match = URI_MATCHER.match(uri);

    Cursor cursor;

    switch (match) {
      case MOVIES:
        cursor = sqLiteDatabase.query(MovieContract.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        break;
      case MOVIES_WITH_ID:
        String id = uri.getPathSegments().get(1);
        String where = "_id=?";
        String[] args = new String[]{id};
        cursor = sqLiteDatabase.query(MovieContract.TABLE_NAME,projection,where,args,null,null,null);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    cursor.setNotificationUri(getContext().getContentResolver(),uri);
    return cursor;
  }

  @Nullable @Override public String getType(Uri uri) {
    return null;
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues contentValues) {
    SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();

    int match = URI_MATCHER.match(uri);

    Uri returnUri;

    switch (match) {
      case MOVIES:
        long id = sqLiteDatabase.insert(MovieContract.TABLE_NAME,null,contentValues);
        if (id > 0){
          returnUri = ContentUris.withAppendedId(MovieContract.CONTENT_URI, id);
        } else {
          throw new android.database.SQLException("Failed to insert row into : "+ uri);
        }
        break;
      default: throw new UnsupportedOperationException("Unknown uri: "+ uri);
    }

    getNotifyResolver(uri);
    return returnUri;
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();

    int match = URI_MATCHER.match(uri);

    switch (match) {
      case MOVIES: {
        match = sqLiteDatabase.delete(MovieContract.TABLE_NAME,
            selection,
            selectionArgs);
        break;
      }
      default: {
        throw new UnsupportedOperationException("Unknown insert uri: " + uri);
      }
    }
    getNotifyResolver(uri);
    return match;
  }

  @Override public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
    SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();

    int match = URI_MATCHER.match(uri);

    switch (match) {
      case MOVIES: {
        match = sqLiteDatabase.update(MovieContract.TABLE_NAME,contentValues,
            selection,
            selectionArgs);
        break;
      }
      default: {
        throw new UnsupportedOperationException("Unknown insert uri: " + uri);
      }
    }
    getNotifyResolver(uri);
    return match;
  }

  private void getNotifyResolver(Uri uri) {
    getContext().getContentResolver().notifyChange(uri, null);
  }
}
