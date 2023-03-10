import java.util.TreeSet;

public interface Strategy {

    public Student getBestStudent(TreeSet<Grade> grades);
}
