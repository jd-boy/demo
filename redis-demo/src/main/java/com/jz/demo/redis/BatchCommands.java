package com.jz.demo.redis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.batch.BatchExecutor;
import io.lettuce.core.dynamic.batch.BatchSize;
import io.lettuce.core.dynamic.batch.CommandBatching;

@BatchSize(10)
public interface BatchCommands extends Commands, BatchExecutor {

    /**
     * 同步命令无法获取到结果，只能通过 RedisFuture 异步获取
     */
    void set(String key, String value);

    RedisFuture<String> get(String key);

    RedisFuture<String> get(String key, CommandBatching batching);

}
