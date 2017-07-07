package com.kursigoyang.popularmovie.constant;

/**
 * Created by Fajar Rianda on 24/06/2017.
 */
public class URLCons {

  public static final String URL_BASE = "http://api.themoviedb.org/3/";
  public static final String URL_GET_POPULAR_MOVIES = URL_BASE + "movie/popular";
  public static final String URL_GET_TOP_RATED_MOVIES = URL_BASE + "movie/top_rated";
  public static final String URL_GET_TRAILERS = URL_BASE + "movie/{id}/videos";
  public static final String URL_GET_REVIEWS = URL_BASE + "movie/{id}/reviews";

  public static final String URL_BASE_GET_IMAGE = "https://image.tmdb.org/t/p/w500";
}
