package com.jz.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import lombok.SneakyThrows;

public class Main {

    private static ExecutorService executorService = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));

    public static class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
    @SneakyThrows
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(9);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        l2.next.next.next = new ListNode(9);
        int result = toNum(l1, 1) + toNum(l2, 1);
        toListNodes(result);
    }

    public static ListNode toListNodes(int num) {
        int n = 0;
        ListNode root = new ListNode();
        ListNode node = root;
        do {
            n = num % 10;
            node.val = n;
            num /= 10;
            if (num == 0) {
                node.next = null;
            } else {
                node.next = new ListNode();
                node = node.next;
            }
        } while (num > 0);
        return root;
    }

    public static int toNum(ListNode root, int n) {
        if (root.next == null) {
            return root.val * n;
        }
        return toNum(root.next, n * 10);
    }
}
