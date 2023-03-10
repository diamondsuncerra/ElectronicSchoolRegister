import java.util.LinkedList;

public class Assistant extends User implements Element{
    public Assistant(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    // METODA CE OBTINE LISTA CARE CONTINE
    // CURSURILE LA CARE PREDA ASISTENTUL

    public LinkedList<Course> getCourses(){

        LinkedList<Course> courses = new LinkedList<>();

        Catalog catalog = Catalog.getCatalog();

        for(Course course : catalog.getCourseList())
            if(course.getAssistants().contains(this))
                courses.add(course);

        return courses;
    }

}
