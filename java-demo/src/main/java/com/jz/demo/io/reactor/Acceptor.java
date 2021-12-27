package com.jz.demo.io.reactor;

import java.nio.channels.SelectionKey;

/**
 * 连接器
 */
public interface Acceptor {

    /**
     * 接收一个连接事件的 {@link SelectionKey}
     * @param selectionKey
     * @throws Exception
     */
    void accept(SelectionKey selectionKey) throws Exception;

}
