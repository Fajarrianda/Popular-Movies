package com.kursigoyang.popularmovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.kursigoyang.popularmovie.constant.URLCons;
import com.kursigoyang.popularmovie.model.Movie;
import com.kursigoyang.popularmovie.model.Trailer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fajar Rianda on 26/06/2017.
 */
public class MovieDetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks, TrailerAdapter.ListItemClickListener {

  private static final int MOVIE_LOADER_ID = 0;
  @Bind(R.id.txtMovieTitle) TextView txtMovieTitle;
  @Bind(R.id.txtSynopsis) TextView txtSynopsis;
  @Bind(R.id.txtYear) TextView txtYear;
  @Bind(R.id.txtRating) TextView txtRating;
  @Bind(R.id.btnFavourite) Button btnFavourite;
  @Bind(R.id.imgMovie) ImageView imgContent;
  @Bind(R.id.rvTrailer) RecyclerView rvTrailer;
  @Bind(R.id.rvReview) RecyclerView rvReview;
  TrailerAdapter trailerAdapter;
  ReviewAdapter reviewAdapter;
  //SQLiteDatabase sqLiteDatabase;
  Movie movie;

  public static void start(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieDetailActivity.class);
    intent.putExtra(Movie.class.getSimpleName(), movie);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    ButterKnife.bind(this);
    init(savedInstanceState);
    setupToolbar();
    //
    //SQLiteHelper dbHelper = new SQLiteHelper(this);
    //sqLiteDatabase = dbHelper.getWritableDatabase();
    isFavourited();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (movie != null) {
      outState.putParcelable(Movie.class.getSimpleName(), movie);
    }
  }

  private void init(Bundle savedInstanceState) {

    if (savedInstanceState != null) {
      movie = savedInstanceState.getParcelable(Movie.class.getSimpleName());
    } else {
      movie = getIntent().getParcelableExtra(Movie.class.getSimpleName());
    }

    rvTrailer.setHasFixedSize(true);
    rvTrailer.setLayoutManager(new LinearLayoutManager(this));

    trailerAdapter = new TrailerAdapter(this);
    rvTrailer.setAdapter(trailerAdapter);

    trailerAdapter.setOnListItemClickListener(this);

    rvReview.setHasFixedSize(true);
    rvReview.setLayoutManager(new LinearLayoutManager(this));

    reviewAdapter = new ReviewAdapter(this);
    rvReview.setAdapter(reviewAdapter);

    setupContent(movie);
    loadVideoTrailer(movie.getId());
    loadReview(movie.getId());
  }

  private void setupToolbar() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void setupContent(Movie movie) {
    txtMovieTitle.setText(movie.getTitle());
    txtSynopsis.setText(movie.getOverview());
    txtRating.setText(String.valueOf(movie.getVoteAverage()).concat("/10"));
    txtYear.setText(movie.getReleaseDate().substring(0, 4));
    Glide.with(this).load(URLCons.URL_BASE_GET_IMAGE.concat(movie.getPoster())).into(imgContent);
  }

  private void loadVideoTrailer(int movieId) {
    AppClient.createService(ApiService.class)
        .getVideos(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          trailerAdapter.pushData(object.getResult());
        }, error -> {
          ErrorHelper.thrown(this, error, false);
        });
  }

  private void loadReview(int movieId) {
    AppClient.createService(ApiService.class)
        .getReviews(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          reviewAdapter.pushData(object.getResult());
        }, error -> {
          ErrorHelper.thrown(this, error, false);
        });
  }

  private void addFavourite() {
    ContentValues cv = new ContentValues();
    cv.put(MovieContract.COLUMN_NAME_ID, movie.getId());
    cv.put(MovieContract.COLUMN_NAME_TITLE, movie.getTitle());
    cv.put(MovieContract.COLUMN_FAVOURITED, 1);
    cv.put(MovieContract.COLUMN_VOTE_COUNT, movie.getVoteCount());
    cv.put(MovieContract.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
    cv.put(MovieContract.COLUMN_POSTER_PATH, movie.getPoster());
    cv.put(MovieContract.COLUMN_BACKDROP_PATH, movie.getBackdrop());
    cv.put(MovieContract.COLUMN_OVERVIEW, movie.getOverview());
    cv.put(MovieContract.COLUMN_RELEASE_DATE, movie.getReleaseDate());

    //sqLiteDatabase.insert(MovieContract.TABLE_NAME, null, cv);
    Uri uri = getContentResolver().insert(MovieContract.CONTENT_URI, cv);

    btnFavourite.setSelected(true);
    btnFavourite.setTextColor(ContextCompat.getColor(this, android.R.color.white));
  }

  private void isFavourited() {

    final String where = MovieContract.COLUMN_NAME_ID + "=?";
    final String[] args = new String[] { String.valueOf(movie.getId()) };
    Cursor cursor =
        getContentResolver().query(MovieContract.CONTENT_URI, MovieContract.movieProjection, where,
            args, null, null);

    if (cursor != null) {
      if (cursor.getCount() > 0) {
        cursor.moveToFirst();
        int favourited = cursor.getInt(cursor.getColumnIndex(MovieContract.COLUMN_FAVOURITED));
        btnFavourite.setSelected(favourited != 0);
        btnFavourite.setTextColor(favourited != 0
            ? ContextCompat.getColor(this, android.R.color.white)
            : ContextCompat.getColor(this, android.R.color.black));
      }
    }
    cursor.close();
  }

  private void deleteFavourite() {

    getContentResolver().delete(MovieContract.CONTENT_URI,
        MovieContract.COLUMN_NAME_ID + "=" + movie.getId(), null);
    getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
    btnFavourite.setSelected(false);
    btnFavourite.setTextColor(ContextCompat.getColor(this, android.R.color.black));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onListItemClick(int clickedItemIndex, Trailer trailer) {
    Uri webpage = Uri.parse("https://www.youtube.com/watch?v=".concat(trailer.getKey()));
    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(intent);
    }
  }

  @OnClick(R.id.btnFavourite) public void onFavouriteClick() {
    if (!btnFavourite.isSelected()) {
      addFavourite();
    } else {
      deleteFavourite();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
  }

  @Override public Loader onCreateLoader(int id, Bundle args) {
    return new AsyncTaskLoader<Cursor>(this) {
      Cursor movieData = null;

      @Override protected void onStartLoading() {
        if (movieData != null) {
          deliverResult(movieData);
        } else {
          forceLoad();
        }
      }

      @Override public Cursor loadInBackground() {
        try {
          return getContentResolver().query(MovieContract.CONTENT_URI, null, null, null, null);
        } catch (Exception e) {
          Log.e("Error", "Failed to asynchoronusly load data");
          return null;
        }
      }

      public void deliverResult(Cursor data) {
        movieData = data;
        super.deliverResult(data);
      }
    };
  }

  @Override public void onLoadFinished(Loader loader, Object data) {

  }

  @Override public void onLoaderReset(Loader loader) {

  }
}
