package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.CreateEmployeeVariables;
import com.dibimbingqa.models.responses.CreateEmployeeResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class CreateEmployeeService {
    public static ApiResponse<CreateEmployeeResponse> createEmployee(
            String name, String employeeId, String email, String phoneNumber,
            String divisionId, String employeeRole, int angkatanId,
            String gender, String dateOfBirth, String address, String nik, String npwp) {

        String query = TestDataLoader.load("graphql/mutations/CreateEmployee.graphql");
        CreateEmployeeVariables variables = new CreateEmployeeVariables(
                name, employeeId, email, phoneNumber, divisionId, employeeRole,
                angkatanId, gender, dateOfBirth, address, nik, npwp
        );

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(CreateEmployeeResponse.class)
        );
    }
}
