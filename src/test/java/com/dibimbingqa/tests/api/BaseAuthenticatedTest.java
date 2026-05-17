package com.dibimbingqa.tests.api;

import com.dibimbingqa.services.AuthService;
import com.dibimbingqa.models.responses.LoginResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.Utils;
import org.testng.annotations.BeforeSuite;

public class BaseAuthenticatedTest {

    @BeforeSuite
    public void doLogin() {
        ApiResponse<LoginResponse> response = AuthService.login(
                "arwendymelyn@dibimbing.id",
                "s2et9bh6l",
                "811637b1-9989-4d45-a9f5-220c5f2354f7"
        );
        Utils.logResponse(response); // biar kelihatan hasil login
    }
}
