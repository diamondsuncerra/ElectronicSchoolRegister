import java.util.TreeSet;

public class BestTotalScore implements Strategy {

    @Override
    public Student getBestStudent(TreeSet<Grade> grades) {
        Student bestStudent = null;
        Double score = (double) 0;

        for(Grade grade : grades){

            if(grade.getTotal() > score){
                score = grade.getTotal();
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }
}
