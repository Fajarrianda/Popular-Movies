package com.kursigoyang.popularmovie;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.kursigoyang.popularmovie.model.Movie;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

  @Bind(R.id.rvContent) RecyclerView rvContent;
  MovieAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    init();
    loadDataPopular();
  }

  private void init() {
    rvContent.setHasFixedSize(true);
    rvContent.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

    adapter = new MovieAdapter(this);
    rvContent.setAdapter(adapter);
    adapter.setOnListItemClickListener(this);
  }

  private void loadDataPopular() {
    AppClient.createService(ApiService.class)
        .getPopularMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapter.pushData(object.getResult());
        }, error -> {
          ErrorHelper.thrown(this, error, true);
        });
  }

  private void loadDataTopRated() {
    AppClient.createService(ApiService.class)
        .getTopRatedMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object -> {
          adapter.pushData(object.getResult());
        }, error -> {
          ErrorHelper.thrown(this, error, true);
        });
  }

  private void loadDataFavourite() {
    adapter.clearListData();
    Cursor cursor =
        getContentResolver().query(MovieContract.CONTENT_URI, MovieContract.movieProjection, null,
            null, null);
    List<Movie> movieList = new ArrayList<>();
    if (cursor != null) {
      cursor.moveToFirst();
      if (cursor.getCount() > 0) {
        do {

          Movie movie = new Movie();
          movie.setId(cursor.getInt(cursor.getColumnIndex(MovieContract.COLUMN_NAME_ID)));
          movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.COLUMN_NAME_TITLE)));
          movie.setVoteCount(
              cursor.getString(cursor.getColumnIndex(MovieContract.COLUMN_VOTE_COUNT)));
          movie.setVoteAverage(
              cursor.getFloat(cursor.getColumnIndex(MovieContract.COLUMN_VOTE_AVERAGE)));
          movie.setPoster(
              cursor.getString(cursor.getColumnIndex(MovieContract.COLUMN_POSTER_PATH)));
          movie.setBackdrop(
              cursor.getString(cursor.getColumnIndex(MovieContract.COLUMN_BACKDROP_PATH)));
          movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.COLUMN_OVERVIEW)));
          movie.setReleaseDate(
              cursor.getString(cursor.getColumnIndex(MovieContract.COLUMN_RELEASE_DATE)));

          movieList.add(movie);
        } while (cursor.moveToNext());

        adapter.pushData(movieList);
      }
    }
    cursor.close();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.sortPopular:
        loadDataPopular();
        break;
      case R.id.sortTopRated:
        loadDataTopRated();
        break;
      case R.id.sortFavourited:
        loadDataFavourite();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onListItemClick(int clickedItemIndex, Movie movie) {
    MovieDetailActivity.start(this, movie);
  }
}
