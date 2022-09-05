package com.rahat.pharmsafebd.services.model;

public class ClientRegisterModelClass {

    private String name;
    private String gender;
    private String phone;
    private String email;
    private String password;
    private String uid;
    private String status;

    public ClientRegisterModelClass(String name, String gender, String phone, String email, String password, String uid, String status) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.status = status;
    }

    public ClientRegisterModelClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
