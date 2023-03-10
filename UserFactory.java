public class UserFactory {
    public static User getUser(String option, String firstName, String lastName){
        if(option == null)
            return null;

        return switch (option) {
            case "Student" -> new Student(firstName, lastName);
            case "Parent" -> new Parent(firstName, lastName);
            case "Assistant" -> new Assistant(firstName, lastName);
            case "Teacher" -> new Teacher(firstName, lastName);
            default -> null;
        };
    }
}
