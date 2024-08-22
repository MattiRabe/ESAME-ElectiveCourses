package it.polito.oop.elective;

public class Student {

    private String id;
    private double average;


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

    

}
