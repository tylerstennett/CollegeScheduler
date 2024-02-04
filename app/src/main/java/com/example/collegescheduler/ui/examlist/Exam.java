package com.example.collegescheduler.ui.examlist;

public class Exam {
    String name;
    String date;
    String time;
    String location;
    String course;

    Exam(String name, String date, String time, String location, String course) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.course = course;
    }
}
