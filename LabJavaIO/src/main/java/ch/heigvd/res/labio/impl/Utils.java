package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    int index;
    if(lines.contains("\r\n")) {
      index = lines.indexOf("\r\n") + 2;
    }
    else {
      if ( (index = lines.indexOf('\r') + 1) == 0)
       /* if( (index = lines.indexOf('\n') + 1) == 0)
          return new String[]{lines,null};*/
        index = lines.indexOf('\n') + 1;
    }
    return new String[]{lines.substring(0,index),lines.substring(index)};
  }

}
