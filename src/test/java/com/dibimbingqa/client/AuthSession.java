package com.dibimbingqa.client;

public class AuthSession {
    private static String sessionCookie;

    public static void setSessionCookie(String sid) {
        sessionCookie = sid;
    }

    public static String getSessionCookie() {
        return sessionCookie;
    }
}
