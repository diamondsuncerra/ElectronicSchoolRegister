import java.util.TreeSet;

public class BestExamScore implements Strategy{

    @Override
    public Student getBestStudent(TreeSet<Grade> grades) {

        Student bestStudent = null;
        Double score = (double) 0;

        for(Grade grade : grades){

            if(grade.getExamScore() > score){
                score = grade.getExamScore();
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;

    }
}
