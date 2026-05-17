package com.dibimbingqa.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    public Data data;
    public List<GraphQLError> errors;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        public Login login;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Login {
        public User user;
        public List<ErrorDetail> errors; // kalau ada error di level login
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        public String id;
        public String username;
        public String role;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorDetail {
        public String field;
        public String message;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphQLError {
        public String message;
        public List<Location> locations;
        public Extensions extensions;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        public int line;
        public int column;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Extensions {
        public String code;
        public ExceptionDetail exception;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExceptionDetail {
        public List<String> stacktrace;
    }
}
