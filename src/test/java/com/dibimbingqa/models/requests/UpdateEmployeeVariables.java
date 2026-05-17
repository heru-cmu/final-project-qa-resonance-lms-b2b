package com.dibimbingqa.models.requests;

public class UpdateEmployeeVariables {
    public String id;
    public Input input;

    public UpdateEmployeeVariables(String id, String name, String email) {
        this.id = id;
        this.input = new Input(name, email);
    }

    public static class Input {
        public String name;
        public String email;

        public Input(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
