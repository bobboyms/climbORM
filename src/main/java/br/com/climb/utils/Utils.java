package br.com.climb.utils;

import static java.util.Objects.nonNull;

public class Utils {

    private Utils() {};

    public static boolean isStringNullOrEmpty(String value) {

        if (nonNull(value) && value.trim().length() > 0) {
            return false;
        }

        return true;

    }
}
