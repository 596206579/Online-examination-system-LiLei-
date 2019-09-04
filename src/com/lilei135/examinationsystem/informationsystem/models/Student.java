package com.lilei135.examinationsystem.informationsystem.models;

/** @author wangsiqian */
public class Student {
    private String studentId;
    private int classId;
    private String studentName;
    private String studentGender;

    public Student(String studentId) {
        this.studentId = studentId;
    }

    public Student(String studentId, int classId, String studentName, String studentGender) {
        this.studentId = studentId;
        this.classId = classId;
        this.studentName = studentName;
        this.studentGender = studentGender;
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
}
