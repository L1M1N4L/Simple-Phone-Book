import java.util.LinkedList;

public class PhoneBook {
    private LinkedList<Contact> contacts;

    public PhoneBook() {
        contacts = new LinkedList<>();
    }

    public void addContact(String name, String phoneNumber, String email) {
        contacts.add(new Contact(name, phoneNumber, email));
    }

    public void removeContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public LinkedList<Contact> getContacts() {
        return contacts;
    }
}
