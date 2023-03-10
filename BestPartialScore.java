import java.util.TreeSet;

public class BestPartialScore implements Strategy {


    @Override
    public Student getBestStudent(TreeSet<Grade> grades) {
        Student bestStudent = null;
        Double score = (double) 0;

        for(Grade grade : grades){

            if(grade.getPartialScore() > score){
                score = grade.getPartialScore();
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }
}
