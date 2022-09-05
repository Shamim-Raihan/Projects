package com.rahat.pharmsafebd.services.model;

public class Order {

    String id;
    String medicineId;
    String uesrId;
    String quantity;
    String location;
    String totalPrice;

    public Order(String id, String medicineId, String uesrId, String quantity, String location, String totalPrice) {
        this.id = id;
        this.medicineId = medicineId;
        this.uesrId = uesrId;
        this.quantity = quantity;
        this.location = location;
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getUesrId() {
        return uesrId;
    }

    public void setUesrId(String uesrId) {
        this.uesrId = uesrId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
