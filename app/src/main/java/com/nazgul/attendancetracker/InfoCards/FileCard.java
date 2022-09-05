package com.nazgul.attendancetracker.InfoCards;

/**
 * Created by Sharat on 05-09-2022, Sep, 2022.
 * Time : 11:36
 * Project : AttendanceTracker
 */
public class FileCard {

    private String file_name;
    private String token;
    private int imgRes;

    public FileCard(int img, String fileName,  String token) {
        this.imgRes = img;
        this.file_name = fileName;
        this.token = token;
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
}
