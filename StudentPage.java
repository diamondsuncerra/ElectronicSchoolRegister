
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;

public class StudentPage extends JFrame implements ListSelectionListener {

    private JPanel panel, panel1, panel2;
    private JList<String> list;
    private JTextArea textArea1, textArea2, textArea3, textArea4;
    private JTextArea textArea5, textArea6, textArea7, textArea8;

    private JLabel label1, label2, label3;
    private JLabel label4, label5, label6;
    private JLabel label7, label8;
    private Student student;

    public Vector<String> getCourseNameList(LinkedList <Course>courseList){

        // DIN LISTA CURSURILOR STUDENTILOR
        // SE OBTINE LISTA NUMELOR CURSURILOR

        Vector<String> courseNameList = new Vector<>();
        for(Course course : courseList)
            courseNameList.add(course.getName());

        return courseNameList;

    }


    public StudentPage(String str, Student student){


        super(str);
        this.student = student;

        // Aflam lista cursurilor la care este inscris
        // Si numele acestora pentru a crea JList

        LinkedList<Course> courseList = student.getCourseList();

        Vector<String> courseNameList = getCourseNameList(courseList);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setMinimumSize(new Dimension(400,400));

        label1 = new JLabel("LISTA CURSURI");
        label2 = new JLabel("DETALII CURS");
        label3 = new JLabel("NUME CURS:");
        label4 = new JLabel("TITULAR CURS:");
        label5 = new JLabel("LISTA ASISTENTI:");
        label6 = new JLabel("ASISTENT STUDENT:");
        label7 = new JLabel("NOTA EXAMEN:");
        label8 = new JLabel("NOTA PARCURS:");

        panel = new JPanel(new GridLayout(2,1));
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());

        panel.add(panel1);
        panel.add(panel2);

        add(panel);

        panel1.add(label1);


        list = new JList<>(courseNameList);
        list.addListSelectionListener(this);

        textArea1 = new JTextArea();
        textArea2 = new JTextArea();
        textArea3 = new JTextArea();
        textArea4 = new JTextArea();
        textArea5 = new JTextArea();
        textArea6 = new JTextArea();
        textArea7 = new JTextArea();
        textArea8 = new JTextArea();

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setSize(new Dimension(800,800));
        listScroller.setVisible(true);

        listScroller.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);

        panel1.add(listScroller);

        panel1.add(textArea1);

        panel2.add(label2);
        panel2.add(textArea2);

        panel2.add(label3);
        panel2.add(textArea3);

        panel2.add(label4);
        panel2.add(textArea4);

        panel2.add(label5);
        panel2.add(textArea5);

        panel2.add(label6);
        panel2.add(textArea6);

        panel2.add(label7);
        panel2.add(textArea7);

        panel2.add(label8);
        panel2.add(textArea8);

        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
        label8.setVisible(false);

        textArea1.setVisible(false);
        textArea2.setVisible(false);
        textArea3.setVisible(false);
        textArea4.setVisible(false);
        textArea5.setVisible(false);
        textArea6.setVisible(false);
        textArea7.setVisible(false);
        textArea8.setVisible(false);

        setVisible(true);


    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

        JList source = (JList) e.getSource();

        if(source.isSelectionEmpty())
            return;
        else{
            String courseName = (String) source.getSelectedValue();

            Course course = Catalog.getCatalog().getCourse(courseName);

            Grade grade = course.getGrade(student);

            label2.setVisible(true);
            label3.setVisible(true);
            label4.setVisible(true);
            label5.setVisible(true);
            label6.setVisible(true);
            label7.setVisible(true);
            label8.setVisible(true);

            textArea1.setVisible(true);
            textArea2.setVisible(true);
            textArea3.setVisible(true);
            textArea4.setVisible(true);
            textArea5.setVisible(true);
            textArea6.setVisible(true);
            textArea7.setVisible(true);
            textArea8.setVisible(true);

            textArea3.setText(courseName);
            textArea4.setText(course.getTitular().toString());

            TreeSet<Assistant> assistants = course.getAssistants();
            String assistantsString = "";

            for(Assistant assistant : assistants)
                assistantsString = assistantsString.concat(assistant.toString() + " ");

            textArea5.setText(assistantsString);
            textArea6.setText(student.getAssistant(course).toString());
            if(grade!= null){

                if(grade.getExamScore() != -1)
                    textArea7.setText(String.valueOf(grade.getExamScore()));
                else textArea7.setText("");

                if(grade.getPartialScore() != -1)
                    textArea8.setText(String.valueOf(grade.getPartialScore()));
                else textArea8.setText("");
            }
           else {
                textArea7.setText("");
                textArea8.setText("");
            }
        }
    }

}

