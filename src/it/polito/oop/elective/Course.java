package it.polito.oop.elective;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Course {

    private String name;
    private Integer positions;
    private Integer[] preferences = {0, 0, 0};
    private TreeMap<String, Student> students = new TreeMap<>();


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


    public TreeMap<String, Student> getStudents() {
        return students;
    }

    public void addStudent(Student s){
        students.put(s.getId(), s);
    }

    //id ordinati per media decrescente
    public List<String> getListStudent(){
        return students.values().stream().sorted(Comparator.comparing(Student::getAverage).reversed())
        .map(Student::getId).collect(Collectors.toList());
    }

}
