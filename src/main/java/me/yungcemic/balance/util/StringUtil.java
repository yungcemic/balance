package me.yungcemic.balance.util;

public final class StringUtil {

    private StringUtil() {}

    public static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}