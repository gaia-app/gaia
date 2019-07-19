package io.codeka.gaia.runner;

import com.spotify.docker.client.LogReader;
import com.spotify.docker.client.LogStream;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.io.IdentityInputStream;
import org.apache.http.impl.io.SessionInputBufferImpl;
import org.glassfish.jersey.message.internal.EntityInputStream;
import org.springframework.stereotype.Component;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;

@Component
public class HttpHijackWorkaround {

    OutputStream getOutputStream(final LogStream stream, final String uri) throws IOException {
        // @formatter:off
        final String[] fields =
                new String[] {"reader",
                        "stream",
                        "original",
                        "input",
                        "in",
                        "in",
                        "in",
                        "eofWatcher",
                        "wrappedEntity",
                        "content",
                        "in",
                        "inStream"};

        final String[] containingClasses =
                new String[] {"com.spotify.docker.client.DefaultLogStream",
                        LogReader.class.getName(),
                        "org.glassfish.jersey.message.internal.ReaderInterceptorExecutor$UnCloseableInputStream",
                        EntityInputStream.class.getName(),
                        FilterInputStream.class.getName(),
                        FilterInputStream.class.getName(),
                        FilterInputStream.class.getName(),
                        EofSensorInputStream.class.getName(),
                        HttpEntityWrapper.class.getName(),
                        BasicHttpEntity.class.getName(),
                        IdentityInputStream.class.getName(),
                        SessionInputBufferImpl.class.getName()};
        // @formatter:on

        final List<String[]> fieldClassTuples = new LinkedList<>();
        for (int i = 0; i < fields.length; i++) {
            fieldClassTuples.add(new String[] {containingClasses[i], fields[i]});
        }

        if (uri.startsWith("unix:")) {
            //fieldClassTuples.add(new String[] {"org.apache.http.impl.conn.LoggingInputStream", "in"});
            fieldClassTuples.add(new String[] {"sun.nio.ch.ChannelInputStream", "ch"});
        } else if (uri.startsWith("https:")) {
            final float jvmVersion = Float.parseFloat(System.getProperty("java.specification.version"));
            fieldClassTuples.add(new String[] {"sun.security.ssl.AppInputStream", jvmVersion < 1.9f ? "c" : "socket"});
        } else {
            fieldClassTuples.add(new String[] {"java.net.SocketInputStream", "socket"});
        }

        final Object res = getInternalField(stream, fieldClassTuples);
        if (res instanceof WritableByteChannel) {
            return Channels.newOutputStream((WritableByteChannel) res);
        } else if (res instanceof Socket) {
            return ((Socket) res).getOutputStream();
        } else {
            throw new AssertionError("Expected " + WritableByteChannel.class.getName() + " or " + Socket.class.getName() + " but found: " +
                    res.getClass().getName());
        }
    }

    public static InputStream getInputStream(LogStream stream) {
        final String[] fields = new String[] { "reader", "stream" }; //$NON-NLS-1$ //$NON-NLS-2$
        final String[] declared = new String[] { "com.spotify.docker.client.DefaultLogStream", LogReader.class.getName()};

        List<String[]> list = new LinkedList<>();
        for (int i = 0; i < fields.length; i++) {
            list.add(new String[] { declared[i], fields[i] });
        }
        return (InputStream) getInternalField(stream, list);
    }

    /**
     * Recursively traverse a hierarchy of fields in classes, obtain their value and continue the traversing on the optained object
     *
     * @param fieldContent     current object to operate on
     * @param classFieldTupels the class/field hierarchy
     *
     * @return the content of the leaf in the traversed hierarchy path
     */
    private static Object getInternalField(final Object fieldContent, final List<String[]> classFieldTupels) {
        Object curr = fieldContent;
        for (final String[] classFieldTuple : classFieldTupels) {
            //noinspection ConstantConditions
            final Field field;
            try {
                field = Class.forName(classFieldTuple[0]).getDeclaredField(classFieldTuple[1]);
                field.setAccessible(true);
                curr = field.get(curr);
            } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return curr;
    }
}
