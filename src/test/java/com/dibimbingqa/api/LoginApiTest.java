package com.dibimbingqa.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dibimbingqa.utils.ExcelUtils;
import io.qameta.allure.*;

import static io.restassured.RestAssured.given;

public class LoginApiTest {

    @Test
    public void testLogin() {
        String baseUrl = "https://lmsb2b.do.dibimbing.id/graphql";

        String body = "{ \"query\": \"mutation login($usernameOrEmail:String!,$password:String!,$companyId:String!){ " +
                "login(usernameOrEmail:$usernameOrEmail,password:$password,companyId:$companyId){ " +
                "user { username id role __typename } " +
                "errors { field message __typename } __typename }}\", " +
                "\"operationName\": \"login\", " +
                "\"variables\": { \"usernameOrEmail\": \"arwendymelyn@dibimbing.id\", " +
                "\"password\": \"s2et9bh6l\", " +
                "\"companyId\": \"811637b1-9989-4d45-a9f5-220c5f2354f7\" } }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(baseUrl);

        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Cookies: " + response.getCookies());

        // Ambil cookie sid_b2b dari response header
        String sessionToken = response.getCookie("sid_b2b");

        // Kalau ternyata token ada di JSON, bisa ambil begini:
        if (sessionToken == null) {
            sessionToken = response.jsonPath().getString("data.login.user.id");
        }

        System.out.println("Session Token: " + sessionToken);

        ExcelUtils.writeCookie("ApiCookie", sessionToken);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Login gagal: " + response.jsonPath().getString("errors[0].message"));
        }


        Assert.assertNotNull(sessionToken, "Login gagal, token/cookie tidak ditemukan!");
    }

}
