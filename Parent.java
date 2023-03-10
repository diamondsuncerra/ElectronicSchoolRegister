import java.util.Comparator;
import java.util.TreeSet;

public class Parent extends User implements Observer{
    private TreeSet<Notification> notifications;
    public Parent(String firstName, String lastName) {

        super(firstName, lastName);
        notifications = new TreeSet<>(new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Override
    public void update(Notification notification) {

        notifications.add(notification);

    }
    public TreeSet<Notification> getNotifications(){
        return notifications;
    }
}
