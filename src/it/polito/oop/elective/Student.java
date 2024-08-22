package it.polito.oop.elective;

import java.util.LinkedList;

public class Student {

    private String id;
    private double average;
    private LinkedList<String> freeCourses = new LinkedList<>(); 
    private Course courseAssigned;


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

    public Course getCourseAssigned() {
        return courseAssigned;
    }

    public void setCourseAssigned(Course courseAssigned) {
        this.courseAssigned = courseAssigned;
    }

    public Boolean isSatisfied(int i){
        int el = 1;
        String course=null;
        for(String c : freeCourses){
            course = c;
            if(el==i) break;
            else el++;
        }
        if(course.equals(courseAssigned.getName())) return true;
        return false;
    }

}
