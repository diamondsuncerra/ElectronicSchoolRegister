import java.util.*;


public class ScoreVisitor implements  Visitor{

    private HashMap<Teacher, LinkedList<Tuple<Student,String,Double>>> examScores;
    private HashMap<Assistant,LinkedList<Tuple<Student,String,Double>>> partialScores;

    public ScoreVisitor(){
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }

    // FUNCTIE CARE FACE VECTORUL DE TUPLE SUB FORMA DE STRING

    public Vector<String> tupleListToVector(LinkedList<Tuple<Student,String,Double>> tuples, String course){

        Vector<String> stringVector = new Vector<>();

        for(Tuple<Student, String, Double> tuple : tuples){
            if(tuple.getCourse().equals(course)){
                String string = tuple.toString();
                stringVector.add(string);
            }
        }

        return stringVector;
    }

    // FUNCTIE CARE RETURNEAZA LISTA DE GRADE
    // DE LA PARTIAL SAU EXAMEN
    // PENTRU USURINTA IN INTERFATA GRAFICA

    public Vector<String> getGradeVector(User user, String course){

        if(user instanceof  Teacher)
        {
                LinkedList<Tuple<Student,String,Double>> tuples = examScores.get(user);
                return tupleListToVector(tuples, course);

        }
        else{
            LinkedList<Tuple<Student,String,Double>> tuples = partialScores.get(user);
            return tupleListToVector(tuples, course);
        }
    }

    // GETTER PENTRU A ACCESA DICTIONARELE
    public HashMap<Teacher, LinkedList<Tuple<Student,String,Double>>> getExamScores(){
        return examScores;
    }
    public HashMap<Assistant, LinkedList<Tuple<Student,String,Double>>> getPartialScores(){
        return partialScores;
    }


    public void addGrade(Double score, User user, Student student, String course){

        Tuple<Student, String, Double> tuple = new Tuple<>(student,course,score);
        if(user instanceof  Teacher){
            LinkedList<Tuple<Student,String,Double>> list = examScores.get(user);
            LinkedList<Tuple<Student,String,Double>> aux;

            if(list == null) {
                aux = new LinkedList<>();
                aux.add(tuple);
                examScores.put((Teacher) user,aux);
            }
            else examScores.get(user).add(tuple);

        }

        else {
            LinkedList <Tuple<Student,String,Double>>list = partialScores.get(user);
            LinkedList<Tuple<Student,String,Double>> aux;

            if(list == null) {
                aux = new LinkedList<>();
                aux.add(tuple);
                partialScores.put((Assistant) user,aux);
            }
            else partialScores.get(user).add(tuple);

        }
    }


    @Override
    public void visit(Assistant assistant) {

        Catalog catalog = Catalog.getCatalog();
        LinkedList<Tuple<Student,String,Double>> list = partialScores.get(assistant);
        // LISTA CU NOTELE ASISTENTULUI

        for(Tuple<Student,String,Double> tuple : list){

            String courseName = tuple.getCourse();
            Course course = catalog.getCourse(courseName);

            Grade grade = course.getGrade(tuple.getStudent());

            int aux = 0;

            if(grade == null) {
                grade = new Grade(tuple.getGrade(), (double) -1, tuple.getStudent(), courseName);
                aux = 1;
            }
            else grade.setPartialScore(tuple.getGrade());

            if(aux == 1)
                course.addGrade(grade);
            else catalog.notifyObservers(grade);
    }
}
    @Override
    public void visit(Teacher teacher) {

        Catalog catalog = Catalog.getCatalog();
        LinkedList<Tuple<Student,String,Double>> list = examScores.get(teacher);
        // LISTA CU NOTELE PROFESORULUI

        for(Tuple<Student,String,Double> tuple : list){

            String courseName = tuple.getCourse();
            Course course = catalog.getCourse(courseName);

            Grade grade = course.getGrade(tuple.getStudent());

            // VARIABILA CE TINE CONT DACA NOTA TREBUIE ADAUGATA SAU DOAR NOTIFICATA

            int aux = 0;

            if(grade == null) {
                grade = new Grade((double) -1, tuple.getGrade(), tuple.getStudent(), courseName);
                aux = 1;
            }
            else grade.setExamScore(tuple.getGrade());

            if(aux == 1)
                course.addGrade(grade);

            else catalog.notifyObservers(grade);

        }
    }
    private class Tuple<K,V,T> {
        private K student;
        private V course;
        private T grade;

        private Tuple(K student, V course, T grade){
            this.student = student;
            this.course = course;
            this.grade = grade;
        }

        public K getStudent() {
            return student;
        }

        public void setStudent(K student) {
            this.student = student;
        }

        public V getCourse() {
            return course;
        }

        public void setCourse(V course) {
            this.course = course;
        }

        public T getGrade() {
            return grade;
        }

        public void setGrade(T grade) {
            this.grade = grade;
        }

        public String toString(){
            return student + " " + grade;
        }

    }

}
