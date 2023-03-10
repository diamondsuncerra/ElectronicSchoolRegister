import java.util.LinkedList;

public class Teacher extends User implements  Element{
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    // METODA CE OBTINE LISTA CARE CONTINE
    // CURSURILE LA CARE PREDA PROFESORUL
    // PENTRU INTERFATA GRAFICA
    public LinkedList<Course> getCourses(){

        LinkedList<Course> courses = new LinkedList<>();

        Catalog catalog = Catalog.getCatalog();

        for(Course course : catalog.getCourseList())
            if(course.getTitular().equals(this))
                courses.add(course);

        return courses;
    }
}
