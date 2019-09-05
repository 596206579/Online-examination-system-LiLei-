package com.lilei135.examinationsystem.informationsystem.models;

/** @author wangsiqian */
public class Course {
    private int courseId;
    private int teacherId;
    private int classId;
    private String courseName;
    private String courseStatus;

    public Course(int courseId) {
        this.courseId = courseId;
    }

    public Course(int courseId, int teacherId, int classId, String courseName, String courseStatus) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.courseName = courseName;
        this.courseStatus = courseStatus;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
