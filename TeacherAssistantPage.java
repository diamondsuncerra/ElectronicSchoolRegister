import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

    // PAGINA TEACHER/ASSISTANT ESTE CREATA INTR-O
    // SINGURA CLASA FACAND DIFERENTIERE
    // PE BAZA ARGUMENTULUI DAT IN CONSTRUCTOR

public class TeacherAssistantPage extends JFrame implements ActionListener,ListSelectionListener {

    private JPanel panel, panel1, panel2,panel3;
    private JList<String> listCourse, listGrades;
    private JButton button;
    private JScrollPane listScroller1, listScroller2;

    private DefaultListModel listModel;


    private ScoreVisitor scoreVisitor;

    private User user;

    // DECIDE DACA ESTE PAGINA UNUI ASISTENT
    // SAU A UNUI PROFESOR

    public TeacherAssistantPage(String string, User user, ScoreVisitor scoreVisitor)  {

        super(string);
        this.user = user;
        this.scoreVisitor = scoreVisitor;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setMinimumSize(new Dimension(400,400));


        panel = new JPanel(new GridLayout(1,3));
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();

        add(panel);

        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);


        // CREAM LISTA DE CURSURI LA CARE PREDA USER-UL

        LinkedList<Course> coursesList;

        if(user instanceof  Teacher)
            coursesList = ((Teacher)user).getCourses();
        else coursesList = ((Assistant)user).getCourses();

        Vector<String> coursesNameVector = new Vector<>();
        for(Course course : coursesList)
            coursesNameVector.add(course.getName());

        listCourse = new JList<>(coursesNameVector);

        panel1.add(listCourse);

        listScroller1 = new JScrollPane(listCourse);
        listScroller1.setSize(new Dimension(800,800));
        listScroller1.setVisible(true);
        listScroller1.setViewportView(listCourse);

        listCourse.setLayoutOrientation(JList.VERTICAL);
        panel1.add(listScroller1);


        listModel = new DefaultListModel<>();
        listModel.addElement("BUNA ZIUA! SELECTATI UN CURS");

        listGrades = new JList<>(listModel);
        listGrades.setSize(new Dimension(800,800));

        panel2.add(listGrades);

        listScroller2 = new JScrollPane(listGrades);
        listScroller2.setViewportView(listGrades);
        listScroller2.setSize(new Dimension(800,800));
        listGrades.setLayoutOrientation(JList.VERTICAL);
        listGrades.setMinimumSize(new Dimension(800,800));
        panel2.add(listScroller2);


        // CREAM BUTONUL DE VALIDARE A NOTELOR

        button = new JButton("VALIDEAZA NOTE");
        button.addActionListener(this);

        listCourse.addListSelectionListener(this);
        listGrades.addListSelectionListener(this);

        panel3.add(button);

        button.setEnabled(false);

        setVisible(true);

    }

    // BUTONUL ARE POSIBILITATEA DE A VALIDA
    // TOATE NOTELE IN ACELASI TIMP

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == button){

            if(user instanceof Teacher)
                ((Teacher)user).accept(scoreVisitor);
            else ((Assistant)user).accept(scoreVisitor);

            button.setEnabled(false);
            button.setText("NOTE VALIDATE");
            listModel.removeAllElements();

        }

    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

        // LISTA CU NOTE APARE CAND SE DA CLICK
        // PE NUMELE CURSULUI

        JList source = (JList) e.getSource();

        button.setEnabled(true);
        button.setText("VALIDEAZA NOTE");

        if(source.equals(listGrades))
            return;

        if(source.isSelectionEmpty())
            return;

        String courseName = (String) source.getSelectedValue();
        Course course = Catalog.getCatalog().getCourse(courseName);

        Vector<String> gradeVector = new Vector<>();
        gradeVector = scoreVisitor.getGradeVector(user, courseName);

        // SE OBTINE VECTORUL CU NOTELE DE VALIDAT
        // DIN DICTIONARELE DIN SCOREVISITOR
        // LA CURSUL RESPECTIV PENTRU PROFESOR/ASISTENT


        listModel.removeAllElements();

        for(String string : gradeVector)
            listModel.addElement(string);

        button.setEnabled(true);

    }
}
