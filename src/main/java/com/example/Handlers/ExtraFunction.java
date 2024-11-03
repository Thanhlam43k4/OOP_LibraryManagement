package com.example.Handlers;

public class ExtraFunction {

    public static String extractISBN(String fullISBN) {
        int lastDashIndex = fullISBN.lastIndexOf('-');
        if (lastDashIndex != -1) {
            return fullISBN.substring(0, lastDashIndex);
        }
        return fullISBN;
    }
}
