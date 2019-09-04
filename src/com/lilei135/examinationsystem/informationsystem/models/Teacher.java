package com.lilei135.examinationsystem.informationsystem.models;

/** @author wangsiqian */
public class Teacher {
    private int teacherId;
    private String teacherName;
    private String teacherLevel;

    public Teacher(int teacherId) {
        this.teacherId = teacherId;
    }

    public Teacher(int teacherId, String teacherName, String teacherLevel) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherLevel = teacherLevel;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherLevel() {
        return teacherLevel;
    }

    public void setTeacherLevel(String teacherLevel) {
        this.teacherLevel = teacherLevel;
    }
}
