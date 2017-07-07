package com.kursigoyang.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.kursigoyang.popularmovie.constant.URLCons;
import com.kursigoyang.popularmovie.model.Movie;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fajar Rianda on 26/06/2017.
 */
public class MovieDetailActivity extends AppCompatActivity {

  @Bind(R.id.txtMovieTitle) TextView txtMovieTitle;
  @Bind(R.id.txtSynopsis) TextView txtSynopsis;
  @Bind(R.id.txtYear) TextView txtYear;
  @Bind(R.id.txtRating) TextView txtRating;
  @Bind(R.id.imgMovie) ImageView imgContent;

  public static void start(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieDetailActivity.class);
    intent.putExtra(Movie.class.getSimpleName(), movie);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    ButterKnife.bind(this);
    init();
  }

  private void init() {
    Movie movie = getIntent().getParcelableExtra(Movie.class.getSimpleName());
    setupContent(movie);
    //loadVideoTrailer(movie.getId());
    //loadReview(movie.getId());
  }

  private void setupContent(Movie movie) {
    txtMovieTitle.setText(movie.getTitle());
    txtSynopsis.setText(movie.getOverview());
    txtRating.setText(String.valueOf(movie.getVoteAverage()).concat("/10"));
    txtYear.setText(movie.getReleaseDate().substring(0,4));
    Glide.with(this).load(URLCons.URL_BASE_GET_IMAGE.concat(movie.getPoster())).into(imgContent);
  }

  private void loadVideoTrailer(int movieId) {
    AppClient.createService(ApiService.class)
        .getVideos(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();
  }

  private void loadReview(int movieId) {
    AppClient.createService(ApiService.class)
        .getReviews(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();
  }

  @Override protected void onStart() {
    super.onStart();
    getSupportActionBar().setTitle("Pop Movie");
  }
}
