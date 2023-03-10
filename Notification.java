// NOTIFICATION ESTE COMPARABILA DEOARECE
// FOLOSIM TREESET PENTRU A EVITA DUBLURI
// DOUA NOTIFICARI SUNT EGALE DACA AU SI NOTELE
// SI STUNDETII EGALI
public class Notification implements Comparable {
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Notification(Grade grade){
        this.course = grade.getCourse();
        this.partialScore = grade.getPartialScore();
        this.examScore = grade.getExamScore();
        this.student = grade.getStudent();
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public Student getStudent() {
        return student;
    }

    public String getCourse() {
        return course;
    }
    public Double getTotal() {
        return examScore + partialScore;
    }

    public String toString(){
        return "STUDENTUL: " + student + " a obtinut notele: " + partialScore + " " + examScore + "la disciplina "
                + course;
    }

    @Override
    public int compareTo(Object o) {

        if(partialScore.compareTo( ( (Notification)o).getPartialScore() ) != 0 )
            return partialScore.compareTo( ( (Notification)o).getPartialScore() );
        else if( examScore.compareTo( ( (Notification)o).getExamScore())!= 0 )
            return examScore.compareTo( ( (Notification)o).getExamScore());
        else return  student.compareTo(((Notification)o).getStudent());

    }
}
