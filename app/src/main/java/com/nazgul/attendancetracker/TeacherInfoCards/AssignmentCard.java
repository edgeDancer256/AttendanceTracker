package com.nazgul.attendancetracker.TeacherInfoCards;

/**
 * Created by Sharat on 13-09-2022, Sep, 2022.
 * Time : 17:12
 * Project : AttendanceTracker
 */
public class AssignmentCard {
    private String file_name;
    private String id;
    private String token;
    private int imgRes;

    public AssignmentCard(int img, String fileName,  String token, String id) {
        this.imgRes = img;
        this.file_name = fileName;
        this.token = token;
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public int getImg() {
        return imgRes;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }
}
