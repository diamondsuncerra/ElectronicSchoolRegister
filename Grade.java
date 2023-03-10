public class Grade implements Cloneable, Comparable{

    private Double partialScore, examScore;
    private Student student;
    private String course; // Numele cursului

    public Grade(Double partialScore, Double examScore, Student student, String course){

        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;

    }

    public String toString(){
        String aux = "NOTA PARTIAL: " + partialScore + " NOTA EXAMEN: " + examScore + " " ;
        aux = aux.concat("STUDENT: " + student + " " + course + "\n" );
        return aux;
    }

    // Setter
    public void setPartialScore(double partialScore){
        this.partialScore = partialScore;
    }
    public void setExamScore(double examScore){
        this.examScore = examScore;
    }
    public void setStudent(Student student){
        this.student = student;
    }

    public void setCourse(String course){
        this.course = course;
    }

    //Getter

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

    @Override
    public int compareTo(Object o) {

        // GRADES SUNT COMPARABILE DUPA
        // NOTA FINALA DAR DACA E ACEEASI
        // NOTA FINALA, PENTRU A NU FII
        // CONSIDERATE EGALE, SE COMPARA
        // NUMELE STUDENTILOR

        Grade grade = (Grade) o;
        Double total = this.getTotal();
        Double otherTotal = grade.getTotal();

        if(total.compareTo(otherTotal) != 0)
            return total.compareTo(otherTotal);
        else return student.compareTo(((Grade) o).getStudent());

    }



    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

