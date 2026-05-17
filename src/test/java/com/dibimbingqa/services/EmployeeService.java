package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.EmployeesVariables;
import com.dibimbingqa.models.responses.EmployeesResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

import java.util.Map;

public class EmployeeService {
    public static ApiResponse<EmployeesResponse> getEmployees(EmployeesVariables variables) {
        String query = TestDataLoader.load("graphql/queries/Employees.graphql");
        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(EmployeesResponse.class)
        );
    }
}
