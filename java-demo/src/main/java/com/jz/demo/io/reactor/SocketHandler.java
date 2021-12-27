package com.jz.demo.io.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface SocketHandler {

    void read(SelectionKey selectionKey) throws IOException;

    void write(SelectionKey selectionKey) throws IOException;

}
