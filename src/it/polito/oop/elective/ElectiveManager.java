package it.polito.oop.elective;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

    TreeMap<String, Course> courses = new TreeMap<>();
    TreeMap<String, Student> students = new TreeMap<>();
    LinkedList<Student> notAssignedList = new LinkedList<>();

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        courses.put(name, new Course(name, availablePositions));
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        return courses.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, double gradeAverage){
        students.put(id, new Student(id, gradeAverage));
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return students.keySet().stream().collect(Collectors.toList());
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return students.values().stream().filter(s->s.getAverage()>= inf && s.getAverage()<=sup)
        .map(Student::getId).collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
        if(courses.size()<1 || courses.size()>3 ) throw new ElectiveException();
        if(!students.containsKey(id)) throw new ElectiveException();
        for(String c : courses) if(!this.courses.containsKey(c)) throw new ElectiveException();

        int i=0;
        for(String c : courses){
            students.get(id).addFreeCourses(c);
            this.courses.get(c).addPreference(i);
            i++;
        }
        
        return students.get(id).getFreeCourses().size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
        return courses.values().stream().collect(Collectors.toMap(Course::getName, Course::getListPreferences));
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
        //studenti in ordine di media decrescente
        LinkedList<Student> s =students.values().stream().sorted(Comparator.comparing(Student::getAverage).reversed()).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Student> notAssigned = new LinkedList<>();
        Student st;
        while(!s.isEmpty()){
            st = s.getFirst();
            Boolean assigned = false;
            for(String c : st.getFreeCourses()){
                if(courses.get(c).getStudents().size()<courses.get(c).getPositions()){
                    courses.get(c).addStudent(st);
                    st.setCourseAssigned(courses.get(c));
                    assigned = true;
                }
            }
            if(!assigned){
                notAssigned.add(st);
                st.setCourseAssigned(null);
            }
        }
        this.notAssignedList=notAssigned;
        return notAssigned.size();
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        return courses.values().stream().collect(Collectors.toMap(Course::getName, Course::getListStudent));
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        double satisfied=0.0;

        for(Student s : students.values()){
            if(s.isSatisfied(choice)) satisfied++;
        }

        return satisfied/students.size();
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return notAssignedList.stream().map(Student::getId).collect(Collectors.toList());
    }

}
