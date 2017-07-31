package com.kursigoyang.popularmovie;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fajar Rianda on 24/07/2017.
 */
public class MovieContract implements BaseColumns {

  public static final String CONTENT_AUTHORITY = "com.kursigoyang.popularmovie";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  public static final String PATH_MOVIES = "movies";
  public static final Uri CONTENT_URI =
      BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
  public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
  /**
   * This is a String type that denotes a Uri references a single item.
   */
  public static final String CONTENT_ITEM_TYPE =
      ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
  public static final String TABLE_NAME = "movie";
  public static final String COLUMN_NAME_ID = "movie_id";
  public static final String COLUMN_NAME_TITLE = "title";
  public static final String COLUMN_FAVOURITED = "favourited";
  public static final String COLUMN_VOTE_COUNT = "vote_count";
  public static final String COLUMN_VOTE_AVERAGE = "vote_average";
  public static final String COLUMN_POSTER_PATH = "poster_path";
  public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
  public static final String COLUMN_OVERVIEW = "overview";
  public static final String COLUMN_RELEASE_DATE = "release_date";

  public static final String[] movieProjection = new String[] {
      MovieContract.COLUMN_NAME_ID, MovieContract.COLUMN_NAME_TITLE,
      MovieContract.COLUMN_FAVOURITED, MovieContract.COLUMN_VOTE_COUNT,
      MovieContract.COLUMN_VOTE_AVERAGE, MovieContract.COLUMN_POSTER_PATH,
      MovieContract.COLUMN_BACKDROP_PATH, MovieContract.COLUMN_OVERVIEW,
      MovieContract.COLUMN_RELEASE_DATE
  };

  private MovieContract() {
  }

  public static Uri buildTermUriWithId(long id) {
    return ContentUris.withAppendedId(CONTENT_URI, id);
  }
}
