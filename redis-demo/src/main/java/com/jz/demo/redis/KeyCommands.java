package com.jz.demo.redis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.Value;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import static io.lettuce.core.dynamic.annotation.CommandNaming.Strategy.METHOD_NAME;

/**
 * 需要继承自 {@link Commands}
 * 文档地址：https://lettuce.io/core/release/reference/#redis-command-interfaces
 */
public interface KeyCommands extends Commands {

    String set(String key, String value);

    @Command("SET ?1 ?0")
    String set1(String value, String key);

    @Command("SET :key :value")
    String set2(@Param("key") String key, @Param("value") String value);

    @CommandNaming(strategy = METHOD_NAME)
    String mSet(String key1, String value1, String key2, String value2);

    @CommandNaming(strategy = METHOD_NAME)
    RedisFuture<String> get(String key);

    List<String> mget(String... keys);

    @Command("MGET")
    List<Value<String>> mget1(String... keys);

}
