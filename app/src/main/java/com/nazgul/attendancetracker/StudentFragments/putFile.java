package com.nazgul.attendancetracker.StudentFragments;

public class putFile {

    public String name;
    public String url;

    public putFile() {
    }

    public putFile(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
