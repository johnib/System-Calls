/**
 * Created by Jonathan Yaniv on 3/4/15.
 * Student ID: 304726896
 */


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimeTest {

    private static final String ERR_USAGE = "Usage: java TimeTest [/force] source_file target_file buffer_size\n";
    private static final String ERR_OPEN  = "Error opening reader/writer.\n";
    private static final String ERR_CLOSE = "Error closing reader/writer.\n";
    private static final String ERR_COPY  = "File %s could not be copied to file %s\n";
    private static final String DID_COPY  = "File %s was copied to %s\nTotal time: %dms\n";

    // I assume that as long as #args is 3 or 4, the args given are valid.
    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4) {
            System.out.println(ERR_USAGE);
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
            System.out.printf(DID_COPY, src, dst, (end - start));
        else
            System.out.printf(ERR_COPY, src, dst);
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
            // check for file existence
            if (!bOverwrite)
                if ((new File(toFileName)).exists())
                    return false;

            // initialize
            src = new FileReader(srcFileName);
            dst = new FileWriter(toFileName);
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
