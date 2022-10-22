package org.jd.demo;

import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        System.out.println(list);
    }

    private static void f(List<Integer> list) {
        list = new ArrayList<>();
        list.add(9);
    }
}
