import java.util.HashMap;
import java.util.LinkedList;

public class Student extends  User {
    private Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother(){
        return mother;
    }
    public Parent getFather(){
        return father;
    }

    public LinkedList<Course> getCourseList (){

        // OBTINEM LISTA DE CURSURI LA CARE ESTE
        // INSCRIS STUDENTUL

        Catalog catalog = Catalog.getCatalog();
        // UNICA INSTANTA A CATALOGULUI

        LinkedList<Course> allCourseList = catalog.getCourseList();
        // LISTA DE CURSURI DIN TOT CATALOGUL

        LinkedList<Course> courseList = new LinkedList<>();

        for(Course course : allCourseList)
            if(course.isStudent(this))
                courseList.add(course);

        return courseList;
    }

    public Group getGroup(Course course){

        // CAUTAM IN GRUPELE CURSULUI
        // STUDENTUL RESPECTIV

        HashMap<String, Group> groups = course.getGroups();

        for(Group group : groups.values()) {
            if(group.isStudent(this))
                return group;
        }
        return  null;
    }
    public Assistant getAssistant(Course course){

        // CAUTAM ASISTENTUL STUDENTULUI
        // DE LA CURSUL RESPECTIV

        Group group = getGroup(course);
        return group.getAssistant();
    }

    public Assistant getAssistant(String courseName){

        Course course = Catalog.getCatalog().getCourse(courseName);
        return  getAssistant(course);
    }

}
