package com.rahat.pharmsafebd.services.model;

public class AmbulanceModelClass {

    String id;
    String hospitalName;
    String hotline;

    public AmbulanceModelClass(String id, String hospitalName, String hotline) {
        this.id = id;
        this.hospitalName = hospitalName;
        this.hotline = hotline;
    }

    public AmbulanceModelClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }
}
