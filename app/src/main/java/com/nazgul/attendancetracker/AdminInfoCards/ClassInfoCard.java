package com.nazgul.attendancetracker.AdminInfoCards;

public class ClassInfoCard {
    private int imgRes;
    private String class_id;
    private String course_name;
    private String semester;
    private String subject;
    private String teacher_id;

    public ClassInfoCard(int imgRes, String class_id, String course_name, String semester, String subject, String teacher_id) {
        this.imgRes = imgRes;
        this.class_id = class_id;
        this.course_name = course_name;
        this.semester = semester;
        this.subject = subject;
        this.teacher_id = teacher_id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public String getClass_id() {
        return class_id;
    }
}
