package org.jd.demo;

import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
    }

    private static boolean search(int[][] matrix, int i, int j, int target, int maxColumn, int maxRow, boolean[][] flag) {
        System.out.printf("%d, %d\n", i, j);
        int current = matrix[i][j];
        flag[i][j] = true;
        if (current == target) {
            return true;
        }

        int nexti = i, nextj = j;
        if (current < target) {
            if (i + 1 <= maxRow) {
                nexti++;
            }
            if (j + 1 <= maxColumn) {
                nextj++;
            }

            if (i == nexti && j == nextj) {
                return false;
            }

            int down = matrix[nexti][j];
            int right = matrix[i][nextj];

            if (down == target || right == target) {
                return true;
            }

            if (i == nexti) {
                down = 999999999;
            }
            if (j == nextj) {
                right = 999999999;
            }

            if (down < right) {
                return search(matrix, nexti, j, target, maxColumn, maxRow, flag);
            } else {
                return search(matrix, i, nextj, target, maxColumn, maxRow, flag);
            }
        } else {
            for (int ii = i; ii < i; ii++) {
                if (matrix[ii][j] == target) {
                    return true;
                }
            }
            for (int jj = 0; jj < j; jj++) {
                if (matrix[i][jj] == target) {
                    return true;
                }
            }
            return false;
        }
    }
}
