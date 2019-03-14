package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  static int nbLigne;
  static boolean newLine;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    nbLigne = 1;
    newLine = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str = "";
    if(nbLigne == 1) {
      str += nbLigne + "\t";
      nbLigne++;
    }

    for( int i = off; i < off + len; i++) {
      if( cbuf[i] == '\r') {
        newLine = true;
        str += cbuf[i];
      }
      else if( cbuf[i] == '\n') {
        str += cbuf[i];
        str += nbLigne + "\t";
        nbLigne++;
        newLine = false;
      }
      else{
        if(newLine){
          newLine = false;
          str += nbLigne + "\t" + cbuf[i];
          nbLigne++;
        }
        else{
          str += cbuf[i];
        }
      }

    }

    super.write(str, 0, str.length());
  }

  @Override
  public void write(int c) throws IOException {
    write(new char[]{(char)c}, 0, 1);
}



}
