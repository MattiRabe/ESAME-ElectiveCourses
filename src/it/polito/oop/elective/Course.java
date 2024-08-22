package it.polito.oop.elective;

import java.util.LinkedList;

public class Course {

    private String name;
    private Integer positions;
    private Integer[] preferences = {0, 0, 0};


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

    public void addPreference(int i){
        preferences[i]++;
    }

    public LinkedList<Long> getListPreferences() {
        LinkedList<Long> l = new LinkedList<>();
        for(int i : preferences) l.addLast((long)i);
        return l;
    }

    


}
