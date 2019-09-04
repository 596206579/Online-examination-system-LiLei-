package com.lilei135.examinationsystem.informationsystem.models;

/**
 * @author wangsiqian
 */
public class Class {
    private int classId;
    private int professionId;
    private String className;

    public Class(int classId) {
        this.classId = classId;
    }

    public Class(int classId, int professionId, String className) {
        this.classId = classId;
        this.professionId = professionId;
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
