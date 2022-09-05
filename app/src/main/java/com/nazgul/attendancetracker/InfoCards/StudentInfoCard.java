package com.nazgul.attendancetracker.InfoCards;

/**
 * Created by Sharat on 27-08-2022, Aug, 2022.
 * Time : 11:40
 * Project : AttendanceTracker
 */
public class StudentInfoCard {

    private String student_info;
    private String student_id;
    private String std_semester;
    private String std_course;
    private int imgRes;

    public StudentInfoCard(int imgRes, String student_info, String student_id, String std_course, String std_semester) {
        this.imgRes = imgRes;
        this.student_info = student_info;
        this.student_id = student_id;
        this.std_course = std_course;
        this.std_semester = std_semester;
    }

    public String getStudent_info() {
        return student_info;
    }

    public String getStudent_id() {
        return student_id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public String getStd_semester() {
        return std_semester;
    }

    public String getStd_course() {
        return std_course;
    }
}
