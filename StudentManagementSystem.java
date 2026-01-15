import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// Student Class (OOP)
class Studentrecord {
    private String id;
    private String name;
    private int marks;

    public Studentrecord(String id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getMarks() { return marks; }

    @Override
    public String toString() {
        return id + "," + name + "," + marks;
    }

    // Convert CSV line → Student object
    public static Student fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            try {
                return new Student(parts[0], parts[1], Integer.parseInt(parts[2]));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

// GUI + Logic Class
public class StudentManagementSystem {
    private static final String FILE_NAME = "students.txt";
    private java.util.List<Student> studentList = new ArrayList<>();

    // GUI Components
    private JFrame frame;
    private JTextField idField, nameField, marksField, searchField;
    private JTextArea displayArea;

    public StudentManagementSystem() {
        // Load existing data from file
        loadStudents();

        // Create Frame
        frame = new JFrame("Student Management System");
        frame.setSize(500, 450);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels + Fields
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 30, 50, 25);
        frame.add(idLabel);

        idField = new JTextField();
        idField.setBounds(100, 30, 150, 25);
        frame.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 50, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 70, 150, 25);
        frame.add(nameField);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setBounds(30, 110, 50, 25);
        frame.add(marksLabel);

        marksField = new JTextField();
        marksField.setBounds(100, 110, 150, 25);
        frame.add(marksField);

        // Buttons
        JButton addButton = new JButton("Add Student");
        addButton.setBounds(280, 30, 150, 30);
        frame.add(addButton);

        JButton displayButton = new JButton("Display All");
        displayButton.setBounds(280, 70, 150, 30);
        frame.add(displayButton);

        JButton saveButton = new JButton("Save to File");
        saveButton.setBounds(280, 110, 150, 30);
        frame.add(saveButton);

        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setBounds(30, 160, 100, 25);
        frame.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(130, 160, 120, 25);
        frame.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(280, 160, 150, 30);
        frame.add(searchButton);

        // Display Area
        displayArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(30, 210, 400, 170);
        frame.add(scroll);

        // Event Handling
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayStudents();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveStudents();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        // Show Frame
        frame.setVisible(true);
    }

    // Add Student
    private void addStudent() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            int marks = Integer.parseInt(marksField.getText().trim());

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "ID and Name cannot be empty.");
                return;
            }

            Student s = new Student(id, name, marks);
            studentList.add(s);

            JOptionPane.showMessageDialog(frame, "Student Added Successfully!");
            idField.setText(""); nameField.setText(""); marksField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid marks! Enter a number.");
        }
    }

    // Display All
    private void displayStudents() {
        displayArea.setText("");
        if (studentList.isEmpty()) {
            displayArea.append("No records available.\n");
        } else {
            for (Student s : studentList) {
                displayArea.append(s.toString() + "\n");
            }
        }
    }

    // Search
    private void searchStudent() {
        String searchId = searchField.getText().trim();
        boolean found = false;
        displayArea.setText("");
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(searchId)) {
                displayArea.append("Found: " + s.toString() + "\n");
                found = true;
                break;
            }
        }
        if (!found) {
            displayArea.append("Student with ID " + searchId + " not found.\n");
        }
    }

    // Save to File
    private void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) {
                writer.write(s.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "Data saved to " + FILE_NAME);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving file: " + e.getMessage());
        }
    }

    // Load from File
    private void loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromString(line);
                if (s != null) studentList.add(s);
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // Main
    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}
