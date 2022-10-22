package org.jd.demo.io.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface SocketHandler {

    void read() throws IOException;

    void write() throws IOException;

}
