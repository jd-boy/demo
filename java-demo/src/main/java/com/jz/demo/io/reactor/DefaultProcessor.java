package com.jz.demo.io.reactor;

import com.jz.demo.io.IOUtils;

import java.nio.charset.StandardCharsets;

public class DefaultProcessor extends Processor {

    public DefaultProcessor(byte[] data) {
        super(data);
    }

    @Override
    public byte[] process() {
        IOUtils.doSomething();
        byte[] result = IOUtils.buildResp("{'b': '响应：HelloWord'}").getBytes(StandardCharsets.UTF_8);
        return result;
    }

}
