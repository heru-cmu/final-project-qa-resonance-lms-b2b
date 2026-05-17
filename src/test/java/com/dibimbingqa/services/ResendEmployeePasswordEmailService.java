package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.ResendEmployeePasswordEmailVariables;
import com.dibimbingqa.models.responses.ResendEmployeePasswordEmailResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class ResendEmployeePasswordEmailService {
    public static ApiResponse<ResendEmployeePasswordEmailResponse> resendEmployeePasswordEmail(String employeeId) {
        String query = TestDataLoader.load("graphql/mutations/ResendEmployeePasswordEmail.graphql");
        ResendEmployeePasswordEmailVariables variables = new ResendEmployeePasswordEmailVariables(employeeId);

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(ResendEmployeePasswordEmailResponse.class)
        );
    }
}
