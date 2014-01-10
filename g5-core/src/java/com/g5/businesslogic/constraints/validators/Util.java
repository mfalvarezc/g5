package com.g5.businesslogic.constraints.validators;

import java.util.regex.Pattern;

public final class Util {

    private static final Pattern pattern = Pattern.compile("[\\w\\+/=]+");

    public static boolean isBase64(String value) {
        return pattern.matcher(value).matches();
    }

}
