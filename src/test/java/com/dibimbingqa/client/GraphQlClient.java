package com.dibimbingqa.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import com.dibimbingqa.utils.Utils;

public class GraphQlClient {
    public static Response execute(String query, Object variables) {
        String baseUrl = Utils.getProperty("BASE_URL"); // ambil dari data.properties

        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .cookie("sid_b2b", AuthSession.getSessionCookie())
                .body(new GraphQlRequest(query, variables))
                .post("")
                .then()
                .extract()
                .response();
    }

    // inner class untuk format body GraphQL
    public static class GraphQlRequest {
        public String query;
        public Object variables;

        public GraphQlRequest(String query, Object variables) {
            this.query = query;
            this.variables = variables;
        }
    }
}
