package nia.chapter13;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/12.
 */
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';

    private final InetSocketAddress source;

    private final String logfile;

    private final String msg;

    private final long received;

    // Constructor for an outgoing message
    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    // Constructor for an incoming message
    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }
}
