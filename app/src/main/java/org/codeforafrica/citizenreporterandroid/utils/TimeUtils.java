package org.codeforafrica.citizenreporterandroid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ahereza on 9/13/17.
 */

public class TimeUtils {
  public static String formatDate(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMMM yyyy");
    return dateFormat.format(date);
  }
}
