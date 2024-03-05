import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class PhoneBookUI {
    private PhoneBook phoneBook;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField searchField;

    public PhoneBookUI() {
        phoneBook = new PhoneBook();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Phone Book");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = searchField.getText().trim();
                Contact contact = phoneBook.searchContact(name);
                if (contact != null) {
                    textArea.setText(contact.toString());
                } else {
                    textArea.setText("Contact not found.");
                }
            }
        });
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter name:");
                String phoneNumber = JOptionPane.showInputDialog(frame, "Enter phone number:");
                String email = JOptionPane.showInputDialog(frame, "Enter email:");
                phoneBook.addContact(name, phoneNumber, email);
                updateTextArea();
            }
        });
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter name to remove:");
                phoneBook.removeContact(name);
                updateTextArea();
            }
        });

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportContactsToFile();
            }
        });

        JButton printButton = new JButton("Print List");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printContacts();
            }
        });



        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exportButton);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        for (Contact contact : phoneBook.getContacts()) {
            sb.append(contact).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void printContacts() {
        System.out.println("\nContacts List:");
        for (Contact contact : phoneBook.getContacts()) {
            System.out.println(contact);
        }
    }

    public static void main(String[] args) {
        PhoneBookUI phoneBookUI = new PhoneBookUI();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*******************************************");
            System.out.println("(A)dd\n(D)elete\n(E)mail\n(P)rint List\n(S)earch\n(Q)uit");
            System.out.println("*******************************************");
            System.out.print("Please enter a command: ");
            String command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "A":
                    phoneBookUI.addContactFromConsole();
                    break;
                case "D":
                    phoneBookUI.removeContactFromConsole();
                    break;
                case "E":
                    phoneBookUI.printEmailFromConsole();
                    break;
                case "P":
                    phoneBookUI.printContacts();
                    break;
                case "S":
                    phoneBookUI.searchContactFromConsole();
                    break;
                case "Q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
    private void printEmailFromConsole() {
        System.out.print("Enter name to print email: ");
        String name = new Scanner(System.in).nextLine();
        Contact contact = phoneBook.searchContact(name);
        if (contact != null) {
            System.out.println("Email: " + contact.getEmail());
        } else {
            System.out.println("Contact not found.");
        }
    }

    private void searchContactFromConsole() {
        System.out.print("Enter name to search: ");
        String name = new Scanner(System.in).nextLine();
        Contact contact = phoneBook.searchContact(name);
        if (contact != null) {
            System.out.println("Contact found: " + contact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    private void addContactFromConsole() {
        System.out.print("Enter name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = new Scanner(System.in).nextLine();
        System.out.print("Enter email: ");
        String email = new Scanner(System.in).nextLine();
        phoneBook.addContact(name, phoneNumber, email);
        updateTextArea();
    }
    private void exportContactsToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Contacts");
        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                for (Contact contact : phoneBook.getContacts()) {
                    writer.println(contact);
                }
                writer.flush();
                JOptionPane.showMessageDialog(frame, "Contacts exported successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error exporting contacts.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeContactFromConsole() {
        System.out.print("Enter name to remove: ");
        String name = new Scanner(System.in).nextLine();
        phoneBook.removeContact(name);
        updateTextArea();
    }
}
