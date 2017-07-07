package com.kursigoyang.popularmovie;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Fajar Rianda on 24/06/2017.
 */
public class ErrorHelper {

  public static void thrown(Context context, Throwable throwable, boolean showToast) {
    if (NetworkHelper.unknownHostException(throwable)) {
      if (showToast) {
        Toast.makeText(context, R.string.error_no_connection, Toast.LENGTH_SHORT).show();
      }
    } else if (NetworkHelper.httpException(throwable)) {
      if (showToast) {
        Toast.makeText(context, R.string.error_try_again, Toast.LENGTH_SHORT).show();
      }
    }
  }
}
