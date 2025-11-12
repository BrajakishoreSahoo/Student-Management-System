import java.io.*;
import java.util.*;

class Student {
    String roll, name, dept;
    int marks;

    Student(String roll, String name, String dept, int marks) {
        this.roll = roll;
        this.name = name;
        this.dept = dept;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return roll + "," + name + "," + dept + "," + marks;
    }
}

public class StudentManagement {
    static Scanner sc = new Scanner(System.in);
    static String fileName = "students.txt";

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> deleteStudent();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() throws IOException {
        System.out.print("Enter Roll No: ");
        String roll = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
        bw.write(new Student(roll, name, dept, marks).toString());
        bw.newLine();
        bw.close();

        System.out.println(" Student added successfully!");
    }

    static void viewStudents() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("No records found!");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        System.out.println("\n--- Student List ---");
        System.out.printf("%-10s %-20s %-15s %-5s%n", "Roll No", "Name", "Department", "Marks");
        System.out.println("--------------------------------------------------------");
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                System.out.printf("%-10s %-20s %-15s %-5s%n", parts[0], parts[1], parts[2], parts[3]);
            }
        }
        br.close();
    }

    static void searchStudent() throws IOException {
        System.out.print("Enter Roll No to search: ");
        String roll = sc.nextLine();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        boolean found = false;
        while ((line = br.readLine()) != null) {
            if (line.startsWith(roll + ",")) {
                String[] parts = line.split(",");
                System.out.println("\n Student Found:");
                System.out.println("------------------------");
                System.out.println("Roll No    : " + parts[0]);
                System.out.println("Name       : " + parts[1]);
                System.out.println("Department : " + parts[2]);
                System.out.println("Marks      : " + parts[3]);
                found = true;
                break;
            }
        }
        if (!found) System.out.println(" Student not found!");
        br.close();
    }

    static void deleteStudent() throws IOException {
        System.out.print("Enter Roll No to delete: ");
        String rollToDelete = sc.nextLine();

        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("No records found!");
            return;
        }

        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        boolean deleted = false;

        while ((line = br.readLine()) != null) {
            if (line.startsWith(rollToDelete + ",")) {
                deleted = true;
                continue; // skip this line (delete it)
            }
            allLines.add(line);
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
        for (String l : allLines) {
            bw.write(l);
            bw.newLine();
        }
        bw.close();

        if (deleted) {
            System.out.println(" Student with Roll No " + rollToDelete + " deleted successfully!");
        } else {
            System.out.println(" Student not found!");
        }
    }
}
