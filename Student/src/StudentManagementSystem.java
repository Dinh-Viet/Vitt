import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student {
    private String id;
    private String name;
    private double mark;

    public Student(String id, String name, double mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Mark: " + mark;
    }
}

public class StudentManagementSystem {
    private ArrayList<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                students.remove(student);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    public Student searchByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    public Student searchById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public void printStudentsDescendingOrderByMark() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s2.getMark(), s1.getMark());
            }
        });

        System.out.println("Students in descending order of mark:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        int choice;
        do {
            System.out.println("\n1. Add a new student");
            System.out.println("2. Delete a student");
            System.out.println("3. Search by name");
            System.out.println("4. Search by ID");
            System.out.println("5. Print student info in descending order of mark");
            System.out.println("6. Exit");
            System.out.print("#: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    String id = scanner.next();
                    System.out.print("Enter student name: ");
                    String name = scanner.next();
                    System.out.print("Enter student mark: ");
                    double mark = scanner.nextDouble();
                    sms.addStudent(new Student(id, name, mark));
                    System.out.println("Student added successfully.");
                    break;
                case 2:
                    System.out.print("Enter ID of student to delete: ");
                    String deleteId = scanner.next();
                    sms.deleteStudent(deleteId);
                    break;
                case 3:
                    System.out.print("Enter name to search: ");
                    String searchName = scanner.next();
                    Student foundByName = sms.searchByName(searchName);
                    if (foundByName != null) {
                        System.out.println("Student found: " + foundByName);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter ID to search: ");
                    String searchId = scanner.next();
                    Student foundById = sms.searchById(searchId);
                    if (foundById != null) {
                        System.out.println("Student found: " + foundById);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    sms.printStudentsDescendingOrderByMark();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
