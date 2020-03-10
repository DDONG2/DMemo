package com.example.dmemo.dateDTO;

import java.util.ArrayList;

public class memoListDTO {

    private int _id;
    private String title;
    private String content;
    private String date;
    //  private String imagePath;

    //
//    public memoListDTO(String title, String content, String date, String imagepath) {
//        super();
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.imagePath = imagepath;
//    }
    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
}
