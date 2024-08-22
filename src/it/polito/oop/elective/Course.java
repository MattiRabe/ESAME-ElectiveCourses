package it.polito.oop.elective;

public class Course {

    private String name;
    private Integer positions;


    public Course(String name, Integer positions) {
        this.name = name;
        this.positions = positions;
    }


    public String getName() {
        return name;
    }
    
    public Integer getPositions() {
        return positions;
    }

    

}
