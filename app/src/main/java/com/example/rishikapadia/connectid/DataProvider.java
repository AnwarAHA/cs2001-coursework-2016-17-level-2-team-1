package com.example.rishikapadia.connectid;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class DataProvider {

    private String Name;
    private String Age;
    private String Course;
    private String Societies;
    private String Interests;

    public DataProvider() {

    }

    public DataProvider(String name, String age, String course, String societies, String interests) {
        this.Name = name;
        this.Age = age;
        this.Course = course;
        this.Societies = societies;
        this.Interests = interests;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getSocieties() {
        return Societies;
    }

    public void setSocieties(String societies) {
        Societies = societies;
    }

    public String getInterests() {
        return Interests;
    }

    public void setInterests(String interests) {
        Interests = interests;
    }
}

