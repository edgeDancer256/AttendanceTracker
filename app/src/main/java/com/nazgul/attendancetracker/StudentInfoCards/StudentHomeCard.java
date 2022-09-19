package com.nazgul.attendancetracker.StudentInfoCards;

/**
 * Created by Sharat on 19-09-2022, Sep, 2022.
 * Time : 13:30
 * Project : AttendanceTracker
 */
public class StudentHomeCard {
    private String info;
    private String course;
    private String semester;
    private String course_id;

    public StudentHomeCard(String info, String course, String semester, String course_id) {
        this.info = info;
        this.course = course;
        this.semester = semester;
        this.course_id = course_id;
    }

    public String getInfo() {
        return info;
    }

    public String getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourse_id() {
        return course_id;
    }
}
