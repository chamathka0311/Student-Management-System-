package cw;
import com.sun.source.tree.TryTree;

import java.util.*;
import java.io.*;
import java.io.IOException;

public class main {

    private static int stdCount = 0;
    private static Student[] Studentlist = new Student[100];
    private static int moduleCount = 0;
    private static Module[][] Modulelist = new Module[100][3];
    private static double[] mark = new double[3];

    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        int option;
        while(true) {
            System.out.println("\n\n\tmenu\n "
                    + "1.Check Available seats\n "
                    + "2.Register Student\n "
                    + "3.Delete Student\n "
                    + "4.Find Student\n "
                    + "5.Store Student details\n "
                    + "6.Load Student details\n "
                    + "7.View list of Students\n "
                    + "8.Manage Student Details\n "
                    + "0.Exit\n");
            System.out.println("Select option : ");

            try {
                option = sc.nextInt();
                sc.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    checkAvailableSeats();
                    break;
                case 2:
                    registerStudent(sc);
                    break;
                case 3:
                    deleteStudent(sc);
                    break;
                case 4:
                    findStudent(sc);
                    break;
                case 5:
                    storeStudentDetails();
                    break;
                case 6:
                    loadStudentDetails();
                    break;
                case 7:
                    viewStudentsByName();
                    break;
                case 8:
                    manageStudentDetails(sc);
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void manageStudentDetails(Scanner sc) {

        while(true) {
            String choice;
            System.out.println("\n\n\t options \n"
                    + "a.Add Student Name\n"
                    + "b.Add Module Marks\n"
                    + "c.Generate Summary\n"
                    + "d.Generate Report\n"
                    + "e.Exit\n");
            System.out.println("Enter Choice");
            choice = sc.next();
            sc.nextLine();

            switch (choice) {
                case "a":
                    addStudentName(sc);
                    break;
                case "b":
                    addModuleMarks(sc);
                    break;
                case "c":
                    generateSummary(sc);
                    break;
                case "d":
                    generateReport();
                    break;
                case "e":
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addStudentName(Scanner sc) {

        String stdName = null;
        System.out.println("Enter student ID : ");
        String stdID = sc.nextLine();

        for (int i = 0; i < stdCount; i++) {
            if (Studentlist[i] != null && Studentlist[i].getStdID().equals(stdID))
            {
                System.out.println("Enter student name : ");
                stdName = sc.nextLine();
                Studentlist[i].setStdName(stdName);
                System.out.println("Student name added successfully.");
                return;
            }
        }
        System.out.println(stdID + " Not found!");
    }

    private static void addModuleMarks(Scanner sc) {

        System.out.println("Enter Student ID : ");
        String stdID = sc.nextLine();

        for (int i = 0; i < stdCount; i++) {
            if (Studentlist[i] != null && Studentlist[i].getStdID().equals(stdID))//check whether student ID exists
            {
                for(int j = 0; j < 3; j++) {
                    System.out.println("Enter marks for module :" + (j+1));
                    mark[j] = sc.nextDouble();
                    Modulelist[i][j] = new Module(mark[j]);//set module marks in a 2D array
                }
                System.out.println("Marks added successfully student " + stdID);
                return;
            }
        }
        System.out.println(stdID + " Not found!");
    }

    private static void generateSummary(Scanner sc) {
        int count = 0;
        System.out.println("Total Student Registrations: " + stdCount);

        for (int i = 0; i < stdCount; i++) {
            boolean allPass = true;
            if (Studentlist[i] != null) {
                for (int j = 0; j < 3; j++) {
                    if (Modulelist[i][j] == null || Modulelist[i][j].getMark() < 40)//if one module mark is less than 40 boolean turns false
                    {
                        allPass = false;
                        break;
                    }
                }
                if (allPass)//check whether boolean remains true
                {
                    count++;
                }
            }
        }

        System.out.println("Number of students who scored more than 40 in all modules: " + count);
    }

    private static void generateReport() {
        bubbleSort();
        for (int i = 0; i < stdCount; i++) {
            if (Studentlist[i] != null) {
                for (int j = 0; j < 3; j++) {
                    mark[j] = Modulelist[i][j].getMark();
                }

                double average = Module.calcAvg(mark);//call method from module class to calculate average
                String grade = Module.moduleGrade(average);//call method from module class to generate grade

                System.out.println("Student ID: " + Studentlist[i].getStdID());
                System.out.println("Student Name: " + Studentlist[i].getStdName());
                System.out.println("Total: " + (average * 3));
                System.out.println("Average Marks: " + average);
                System.out.println("Grade: " + grade + "\n");
            }
        }
    }

    private static void checkAvailableSeats()
    {
        loadStudentDetails();
        System.out.println(100 - stdCount);
        return;
    }

    private static void registerStudent(Scanner sc) {
        if (stdCount < 100) {
            System.out.println("Enter student ID : ");
            String stdID = sc.nextLine();

            // Validate student ID
            while (true) {
                if (!isValidStudentID(stdID)) {
                    System.out.println("Invalid student ID. Please enter a valid 8-digit student ID:");
                    stdID = sc.nextLine();
                } else {
                    boolean idExists = false;
                    for (int i = 0; i < stdCount; i++) {
                        if (Studentlist[i] != null && Studentlist[i].getStdID().equals(stdID)) {
                            idExists = true;
                            break;
                        }
                    }
                    if (idExists) {
                        System.out.println("Student ID has already been registered.");
                        return;
                    } else {
                        break; // Exit the while loop if ID is valid and does not exist
                    }
                }
            }


            // Register student
            Studentlist[stdCount] = new Student(stdID, null); // No student name provided
            for (int j = 0; j < 3; j++) {
                Modulelist[stdCount][j] = new Module(0.0); // set default values for module marks
            }
            stdCount++;
            System.out.println("Student has registered successfully");
        } else {
            System.out.println("No available seats");
        }
    }

    private static boolean isValidStudentID(String stdID) {
        return stdID.length() == 8 && stdID.startsWith("w") && stdID.substring(1).matches("\\d{7}");
    }



    private static void deleteStudent(Scanner sc) {
        System.out.println("Enter Student ID to delete : ");
        String ID = sc.nextLine();
        boolean found = false;// Flag to track if the student was found

        for (int i = 0; i < stdCount; i++) {
            if (Studentlist[i] != null && Studentlist[i].getStdID().equals(ID)) {

                // Shift all students and modules one position to the left
                for(int j = i; j< stdCount - 1; j++) {
                    Studentlist[j] = Studentlist[j+1];
                    Modulelist[j] = Modulelist[j+1];
                }

                // Set the last student and module to null because the array is now smaller
                Studentlist[stdCount-1] = null;
                Modulelist[stdCount-1] = null;
                stdCount--;
                System.out.println("Student has deleted successfully.");
                found = true;// Set the flag to true because the student was found
                break;
            }
        }

        // If the student was not found, print a message
        if (!found) {
            System.out.println("Student not found.");
        }
        System.out.println();

    }

    private static void findStudent(Scanner sc) {
        System.out.println("Enter Student ID : ");
        String stdID = sc.nextLine();

        System.out.println("\n  ID          NAME ");
        for(int i = 0; i < stdCount; i++) {
            if (Studentlist[i] != null && Studentlist[i].getStdID().equals(stdID))//check whether student ID exists
            {
                System.out.println(Studentlist[i].getStdID() + "\t" +Studentlist[i].getStdName());
            }

        }
        System.out.println();
    }

    private static void storeStudentDetails() {
        String filepath = "C:\\Users\\ASUS\\Desktop\\cw.txt";// Specify the file path

        FileWriter file = null;
        try {
            file = new FileWriter(filepath);// Create FileWriter object

            for (int i = 0; i < stdCount; i++) {
                if(Studentlist[i].getStdName() != null)// Check if the student name is not null
                {
                    file.append(Studentlist[i].getStdID() + "\t" + Studentlist[i].getStdName());
                }
                else {
                    file.append(Studentlist[i].getStdID() + "\t" + " - ");
                }
                for(int j = 0; j < 3; j++) {
                    if (Modulelist[i][j] != null) {
                        file.append("\t" + Modulelist[i][j].getMark());
                    } else {
                        file.append("\t0.0");
                    }
                }
                file.append("\n");
            }
            System.out.println("Written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // Ensure the FileWriter is closed to release system resources
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadStudentDetails() {
        File file = new File("C:\\Users\\ASUS\\Desktop\\cw.txt");// Specify the file path
        try{
            Scanner sc = new Scanner(file);
            stdCount = 0;

            while (sc.hasNextLine())// Loop through each line in the file
            {
                String line = sc.nextLine();// Read the next line
                String[] parts = line.split("\t");// Split the line into parts by tabs
                if(parts.length == 5)// Ensure the line has the expected number of parts
                {
                    String stdID = parts[0];// Get the student ID
                    String stdName = parts[1];// Get the student name
                    Studentlist[stdCount] = new Student(stdID, stdName);

                    Modulelist[stdCount] = new Module[3];
                    for(int j = 0; j < 3; j++) {
                        double mark = Double.parseDouble(parts[j + 2]);
                        Modulelist[stdCount][j] = new Module(mark);
                    }
                    stdCount++;
                }
            }
            //System.out.println("Student details loaded successfully.\n\n");
        }
        catch(IOException e) {

        }
    }

    private static void viewStudentsByName() {
        bubbleSort();// Sort the students by their IDs using bubble sort
        System.out.println("  ID          NAME ");
        for (int i = 0; i < stdCount; i++) {
            System.out.print(Studentlist[i].getStdID() + "\t" + Studentlist[i].getStdName());
            System.out.println();
        }

    }

    /*private static void bubbleSort() {
        boolean swapped;
        for (int i = 0; i < stdCount - 1; i++) {
            swapped = false;
            for (int j = 0; j < stdCount - i - 1; j++) {
                if (Studentlist[j].getStdID().compareTo(Studentlist[j + 1].getStdID()) > 0) {

                    Student tempStudent = Studentlist[j];
                    Studentlist[j] = Studentlist[j + 1];
                    Studentlist[j + 1] = tempStudent;

                    swapped = true;
                }
            }
        }
    }*/
    private static void bubbleSort() {
        boolean swapped;
        for (int i = 0; i < stdCount - 1; i++) {
            swapped = false;
            for (int j = 0; j < stdCount - i - 1; j++) {
                // Compare names instead of IDs
                if (Studentlist[j].getStdName().compareTo(Studentlist[j + 1].getStdName()) > 0) {

                    // Swap the students
                    Student tempStudent = Studentlist[j];
                    Studentlist[j] = Studentlist[j + 1];
                    Studentlist[j + 1] = tempStudent;

                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then the list is sorted
            if (!swapped) {
                break;
            }
        }
    }

    }



