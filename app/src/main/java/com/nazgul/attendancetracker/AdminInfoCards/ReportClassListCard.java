package com.nazgul.attendancetracker.AdminInfoCards;

/**
 * Created by Sharat on 29-09-2022, Sep, 2022.
 * Time : 16:01
 * Project : AttendanceTracker
 */
public class ReportClassListCard {
    private String subject;
    private String semester;
    private String class_id;
    private String course_name;
    private String tch_id;

    public ReportClassListCard(String subject, String semester, String class_id, String course_name, String tch_id) {
        this.subject = subject;
        this.semester = semester;
        this.class_id = class_id;
        this.course_name = course_name;
        this.tch_id = tch_id;
    }

    public String getSubject() {
        return subject;
    }

    public String getSemester() {
        return semester;
    }

    public String getClass_id() {
        return class_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getTch_id() {
        return tch_id;
    }
}
