import java.io.*;
import java.util.*;

class Student {
    private String id;
    private String name;
    private int marks;

    public Student(String id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getMarks() { return marks; }

    public void setMarks(int marks) { this.marks = marks; }

    @Override
    public String toString() {
        return id + "," + name + "," + marks;
    }

    // Convert from file line → Student object
    public static Student fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            try {
                return new Student(parts[0], parts[1], Integer.parseInt(parts[2]));
            } catch (NumberFormatException e) {
                System.out.println("Error parsing marks for: " + line);
            }
        }
        return null;
    }
}

public class FileHandlingApp {
    private static final String FILE_NAME = "students.txt";

    // Add new record
    public static void addStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(student.toString());
            writer.newLine();
            System.out.println("Student added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Read all records
    public static List<Student> readAllStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student != null) students.add(student);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No records found. File not created yet.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return students;
    }

    // Search by ID
    public static void searchStudent(String id) {
        List<Student> students = readAllStudents();
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                System.out.println("Student Found: " + s);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    // Update marks by ID
    public static void updateStudent(String id, int newMarks) {
        List<Student> students = readAllStudents();
        boolean updated = false;

        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                s.setMarks(newMarks);
                updated = true;
            }
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Student s : students) {
                    writer.write(s.toString());
                    writer.newLine();
                }
                System.out.println("Record updated successfully!");
            } catch (IOException e) {
                System.out.println("Error updating file: " + e.getMessage());
            }
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    // Display all records
    public static void displayStudents() {
        List<Student> students = readAllStudents();
        if (students.isEmpty()) {
            System.out.println("No records to display.");
        } else {
            System.out.println("---- Student Records ----");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Records Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Display All Students");
            System.out.println("4. Update Student Marks");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();
                    addStudent(new Student(id, name, marks));
                    break;

                case 2:
                    System.out.print("Enter Student ID to search: ");
                    String searchId = sc.nextLine();
                    searchStudent(searchId);
                    break;

                case 3:
                    displayStudents();
                    break;

                case 4:
                    System.out.print("Enter Student ID to update: ");
                    String updateId = sc.nextLine();
                    System.out.print("Enter new Marks: ");
                    int newMarks = sc.nextInt();
                    updateStudent(updateId, newMarks);
                    break;

                case 5:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}
