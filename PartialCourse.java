import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class PartialCourse extends Course{
    public PartialCourse(CourseBuilder builder) {
        super(builder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> studentArrayList = new ArrayList<>();
        for(Grade grade : getAllStudentGrades().values()){
            if(grade.getTotal() >= 5)
                studentArrayList.add(grade.getStudent());
        }
        return studentArrayList;
    }

    static class PartialCourseBuilder extends CourseBuilder{

        @Override
        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public  CourseBuilder titular(Teacher titular) {
            this.titular = titular;
            return this;
        }

        @Override
        public CourseBuilder assistants(TreeSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        @Override
        public CourseBuilder grades(TreeSet<Grade> grades) {
            this.grades = grades;
            return this;
        }

        @Override
        public CourseBuilder groups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        @Override
        public CourseBuilder credits(int credits) {
            this.credits = credits;
            return this;
        }

        @Override
        public CourseBuilder strategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }


        @Override
       public Course build() {
            return new PartialCourse(this);
        }
    }

}
