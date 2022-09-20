package com.nazgul.attendancetracker.AdminInfoCards;

/**
 * Created by Sharat on 20-09-2022, Sep, 2022.
 * Time : 13:10
 * Project : AttendanceTracker
 */
public class QuestionPaperCard {
    private String file_name;
    private String token;
    private String class_id;
    private int img;

    public QuestionPaperCard(String file_name, String token, String class_id, int img) {
        this.file_name = file_name;
        this.token = token;
        this.class_id = class_id;
        this.img = img;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getToken() {
        return token;
    }

    public String getClass_id() {
        return class_id;
    }

    public int getImg() {
        return img;
    }
}
