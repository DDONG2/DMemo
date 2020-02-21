package com.example.dmemo.dateDTO;

import java.util.ArrayList;

public class memoListDTO {

    private String subjectText;
    private String dateText;
    private String imagePath;


    public memoListDTO(String subjecttext, String datetext, String imagepath) {
        super();
        this.subjectText = subjecttext;
        this.dateText = datetext;
        this.imagePath = imagepath;

    }


    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
