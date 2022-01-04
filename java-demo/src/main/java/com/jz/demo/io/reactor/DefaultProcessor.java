package com.jz.demo.io.reactor;

import com.jz.demo.io.IOUtils;

import java.nio.charset.StandardCharsets;

public class DefaultProcessor extends Processor {

    public DefaultProcessor(byte[] data) {
        super(data);
    }

    @Override
    public byte[] process() {
        System.out.println("接收到：" + new String(data));
        IOUtils.doSomething();
        return IOUtils.buildResp("{'b': '响应：HelloWord'}").getBytes(StandardCharsets.UTF_8);
    }

}
