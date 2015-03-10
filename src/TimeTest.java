/**
 * Created by Jonathan Yaniv on 3/4/15.
 * Student ID: 304726896
 */


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimeTest {

    private static final String USAGE = "Usage: java TimeTest [/force] source_file target_file buffer_size\n";
    private static final String ERR_OPEN = "Error opening reader/writer.\n";
    private static final String ERR_CLOSE = "Error closing reader/writer.\n";

    // I assume that as long as #args is 3 or 4, the args given are valid.
    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4) {
            System.out.println(USAGE);
            System.exit(1);
        }

        // parse arguments
        final boolean overwrite = (args.length == 4);
        final String src = args[args.length - 3];
        final String dst = args[args.length - 2];
        final int buf = Integer.parseInt(args[args.length - 1]);

        // copy file
        final long start = System.currentTimeMillis();
        boolean wasWritten = copyFile(src, dst, buf, overwrite);
        final long end = System.currentTimeMillis();

        // output results
        if (wasWritten)
            System.out.println("File " + src + " was copied to " + dst + "\nTotal time: " + (end - start) + "ms");
        else
            System.out.println("File " + src + " could not be copied to " + dst);
    }

    /**
     * Copies a file to a specific path, using the specified buffer size.
     *
     * @param srcFileName File to copy
     * @param toFileName Destination file name
     * @param bufferSize Buffer size in bytes
     * @param bOverwrite If file already exists, overwrite it
     * @return true when copy succeeds, false otherwise
     */
    public static boolean copyFile(String srcFileName, String toFileName, int bufferSize, boolean bOverwrite) {
        FileReader src = null;
        FileWriter dst = null;
        try {
            // initialize
            src = new FileReader(srcFileName);
            dst = new FileWriter(toFileName, !bOverwrite);
            char[] buffer = new char[bufferSize];

            // read and write
            int size;
            while ((size = src.read(buffer, 0, bufferSize)) != -1)
                dst.write(buffer, 0, size);

            return true;

        } catch (IOException e) {
            System.err.println(ERR_OPEN);

        } finally {
            try {
                if (src != null) src.close();
                if (dst != null) dst.close();
            } catch (IOException e) {
                System.err.println(ERR_CLOSE);
            }
        }

        return false;
    }
}
