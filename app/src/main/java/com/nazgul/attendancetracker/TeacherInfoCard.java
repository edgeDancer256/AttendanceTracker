package com.nazgul.attendancetracker;

/**
 * Created by Sharat on 18-08-2022, Aug, 2022.
 * Time : 13:31
 * Project : AttendanceTracker
 */
public class TeacherInfoCard {

    private int imgRes;
    private String teacher_info;
    private String teacher_id;

    public TeacherInfoCard(int imgRes, String teacher_info, String teacher_id) {
        this.imgRes = imgRes;
        this.teacher_info = teacher_info;
        this.teacher_id = teacher_id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public String getTeacher_info() {
        return teacher_info;
    }

    public String getTeacher_id() {
        return teacher_id;
    }
}
