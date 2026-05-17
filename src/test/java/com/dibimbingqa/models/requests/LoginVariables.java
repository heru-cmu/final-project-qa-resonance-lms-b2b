package com.dibimbingqa.models.requests;

public class LoginVariables {
    public Input input;

    public LoginVariables(String email, String password, String companyId) {
        this.input = new Input(email, password, companyId);
    }

    public static class Input {
        public String email;
        public String password;
        public String companyId;

        public Input(String email, String password, String companyId) {
            this.email = email;
            this.password = password;
            this.companyId = companyId;
        }
    }
}
