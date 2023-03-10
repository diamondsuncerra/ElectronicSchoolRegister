import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;


public class ParentPage extends JFrame implements ActionListener, ListSelectionListener {

    private JPanel panel, panel1, panel2;
    private JTextArea textArea;
    private JList<String> courseList;
    private JScrollPane scrollPane;
    private JButton button;
    private DefaultListModel<String> listModel;
    private Parent parent;
    private TreeSet<Notification> notifications;

    // BUTONUL DE REFRESH FUNCTIONEAZA ASTFEL INCAT IN CAZUL IN CARE
    // SE ADAUGA O NOTA IN TIMP CE PARINTELE URMARESTE NOTIFICARILE,
    // SA POATA VEDEA SI NOUA NOTIFICARE DUPA CLICK

    public ParentPage(String string, Parent parent){

        super(string);
        this.parent = parent;
        notifications = parent.getNotifications();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setMinimumSize(new Dimension(400,400));

        panel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();

        panel.add(panel1);
        panel.add(panel2);
        add(panel);

        button = new JButton("REFRESH PAGE");
        button.addActionListener(this);
        panel2.add(button);

        textArea = new JTextArea("AICI VOR APAREA DETALIILE NOTIFICARILOR");
        panel2.add(textArea);


        // VECTOR CU NUMELE CURSURILOR
        listModel = new DefaultListModel();


        for(Notification notification : notifications)
            listModel.addElement(notification.getCourse());

        courseList = new JList<>(listModel);
        courseList.addListSelectionListener(this);
        scrollPane = new JScrollPane(courseList);
        panel1.add(scrollPane);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == button){

            for(Notification notification : parent.getNotifications())
                if(listModel.contains(notification.getCourse()) == false){
                listModel.addElement(notification.getCourse());
            }

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        JList list = (JList) e.getSource();
        String courseName = (String) list.getSelectedValue();

        if(courseName == null)
            return;

        // CAUTAM NOTIFICAREA PE CARE O VOM AFISA
        Notification notification = null;

        for(Notification notification1 : notifications)
            if (notification1.getCourse().equals(courseName))
                notification = notification1;

        textArea.setText("");
        String output = "";

        if(notification == null)
            System.out.println("OFF");

        output = output.concat(notification.getStudent().toString() + " are punctajul la materia " + notification.getCourse());

        if(notification.getPartialScore() != -1)
            output = output.concat("\nPe parcurs: " + notification.getPartialScore().toString());
        if(notification.getExamScore() != -1)
            output = output.concat("\nIn examen: " + notification.getExamScore().toString());

        if(notification.getExamScore() != -1 &&notification.getPartialScore()!= -1)
            output = output.concat("\nPentru un total de: " + notification.getTotal().toString() + "\n");
        else output = output.concat("\nNota finala nu este inca afisata.\n");


        textArea.setText(output);
        list.clearSelection();


    }
}
