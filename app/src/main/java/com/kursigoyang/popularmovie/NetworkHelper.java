package com.kursigoyang.popularmovie;

import java.net.UnknownHostException;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Fajar Rianda on 24/06/2017.
 */
public class NetworkHelper {

  // We had non-200 http error
  public static boolean httpException(Throwable throwable) {
    return throwable instanceof HttpException;
  }

  public static boolean unknownHostException(Throwable throwable) {
    return throwable instanceof UnknownHostException;
  }
}
