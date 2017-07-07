package com.kursigoyang.popularmovie;

import com.kursigoyang.popularmovie.constant.URLCons;
import com.kursigoyang.popularmovie.model.Movie;
import com.kursigoyang.popularmovie.model.Responses;
import com.kursigoyang.popularmovie.model.Review;
import com.kursigoyang.popularmovie.model.Video;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Fajar Rianda on 24/06/2017.
 */
public interface ApiService {

  @GET(URLCons.URL_GET_POPULAR_MOVIES)
  Observable<Responses<List<Movie>>> getPopularMovies();

  @GET(URLCons.URL_GET_TOP_RATED_MOVIES)
  Observable<Responses<List<Movie>>> getTopRatedMovies();

  @GET(URLCons.URL_GET_TRAILERS)
  Observable<Responses<List<Video>>> getVideos(@Path("id") int movieId);

  @GET(URLCons.URL_GET_REVIEWS)
  Observable<Responses<List<Review>>> getReviews(@Path("id") int movieId);




}
