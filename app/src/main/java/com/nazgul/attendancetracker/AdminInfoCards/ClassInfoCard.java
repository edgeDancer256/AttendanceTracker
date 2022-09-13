package com.nazgul.attendancetracker.AdminInfoCards;

public class ClassInfoCard {
    private int imgRes;
    private String text;
    private String class_id;

    public ClassInfoCard(int in_imgRes, String in_text, String cID) {
        this.imgRes = in_imgRes;
        this.text = in_text;
        this.class_id = cID;
    }

    public int getImgRes() {
        return imgRes;
    }

    public String getText() {
        return text;
    }

    public String getClass_id() {
        return class_id;
    }
}
