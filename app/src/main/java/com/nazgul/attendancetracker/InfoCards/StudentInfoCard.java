package com.nazgul.attendancetracker.InfoCards;

/**
 * Created by Sharat on 27-08-2022, Aug, 2022.
 * Time : 11:40
 * Project : AttendanceTracker
 */
public class StudentInfoCard {

    private String student_info;
    private String student_id;
    private int imgRes;

    public StudentInfoCard(int imgRes, String student_info, String student_id) {
        this.imgRes = imgRes;
        this.student_info = student_info;
        this.student_id = student_id;
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
}
