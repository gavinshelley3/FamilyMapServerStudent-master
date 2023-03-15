package Handler;

import java.io.*;
/**
 * StringHandler class
 */
public class StringHandler {
    /**
     * StringHandler constructor
     */
    public StringHandler() {

    }

    /**
     * Reads a string from an InputStream
     * @param is
     * @return String read from InputStream
     * @throws IOException
     */
    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * Writes a string to an OutputStream
     * @param str
     * @param os
     * @throws IOException
     */
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}