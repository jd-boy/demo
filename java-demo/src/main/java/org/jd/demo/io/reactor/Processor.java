package org.jd.demo.io.reactor;

import java.util.function.Supplier;

public abstract class Processor implements Supplier<byte[]> {

    protected final byte[] data;

    public Processor(byte[] data) {
        this.data = data;
    }

    @Override
    public final byte[] get() {
        return process();
    }

    public abstract byte[] process();

}
