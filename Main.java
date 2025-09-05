/* Create a Student Record Management system that allows the user to input, display, and 
calculate grades for students. Implement a class-based structure using Object- Oriented 
Programming principles to manage student data such as roll number, name, course, marks, 
and grade. The program should also allow the display of student records and calculate the 
grade based on marks */

import java.util.Scanner;
// ====================== 
// Task 1: Student Class 
// ====================== 
class Student{
    String rollNo;
    String name;
    String course;
    double marks;
    String grade;
    Scanner sc = new Scanner(System.in);

    // Method to input student details 
    public void inputStudent(){
        System.out.print("Enter Roll Number: ");
        rollNo = sc.nextLine();

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Marks: ");
        marks = sc.nextDouble();
        sc.nextLine(); // Consume newline 
        calculateGrade(); // Calculate grade after marks are entered
    }

      // Method to calculate grade based on marks 
      public void calculateGrade(){
        if(marks >= 90)grade = "A+";
         else if (marks >= 80) grade = "A"; 
        else if (marks >= 70) grade = "B"; 
        else if (marks >= 60) grade = "C"; 
        else if (marks >= 50) grade = "D"; 
        else grade = "F";
      }

       // Method to display student details 
    public void displayStudent() { 
        System.out.println("Roll No: " + rollNo); 
        System.out.println("Name: " + name); 
        System.out.println("Course: " + course); 
        System.out.println("Marks: " + marks); 
        System.out.println("Grade: " + grade); 
        System.out.println("-----------------------------------"); 
    }
}
// ======================================== 
// Task 2: Student Management System Class 
// ========================================

class StudentManagementSystem { 
    Student[] students;  // Simple array to store students 
    int studentCount; 
    Scanner sc = new Scanner(System.in); 
 
    public void initializeSystem(int size) { 
        students = new Student[size]; 
        studentCount = 0; 
    } 
 
    // Method to add new student 
    public void addStudent() { 
        if (studentCount >= students.length) { 
            System.out.println("Cannot add more students. Array is full!"); 
            return; 
        } 
        Student s = new Student(); 
        s.inputStudent(); 
        students[studentCount] = s; 
        studentCount++; 
        System.out.println("Student added successfully!\n"); 
    }
      // Method to display all student records 
    public void displayAllStudents() { 
        if (studentCount == 0) { 
            System.out.println("No student records available!\n"); 
            return; 
        } 
        System.out.println("======= Student Records ======="); 
        for (int i = 0; i < studentCount; i++) { 
            students[i].displayStudent(); 
        } 
    } 

    public Scanner getSc() {
        return sc;
    }
}
// ========================== 
// Task 3: Main Driver Class 
// ========================== 
public class Main { 
    public static void main(String[] args) { 
        try (Scanner sc = new Scanner(System.in)) {
            StudentManagementSystem sms = new StudentManagementSystem();
            
            System.out.print("Enter the maximum number of students: ");
            int size = sc.nextInt();
            sc.nextLine(); // Consume newline
            sms.initializeSystem(size);
            
            int choice;
            do {
                System.out.println("\n===== Student Record Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Display All Students");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1 -> sms.addStudent();
                    case 2 -> sms.displayAllStudents();
                    case 3 -> System.out.println("Exiting the program. Goodbye!");
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } while (choice != 3);
        } 
} 
}




