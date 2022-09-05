package com.rahat.pharmsafebd.services.model;

import java.io.Serializable;

public class MedicineModelClass implements Serializable {
    String id;
    String group;
    String medicineName;
    String power;
    String companyName;
    String unitPrice;
    String boxPrice;
    String availability;

    public MedicineModelClass(String id, String group, String medicineName, String power, String companyName, String unitPrice, String boxPrice, String availability) {
        this.id = id;
        this.group = group;
        this.medicineName = medicineName;
        this.power = power;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
        this.boxPrice = boxPrice;
        this.availability = availability;
    }

    public MedicineModelClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(String boxPrice) {
        this.boxPrice = boxPrice;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
