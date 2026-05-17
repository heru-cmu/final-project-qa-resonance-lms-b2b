package com.dibimbingqa.services;

import com.dibimbingqa.client.AuthSession;
import com.dibimbingqa.client.GraphQlClient;
import com.dibimbingqa.models.requests.LoginVariables;
import com.dibimbingqa.models.responses.LoginResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import io.restassured.response.Response;

public class AuthService {
    public static ApiResponse<LoginResponse> login(String email, String password, String companyId) {
        String query = TestDataLoader.load("graphql/mutations/Login.graphql"); // bikin file login.graphql
        LoginVariables variables = new LoginVariables(email, password, companyId);

        Response response = GraphQlClient.execute(query, variables);

        // simpan cookie sid_b2b
        String sid = response.getCookie("sid_b2b");
        if (sid != null) {
            AuthSession.setSessionCookie(sid);
        }

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(LoginResponse.class)
        );
    }
}
