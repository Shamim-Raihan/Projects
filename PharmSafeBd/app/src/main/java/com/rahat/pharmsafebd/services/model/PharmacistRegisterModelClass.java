package com.rahat.pharmsafebd.services.model;

public class PharmacistRegisterModelClass {

    private String pharmacyName;
    private String tradeLicenceImageUrl;
    private String pharmacistLicenceImageUrl;
    private String phone;
    private String email;
    private String password;
    private String uid;
    private String status;


    public PharmacistRegisterModelClass(String pharmacyName, String tradeLicenceImageUrl, String pharmacistLicenceImageUrl, String phone, String email, String password, String uid, String status) {
        this.pharmacyName = pharmacyName;
        this.tradeLicenceImageUrl = tradeLicenceImageUrl;
        this.pharmacistLicenceImageUrl = pharmacistLicenceImageUrl;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.status = status;
    }

    public PharmacistRegisterModelClass() {
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getTradeLicenceImageUrl() {
        return tradeLicenceImageUrl;
    }

    public void setTradeLicenceImageUrl(String tradeLicenceImageUrl) {
        this.tradeLicenceImageUrl = tradeLicenceImageUrl;
    }

    public String getPharmacistLicenceImageUrl() {
        return pharmacistLicenceImageUrl;
    }

    public void setPharmacistLicenceImageUrl(String pharmacistLicenceImageUrl) {
        this.pharmacistLicenceImageUrl = pharmacistLicenceImageUrl;
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
