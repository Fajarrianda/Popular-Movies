package com.kursigoyang.popularmovie;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fajar Rianda on 24/07/2017.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "movie.db";
  private static final int DATABASE_VERSION = 1;

  public SQLiteHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version) {
    super(context, DATABASE_NAME, factory, DATABASE_VERSION);
  }

  public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version, DatabaseErrorHandler errorHandler) {
    super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE "
        + MovieContract.MovieEntry.TABLE_NAME
        + " ("
        + MovieContract.MovieEntry._ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + MovieContract.MovieEntry.COLUMN_NAME_ID + " TEXT NOT NULL, "
        + MovieContract.MovieEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, "
        + MovieContract.MovieEntry.COLUMN_FAVOURITED + " INTEGER NOT NULL" +");";

    sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }
}
