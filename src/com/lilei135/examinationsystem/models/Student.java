package com.lilei135.examinationsystem.models;

/** @author wangsiqian */
public class Student {
    private String studentId;
    private int classId;
    private String studentName;
    private String studentGender;
    private String studentPassword;

    public Student(String studentId) {
        this.studentId = studentId;
    }

    public Student(String studentId, int classId, String studentName, String studentGender, String studentPassword) {
        this.studentId = studentId;
        this.classId = classId;
        this.studentName = studentName;
        this.studentGender = studentGender;
        this.studentPassword = studentPassword;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }
}
