import java.util.LinkedList;


public class Catalog implements Subject {
    private LinkedList<Course> courseList;
    private static Catalog catalog = null;

    private LinkedList<Observer> observers;

    private Catalog() {

        courseList = new LinkedList<>();
        observers = new LinkedList<>();
    }

    public String toString() {

        String aux = "";
        for(Course course : courseList)
            aux = aux.concat(course.toString() + "\n");
        return aux;
    }

    public static Catalog getCatalog(){
        if(catalog == null)
            catalog = new Catalog();
        return catalog;
    }
    public LinkedList<Course> getCourseList(){
        return courseList;
    }

    // FUNCTIE CE INTOARCE INSTANTA CURSULUI PE BAZA NUMELUI
    public Course getCourse(String courseName){
        for(Course course : courseList)
            if(course.getName().equals(courseName))
                return course;
        return null;
    }
    public void addCourse(Course course) {
        courseList.add(course);
    }

    public void removeCourse(Course course) {
        courseList.remove(course);
    }

    @Override
    public void addObserver(Observer observer) {

        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

        observers.remove(observer);

    }

    @Override
    public void notifyObservers(Grade grade) {
        for(Observer observer : observers)
            if(grade.getStudent().getFather() == observer || grade.getStudent().getMother() == observer)
                observer.update(new Notification(grade));
    }

    // FUNCTII CE CAUTA UN STUDENT PE BAZA NUMELUI
    // SI/SAU A CURSULUI LA CARE ESTE INSCRIS
    public Student findStudent(String firstName, String lastName, Course course){

        for(Group group: course.getGroups().values())
            for(Student student1 : group)
                if(student1.getFirstName().equals(firstName) &&
                        student1.getLastName().equals(lastName))
                    return student1;

        return null;

    }
    public Student findStudent(String firstName, String lastName, String courseName){

        Course course = catalog.getCourse(courseName);

        for(Group group: course.getGroups().values())
            for(Student student1 : group)
                if(student1.getFirstName().equals(firstName) &&
                        student1.getLastName().equals(lastName))
                    return student1;

        return null;

    }

    public Student findStudent(String firstName, String lastName){
        for(Course course : catalog.getCourseList())
            for(Group group: course.getGroups().values())
                for(Student student1 : group)
                    if(student1.getFirstName().equals(firstName) && student1.getLastName().equals(lastName))
                        return student1;

        return null;

    }
    // FUNCTIE CARE GASESTE PROFESORUL PE BAZA NUMELUI SI A CURSULUI LA CARE PREDA
    public Teacher findTeacher(String courseName){

        Course course = catalog.getCourse(courseName);
        return course.getTitular();

    }

    // FUNCTIA CARE GASESTE ASISTENTUL PE BAZA NUMELUI SI A CURSULUI LA CARE PREADA
    public Assistant findAssistant(String firstName, String lastName, String courseName){
        for(Course course : courseList)
            if(course.getName().equals(courseName))
                for(Assistant assistant : (course.getAssistants()))
                    if(assistant.getLastName().equals(lastName) &&assistant.getFirstName().equals(firstName))
                        return assistant;

        return null;
    }
}

