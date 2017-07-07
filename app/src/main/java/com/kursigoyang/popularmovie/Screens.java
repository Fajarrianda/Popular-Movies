package com.kursigoyang.popularmovie;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Fajar Rianda on 30/06/2017.
 */
public class Screens {
  public static Point getDisplaySize(Context context) {
    Point point = new Point();
    WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    if(Build.VERSION.SDK_INT >= 13) {
      windowManager.getDefaultDisplay().getSize(point);
    } else {
      Display display = windowManager.getDefaultDisplay();
      point.set(display.getWidth(), display.getHeight());
    }

    return point;
  }
}
