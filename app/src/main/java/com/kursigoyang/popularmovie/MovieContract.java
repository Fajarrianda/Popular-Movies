package com.kursigoyang.popularmovie;

import android.provider.BaseColumns;

/**
 * Created by Fajar Rianda on 24/07/2017.
 */
public class MovieContract {

  private MovieContract() {};

  public static class MovieEntry implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final String COLUMN_NAME_TITLE = "movie_title";
    public static final String COLUMN_NAME_ID = "movie_id";
    public static final String COLUMN_FAVOURITED = "favourited";

  }
}
