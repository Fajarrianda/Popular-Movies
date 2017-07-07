package com.kursigoyang.popularmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.kursigoyang.popularmovie.model.Movie;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

  public static final String POPULAR = "popularity.desc";
  public static final String TOP_RATED = "vote_average.desc";

  @Bind(R.id.rvContent) RecyclerView rvContent;
  MovieAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @Override protected void onStart() {
    super.onStart();
    getSupportActionBar().setTitle("Pop Movie");
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
          Log.d("Error", error.getMessage());
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
          Log.d("Error", error.getMessage());
        });
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
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onListItemClick(int clickedItemIndex, Movie movie) {
    MovieDetailActivity.start(this,movie);
  }
}
