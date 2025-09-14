import java.io.*;
import java.util.*;

class Contact implements Serializable {
    String name;
    String phone;
    String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Phone: " + phone + " | Email: " + email;
    }
}

public class ContactManagementSystem {
    private static final String FILE_NAME = "contacts.dat";
    private static ArrayList<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        loadContacts();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n📒 Simple Contact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addContact(sc);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact(sc);
                    break;
                case 4:
                    deleteContact(sc);
                    break;
                case 5:
                    saveContacts();
                    System.out.println("✅ Contacts saved. Exiting program...");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    // Add contact
    private static void addContact(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        contacts.add(new Contact(name, phone, email));
        System.out.println("✅ Contact added successfully!");
    }

    // View all contacts
    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("📭 No contacts found.");
        } else {
            System.out.println("\n📒 Contact List:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    // Edit a contact
    private static void editContact(Scanner sc) {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the contact number to edit: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index >= 0 && index < contacts.size()) {
            Contact c = contacts.get(index);
            System.out.print("Enter new name (" + c.name + "): ");
            String name = sc.nextLine();
            System.out.print("Enter new phone (" + c.phone + "): ");
            String phone = sc.nextLine();
            System.out.print("Enter new email (" + c.email + "): ");
            String email = sc.nextLine();

            if (!name.isEmpty()) c.name = name;
            if (!phone.isEmpty()) c.phone = phone;
            if (!email.isEmpty()) c.email = email;

            System.out.println("✅ Contact updated successfully!");
        } else {
            System.out.println("❌ Invalid contact number.");
        }
    }

    // Delete a contact
    private static void deleteContact(Scanner sc) {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the contact number to delete: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("🗑️ Contact deleted successfully!");
        } else {
            System.out.println("❌ Invalid contact number.");
        }
    }

    // Save contacts to file
    private static void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("❌ Error saving contacts.");
        }
    }

    // Load contacts from file
    @SuppressWarnings("unchecked")
    private static void loadContacts() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                contacts = (ArrayList<Contact>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("❌ Error loading contacts.");
            }
        }
    }
}
