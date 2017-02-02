package com.example.rishikapadia.connectid;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class DataProvider {

    private String name;
    private String age;
    private String course;
    private String societies;
    private String interests;

    public DataProvider(String name,String age,String course,String societies,String interests){
        this.name = name;
        this.age = age;
        this.course = course;
        this.societies = societies;
        this.interests = interests;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSocieties() {
        return societies;
    }

    public void setSocieties(String societies) {
        this.societies = societies;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
