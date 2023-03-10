import java.util.*;

//**
// STRATEGY IL PUN IN COURSEBUILDER?
// *//

public abstract class Course{

    private String name;
    private Teacher titular;
    private TreeSet<Assistant> assistants;
    private TreeSet<Grade> grades;
    private HashMap<String, Group> groups; // toate grupele
    private int credits;

    private Strategy strategy;

    private Snapshot snapshot;

    public Course (CourseBuilder builder){
        this.name = builder.name;
        this.titular = builder.titular;
        this.assistants = builder.assistants;
        this.groups = builder.groups;
        this.grades = builder.grades;
        this.credits = builder.credits;
        this.strategy = builder.strategy;

    }
    public String toString(){
        String aux = "";
        aux = aux.concat("NUMELE CURSULUI: " + name + "\n");
        aux = aux.concat("TITULAR CURS: " + titular + "\n");
        aux = aux.concat("LISTA ASISTENTILOR: " + assistants + "\n");
        aux = aux.concat("LISTA GRUPELOR:" + groups + "\n");
        aux = aux.concat("LISTA NOTELOR:" + grades + "\n");
        aux = aux.concat("CREDITE: " + credits + "\n");

        return aux;
    }

    public Student getBestStudent(){

        return strategy.getBestStudent(grades);

    }


    // SETTER

    public void setName(String name){
        this.name = name;
    }
    public void setTitular(Teacher titular){
        this.titular = titular;
    }

    public void setAssistants (TreeSet<Assistant> assistants){
        this.assistants = assistants;
    }

    public void setGrades(TreeSet<Grade> grades){
        this.grades = grades;
    }

    public void setGroups(HashMap<String, Group> groups){
        this.groups = groups;
    }
    public void setCredits(int credits){
        this.credits = credits;
    }
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }


    // GETTER

    public String getName(){
        return name;
    }

    public Teacher getTitular(){
        return titular;
    }

    public TreeSet<Assistant> getAssistants(){
        return assistants;
    }

    public TreeSet<Grade> getGrades(){
        return grades;
    }

    public HashMap<String, Group> getGroups(){
        return groups;
    }

    public int getCredits(){
        return credits;
    }

    public Strategy getStrategy(){
        return strategy;
    }

    // Seteaza asistentul în grupa cu ID-ul indicat
    // Daca nu exista deja, adauga asistentul si în multimea asistentilor
    public void addAssistant(String ID, Assistant assistant){

        Group group = groups.get(ID);
        group.setAssistant(assistant);
        assistants.add(assistant);

    }

    public void addStudent(String ID, Student student){
    // Adauga studentul în grupa cu ID-ul indicat
        Group group = groups.get(ID);
        group.add(student);

    }


    // Adauga grupa
    public void addGroup(Group group) {

        groups.put(group.getID(), group);

    }

    public void addGroup(String ID, Assistant assistant){

        Group group = new Group(ID, assistant);
        groups.put(ID,group);

    }

    public void addGroup(String ID, Assistant assist, Comparator<Student> comp){

        Group group = new Group(ID,assist,comp);
        groups.put(ID,group);

    }

    public Grade getGrade(Student student){

        for (Grade grade : grades) {
            if (grade.getStudent() == student)
                return grade;
        }
        return null;
    }

    public void addGrade(Grade grade){

        grades.add(grade);

        Catalog catalog = Catalog.getCatalog();
        catalog.notifyObservers(grade);

    }

    public ArrayList<Student> getAllStudents(){

        ArrayList<Student> studentArrayList = new ArrayList<>();
        for (Group group : groups.values()) {
            for(Student student : group)
                studentArrayList.add(student);
        }
        return studentArrayList;
    }

    public HashMap<Student, Grade> getAllStudentGrades(){
        HashMap<Student,Grade> studentGradeHashMap = new HashMap<>();
        for(Grade grade : grades){
            studentGradeHashMap.put(grade.getStudent(), grade);
        }
        return studentGradeHashMap;
    }

    public boolean isStudent(Student student){
        // FUNCTIE CE STABILESTE DACA UN ELEV STUDIAZA
        // UN ANUMIT CURS

        ArrayList<Student> studentArrayList = getAllStudents();

        return (studentArrayList.contains(student));

    }


    public abstract ArrayList<Student> getGraduatedStudents();
    abstract static class CourseBuilder{

        String name;
        Teacher titular;
        TreeSet<Assistant> assistants = new TreeSet<>(new Comparator<>() {
            @Override
            public int compare(Assistant o1, Assistant o2) {
                if (o1.getFirstName().compareTo(o2.getFirstName()) != 0)
                    return o1.getFirstName().compareTo(o2.getFirstName());
                else return o1.getLastName().compareTo(o2.getLastName());
            }
        });

        TreeSet<Grade> grades = new TreeSet<>();
        HashMap<String, Group> groups = new HashMap<>(); // toate grupele
        int credits;
        Strategy strategy;
        public  abstract CourseBuilder name(String name);
        public abstract CourseBuilder titular(Teacher titular);
        public  abstract CourseBuilder assistants(TreeSet<Assistant> assistants);
        public abstract CourseBuilder grades(TreeSet<Grade> grades);
        public abstract CourseBuilder groups(HashMap<String, Group> groups);

        public abstract CourseBuilder credits(int credits);
        public abstract CourseBuilder strategy(Strategy strategy);

        public abstract Course build();

    }

    public void makeBackup() {

        snapshot = new Snapshot((TreeSet<Grade>) grades.clone());


    }

    public void undo() { // TODO2
        if(snapshot == null)
            System.out.println("NU EXISTA BACK-UP ANTERIOR");
        else {
            grades.clear();
            grades = snapshot.grades;
        }
    }

    private class Snapshot{

        private TreeSet<Grade> grades;

        public Snapshot(TreeSet<Grade> grades){
            this.grades = grades;
        }

        public TreeSet<Grade> getGrades(){
            return grades;
        }

        public void setGrades(TreeSet<Grade> grades){
            this.grades = grades;
        }

    }

}