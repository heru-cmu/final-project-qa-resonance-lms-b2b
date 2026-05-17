package com.dibimbingqa.utils;

import io.restassured.http.Headers;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiResponse<T> {
    private int statusCode;
    private Headers headers;
    private T responseBody;

    public ApiResponse(int statusCode, Headers headers, T responseBody) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    public int getStatusCode() { return statusCode; }
    public Headers getHeaders() { return headers; }
    public T getResponseBody() { return responseBody; }

    // 🔹 Tambahan: print response body sebagai JSON string
    public String toJsonString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
        } catch (Exception e) {
            return responseBody.toString();
        }
    }
}
