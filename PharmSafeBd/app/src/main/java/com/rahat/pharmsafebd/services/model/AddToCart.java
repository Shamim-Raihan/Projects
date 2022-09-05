package com.rahat.pharmsafebd.services.model;

public class AddToCart {
    String id;
    String userId;
    String medicineId;
    String status;
    String quantity;
    String price;

    public AddToCart(String id, String userId, String medicineId, String status, String quantity, String price) {
        this.id = id;
        this.userId = userId;
        this.medicineId = medicineId;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
    }

    public AddToCart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
