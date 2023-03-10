import java.util.Comparator;
import java.util.TreeSet;

public class Group extends TreeSet<Student> {
    private  String ID;
    private Assistant assistant;

    public Assistant getAssistant(){
        return assistant;
    }
    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void setAssistant(Assistant assistant){
        this.assistant = assistant;
}

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.ID = ID;
        this.assistant = assistant;
    }
    public Group(String ID, Assistant assistant) {

        super(new Comparator<>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.getFirstName().compareTo(o2.getFirstName()) != 0)
                    return o1.getFirstName().compareTo(o2.getFirstName());
                else return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        this.ID = ID;
        this.assistant = assistant;


    }

    public boolean isStudent(Student student){

        // Verifica daca un student face
        // parte din grupa pentru
        // interfata grafica

        for(Student student1 : this)
            if(student1.equals(student))
                return true;

        return false;

    }

}

