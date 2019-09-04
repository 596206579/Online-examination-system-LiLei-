package com.lilei135.examinationsystem.informationsystem.models;

/** @author wangsiqian */
public class Profession {
    private int professionId;
    private int departmentId;
    private String professionName;

    public Profession(int professionId, int departmentId, String professionName) {
        this.professionId = professionId;
        this.departmentId = departmentId;
        this.professionName = professionName;
    }

    public Profession(int professionId) {
        this.professionId = professionId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }
}
