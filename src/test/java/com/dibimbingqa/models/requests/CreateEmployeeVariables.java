package com.dibimbingqa.models.requests;

public class CreateEmployeeVariables {
    public Input input;

    public CreateEmployeeVariables(String name, String employeeId, String email,
                                   String phoneNumber, String divisionId, String employeeRole,
                                   int angkatanId, String gender, String dateOfBirth,
                                   String address, String nik, String npwp) {
        this.input = new Input(name, employeeId, email, phoneNumber, divisionId,
                employeeRole, angkatanId, gender, dateOfBirth, address, nik, npwp);
    }

    public static class Input {
        public String name;
        public String employeeId;
        public String email;
        public String phoneNumber;
        public String divisionId;
        public String employeeRole;
        public int angkatanId;
        public String gender;
        public String dateOfBirth;
        public String address;
        public String nik;
        public String npwp;

        public Input(String name, String employeeId, String email, String phoneNumber,
                     String divisionId, String employeeRole, int angkatanId,
                     String gender, String dateOfBirth, String address, String nik, String npwp) {
            this.name = name;
            this.employeeId = employeeId;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.divisionId = divisionId;
            this.employeeRole = employeeRole;
            this.angkatanId = angkatanId;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
            this.address = address;
            this.nik = nik;
            this.npwp = npwp;
        }
    }
}
