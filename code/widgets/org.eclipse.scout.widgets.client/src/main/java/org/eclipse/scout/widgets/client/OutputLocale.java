package org.eclipse.scout.widgets.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * <h3>{@link OutputLocale}</h3>
 *
 * @author awe
 */
public class OutputLocale {

  /**
   * @param args
   */
  public static void main(String[] args) {
    List<Locale> list = new ArrayList<>(Arrays.asList(Locale.getAvailableLocales()));
    list.sort((o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName()));

    for (Locale l : list) {
      String s = "['" + l.getDisplayName() + "', '" + l.toString() + "'],";
      System.out.println(s);
    }
  }

}
