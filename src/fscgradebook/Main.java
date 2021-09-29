/*Aidan Border
* planes4u@outlook.com
*
* */

package fscgradebook;

import java.io.*;
import java.util.*;

public class Main {

    /* main()
     *  Parameters: String[] args
     *  Returns: Void
     *  Description: Method invoked by the Java runtime when we run the project or run Main.java directly.
     *  @throws: FileNotFoundException */
    public static void main(String[] args) throws FileNotFoundException {

        File inputFile = new File("FSCgradeBook.in");
        Scanner in = new Scanner(inputFile);
        File outputFile = new File("FSCgradeBook.out");
        PrintWriter out = new PrintWriter(outputFile);

        if (!inputFile.exists()) {
            System.out.println("FATAL ERROR: The file " + inputFile + " cannot be read. Please ensure the file exists" +
                    " in the project directory. The program will now exit");
            System.exit(-3); // A bit of trivia here. Driver number 4 in Formula 1 is McLaren driver Daniel Ricciardo.
            // Nobody's going to see these exit codes, so I think I can have fun with them.
        }

        /*Setup for the program before we actually process any input*/

        int numCourses = in.nextInt();

        FSCCourseRoster[] courses = new FSCCourseRoster[numCourses];

        for (int i = 0; i < numCourses; i++) {
            courses[i] = new FSCCourseRoster();
            courses[i].setCourseNumber(in.next());
        }

        /*Main program execution loop.
         * This loop will run until the QUIT command is read, at which point we're done here.
         * We should start cleaning up after ourselves and prepare to exit.*/
        while (true) {
            switch (in.next()) {
                case "ADDRECORD":
                    out.println("Running ADDRECORD...");
                    addRecord(in, out, courses, numCourses);
                    break;
                case "DELETERECORD":
                    out.println("Running DELETERECORD...");
                    deleteRecord(in, out, courses, numCourses);
                    break;
                case "SEARCHBYID":
                    out.println("Running SEARCHBYID...");
                    searchByID(courses, numCourses, in, out);
                    break;
                case "SEARCHBYNAME":
                    out.println("Running SEARCHBYNAME...");
                    searchByName(courses, numCourses, in, out);
                    break;
                case "DISPLAYSTATS":
                    out.println("Running DISPLAYSTATS...");
                    displayStats(courses, numCourses, in, out);
                    break;
                case "DISPLAYSTUDENTS":
                    out.println("Running DISPLAYSTUDENTS...");
                    displayStudents(courses, numCourses, in, out);
                    break;
                /*This case is kinda special since we need to do our cleanup work here. If I'm going to just return
                from main() here I'm going to exit directly after the return statement.
                Note: The reason I don't use System.exit() here is because it's not convention. If I could exit with
                an F1 driver's number as the code I absolutely would. */
                case "QUIT":
                    out.println("Thank you for using the the FSC Grade Book.\n" +
                            "\n" +
                            "Goodbye.");
                    in.close();
                    out.close();
                    return;
            }
        }
    }

    public static void addRecord(Scanner in, PrintWriter out, FSCCourseRoster[] courses, int numCourses) {
        String courseNumber = in.next();
        int ID = in.nextInt();
        String firstName = in.next();
        String lastName = in.next();

        int[] examGrades = new int[3];
        for (int i = 0; i < 3; i++) {
            examGrades[i] = in.nextInt();
        }
        double finalGrade = getFinalGrade(examGrades);
        char letterGrade = getLetterGrade(finalGrade);

        Student newStudent = new Student(courseNumber, ID, firstName, lastName, examGrades, finalGrade,
                letterGrade);

        for (int i = 0; i < numCourses; i++) {
            if (courses[i].getCourseNumber().equals(courseNumber)) {
                courses[i].insert(newStudent);
            }
        }
        out.printf("   %s %s (ID# %d) has been added to %s.\n   Final Grade: %5.2f (%c)\n",
                newStudent.getFirstName(),
                newStudent.getLastName(), newStudent.getID(), newStudent.getCourseNumber(), newStudent.getFinalGrade(),
                newStudent.getLetterGrade());
    }

    public static double getFinalGrade(int[] examGrades) {
        return (examGrades[0] * 0.3) + (examGrades[1] * 0.3) + (examGrades[2] * 0.4);
    }

    public static char getLetterGrade(double finalGrade) {
        char studentLetterGrade = 'E';
        if (finalGrade >= 90.0) {
            return studentLetterGrade = 'A';
        } else if (finalGrade >= 80.0 && finalGrade <= 89.9) {
            return studentLetterGrade = 'B';
        } else if (finalGrade >= 70.0 && finalGrade <= 79.9) {
            return studentLetterGrade = 'C';
        } else if (finalGrade >= 60.0 && finalGrade <= 69.9) { //nice
            return studentLetterGrade = 'D';
        } else if (finalGrade >= 0 && finalGrade <= 59.9) {
            return studentLetterGrade = 'F';
        }

        return studentLetterGrade;
    }

    public static void deleteRecord(Scanner in, PrintWriter out, FSCCourseRoster[] courses, int numCourses) {
        int studentID = in.nextInt();

        for (int i = 0; i < numCourses; i++) {
            if (courses[i].searchID(studentID)) {
                Student deletedBoi = courses[i].findNode(studentID);
                out.printf("\t%s %s (ID %d) has been deleted from %s\n", deletedBoi.getFirstName(),
                        deletedBoi.getLastName(), deletedBoi.getID(), deletedBoi.getCourseNumber());
                courses[i].delete(studentID);
            }
        }
    }

    public static void searchByID(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        int ID = in.nextInt();
        for (int i = 0; i < numCourses; i++) {
            if (courses[i].searchID(ID)) {
                out.println(courses[i].findNode(ID).toString());
            } else {
                out.printf("	ERROR: there is no record for student ID# %d.\n", ID);
                return;
            }
        }
    }
    public static void searchByName(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        String firstName = in.next();
        String lastName = in.next();
        for (int i = 0; i < numCourses; i++) {
            if (courses[i].searchName(firstName, lastName)) {
                out.println(courses[i].findNode(firstName, lastName).toString());
            } else {
                out.printf("	ERROR: there is no record for student \"%s %s\".\n", firstName, lastName);
                return;
            }
        }
    }

    public static void displayStats(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        String courseToPrint = in.next();
        if (courseToPrint.equals("ALL")) {
            courses[0].printStats(out); //TODO: Fix this fuckery
        } else {
            for (int i = 0; i < numCourses; i++) {
                if (courses[i].getCourseNumber().equals(courseToPrint)) {
                    out.println(courses[i].toString());
                }
            }
        }
    }

    public static void displayStudents(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        String courseNum = in.next();
        if (Student.getNumStudents() == 0) {
            out.println("\tERROR: there are no students currently in the system.");
            return;
        }
        if (courseNum.equals("ALL")) {
            for (int i = 0; i < numCourses; i++) {
                out.println(courses[i].toString());
            }
        } else {
            for (int i = 0; i < numCourses; i++) {
                if (courses[i].getCourseNumber().equals(courseNum)) {
                    out.println(courses[i].toString());
                }
            }
        }
    }
}
