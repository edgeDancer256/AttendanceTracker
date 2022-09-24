package com.nazgul.attendancetracker.TeacherInfoCards;

/**
 * Created by Sharat on 23-09-2022, Sep, 2022.
 * Time : 19:27
 * Project : AttendanceTracker
 */
public class StudentAttInfo {
    private String std_name;
    private String std_status;
    private String student_id;

    public StudentAttInfo(String std_name, String std_status, String student_id) {
        this.std_name = std_name;
        this.std_status = std_status;
        this.student_id = student_id;
    }

    public String getStd_name() {
        return std_name;
    }

    public String getStd_status() {
        return std_status;
    }

    public void setStd_status(String std_status) {
        this.std_status = std_status;
    }

    public String getStudent_id() {
        return student_id;
    }
}
