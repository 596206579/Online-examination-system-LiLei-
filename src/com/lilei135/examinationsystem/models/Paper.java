package com.lilei135.examinationsystem.models;

/** @author wangsiqian */
public class Paper {
    private int paperId;
    private int studentId;
    private int paperGrade;
    private String paperSubject;

    public Paper(int paperId, int studentId, int paperGrade, String paperSubject) {
        this.paperId = paperId;
        this.studentId = studentId;
        this.paperGrade = paperGrade;
        this.paperSubject = paperSubject;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getPaperGrade() {
        return paperGrade;
    }

    public void setPaperGrade(int paperGrade) {
        this.paperGrade = paperGrade;
    }

    public String getPaperSubject() {
        return paperSubject;
    }

    public void setPaperSubject(String paperSubject) {
        this.paperSubject = paperSubject;
    }
}
