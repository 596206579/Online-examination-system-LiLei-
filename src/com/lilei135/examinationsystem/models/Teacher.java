package com.lilei135.examinationsystem.models;

/** @author wangsiqian */
public class Teacher {
    private String teacherId;
    private String teacherName;
    private String teacherPassword;
    private String teacherLevel;

    public Teacher(String teacherId) {
        this.teacherId = teacherId;
    }

    public Teacher(String teacherId, String teacherName, String teacherPassword, String teacherLevel) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPassword = teacherPassword;
        this.teacherLevel = teacherLevel;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
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

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
}
