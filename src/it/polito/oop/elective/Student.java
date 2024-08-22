package it.polito.oop.elective;

import java.util.LinkedList;

public class Student {

    private String id;
    private double average;
    private LinkedList<String> freeCourses = new LinkedList<>(); 


    public Student(String id, double average) {
        this.id = id;
        this.average = average;
    }


    public String getId() {
        return id;
    }


    public double getAverage() {
        return average;
    }


    public LinkedList<String> getFreeCourses() {
        return freeCourses;
    }


    public void addFreeCourses(String s) {
        this.freeCourses.addLast(s);
    }

}
