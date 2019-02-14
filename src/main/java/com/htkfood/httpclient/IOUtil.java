package com.htkfood.httpclient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.channels.Selector;

/**
 * Created by hongshuiqiao on 2017/12/20.
 */
public class IOUtil {
    private static final int BUFFER_LENGTH=1024;

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while (true){
            byte[] buffer = new byte[BUFFER_LENGTH];
            int read = input.read(buffer);
            if(read<0)
                break;
            output.write(buffer,0,read);
        }
        output.flush();
        return output.toByteArray();
    }

    public static void close(final URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    public static void closeQuietly(final Reader input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(final Writer output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(final InputStream input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(final OutputStream output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    public static void closeQuietly(final Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    public static void closeQuietly(final Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    public static void closeQuietly(final ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }
}
