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
  static int nbLigne = 1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String line[];
    do {
      line = Utils.getNextLine(str);
      super.write(nbLigne + " " + line[0], off, len);
    }
    while(line[1] != null);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String line[];
    do {
      line = Utils.getNextLine(new String(cbuf));
      super.write(nbLigne + " " + line[0], off, len);
    }
    while(line[1] != null);
  }

  @Override
  public void write(int c) throws IOException {
    switch (c) {
      case '\n':
      case '\r':
        super.write(c + nbLigne + " ");
        break;
      default:
        super.write(c);
        break;

    }
}



}
