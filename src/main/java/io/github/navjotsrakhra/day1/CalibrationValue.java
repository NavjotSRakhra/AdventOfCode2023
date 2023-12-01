package io.github.navjotsrakhra.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalibrationValue {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long sum = 0;
        int c = 0;
        sum = in.lines().map(CalibrationValue::processString).mapToLong(Integer::longValue).sum();
        System.out.println(sum);
    }

    private static int processString(String str) {
        int n = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                n = Character.getNumericValue(ch);
                break;
            }
        }

        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                n *= 10;
                n += Character.getNumericValue(ch);
                break;
            }
        }
        return n;
    }
}
