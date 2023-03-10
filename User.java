public abstract class User  implements Comparable{
    private String firstName, lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String toString() {
        return firstName + " " + lastName;
    }

    // PENTRU COMPARATORUL DIN GROUP
    @Override
    public int compareTo(Object o) {
        String firstName1 = ((User) o).getFirstName();
        String lastName1 = ((User) o).getLastName();

        if(firstName.compareTo(firstName1)!=0)
            return firstName.compareTo(firstName1);
        else return lastName.compareTo(lastName1);
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}