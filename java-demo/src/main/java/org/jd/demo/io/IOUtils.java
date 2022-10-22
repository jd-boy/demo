package org.jd.demo.io;

public class IOUtils {

    public static String buildResp() {
        return buildResp("{'a':123}");
    }

    public static String buildResp(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK").append('\n');
        stringBuilder.append("connection: Close").append('\n');
        stringBuilder.append("content-type: application/json").append('\n');
        stringBuilder.append("content-length: ").append(str.getBytes().length).append('\n');
        stringBuilder.append("\n").append(str);
        return stringBuilder.toString();
    }

    public static void doSomething() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
