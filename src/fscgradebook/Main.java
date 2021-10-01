/*Aidan Border
* planes4u@outlook.com
*
* */

package fscgradebook;

import java.io.*;
import java.util.*;

public class Main {

    /*  main()
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
            System.exit(-3); // A bit of trivia here. Driver number 3 in Formula 1 is McLaren driver Daniel Ricciardo.
            // Nobody's going to see these exit codes, so I think I can have fun with them.
        }

        out.println("Welcome to the FSC Grade Book.\n");


        /*Setup for the program before we actually process any input*/

        int numCourses = in.nextInt();

        FSCCourseRoster[] courses = new FSCCourseRoster[numCourses];

        out.println("The following course(s) have been added to the database:");

        for (int i = 0; i < numCourses; i++) {
            courses[i] = new FSCCourseRoster();
            courses[i].setCourseNumber(in.next());
            out.printf("\t%s\n", courses[i].getCourseNumber());
        }



        /*Main program execution loop.
         * This loop will run until the QUIT command is read, at which point we're done here. */
        while (true) {
            out.println(); //This println call is just to make sure the output is properly spaced apart
            switch (in.next()) {
                case "ADDRECORD":
                    addRecord(in, out, courses, numCourses);
                    break;
                case "DELETERECORD":
                    deleteRecord(in, out, courses, numCourses);
                    break;
                case "SEARCHBYID":
                    searchByID(courses, numCourses, in, out);
                    break;
                case "SEARCHBYNAME":
                    searchByName(courses, numCourses, in, out);
                    break;
                case "DISPLAYSTATS":
                    displayStats(courses, numCourses, in, out);
                    break;
                case "DISPLAYSTUDENTS":
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

    //region addRecord and supporting methods
    /*  addRecord()
     *  Parameters: Scanner in, PrintWriter out, FSCCourseRoster[] courses, int numCourses
     *  Returns: Void
     *  Description: Adds a record to the relevant Linked List. */
    public static void addRecord(Scanner in, PrintWriter out, FSCCourseRoster[] courses, int numCourses) {
        out.println("Command: ADDRECORD");
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
                break;
            } else {
                if (i == courses.length - 1) {
                    out.printf("\tERROR: cannot add student. Course \"%s\" does not exist.\n", courseNumber);
                    return;
                }
            }
        }

        out.printf("   %s %s (ID# %d) has been added to %s.\n   Final Grade: %5.2f (%c).\n",
                newStudent.getFirstName(),
                newStudent.getLastName(), newStudent.getID(), newStudent.getCourseNumber(), newStudent.getFinalGrade(),
                newStudent.getLetterGrade());
    }

    /*  getFinalGrade()
     *  Parameters: int[] examGrades
     *  Returns: double
     *  Description: Supporting method for addRecord(). Computes the final grade as a double and returns it. */
    public static double getFinalGrade(int[] examGrades) {
        return (examGrades[0] * 0.3) + (examGrades[1] * 0.3) + (examGrades[2] * 0.4);
    }

    /*  getLetterGrade()
     *  Parameters: double finalGrade
     *  Returns: char
     *  Description: Supporting method for addRecord(). Computes the earned letter grade and returns it. */
    public static char getLetterGrade(double finalGrade) {
        char studentLetterGrade = 'E';
        if (finalGrade >= 90.0) {
            return studentLetterGrade = 'A';
        } else if (finalGrade >= 80.0 && finalGrade <= 89.9) {
            return studentLetterGrade = 'B';
        } else if (finalGrade >= 70.0 && finalGrade <= 79.9) {
            return studentLetterGrade = 'C';
        } else if (finalGrade >= 60.0 && finalGrade <= 69.9) { // nice
            return studentLetterGrade = 'D';
        } else if (finalGrade >= 0 && finalGrade <= 59.9) {
            return studentLetterGrade = 'F';
        }

        return studentLetterGrade;
    }
    //endregion

    /*  deleteRecord()
     *  Parameters: Scanner in, PrintWriter out, FSCCourseRoster[] courses, int numCourses
     *  Returns: void
     *  Description: Deletes a record from the relevant Linked List */
    public static void deleteRecord(Scanner in, PrintWriter out, FSCCourseRoster[] courses, int numCourses) {
        out.println("Command: DELETERECORD");
        int studentID = in.nextInt();

        for (int i = 0; i < numCourses; i++) {
            if (courses[i].searchID(studentID)) {
                Student deletedBoi = courses[i].findNode(studentID);
                courses[i].delete(studentID);
                out.printf("\t%s %s (ID# %d) has been deleted from %s.\n", deletedBoi.getFirstName(),
                        deletedBoi.getLastName(), deletedBoi.getID(), deletedBoi.getCourseNumber());
                Student.decrementNumStudents();
            }
        }
    }

    /*  searchByID()
     *  Parameters: FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out
     *  Returns: void
     *  Description: Searches the relevant Linked List for an ID. If it's found, print the information we have on the
     *  student. If not, print an error. */
    public static void searchByID(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        out.println("Command: SEARCHBYID");
        int ID = in.nextInt();
        for (int i = 0; i < numCourses; i++) {
            if (courses[i].searchID(ID)) {
                out.println(courses[i].findNode(ID).toString());
                return;
            }
        }
        out.printf("\tERROR: there is no record for student ID# %d.\n", ID);
    }
    /*  searchByName()
     *  Parameters: FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out
     *  Returns: void
     *  Description: Searches the relevant Linked List for a student's name. If it's found, print the information we
     * have on the student. If not, print an error. */
    public static void searchByName(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        out.println("Command: SEARCHBYNAME");
        String firstName = in.next();
        String lastName = in.next();
        for (int i = 0; i < numCourses; i++) {
            if (courses[i].searchName(firstName, lastName)) {
                out.println(courses[i].findNode(firstName, lastName).toString());
                return;
            }
        }
        out.printf("\tERROR: there is no record for student \"%s %s\".\n", firstName, lastName);
    }

    /*  displayStats()
     *  Parameters: FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out
     *  Returns: void
     *  Description: Displays statistics on a course or the gradebook as a whole. */
    public static void displayStats(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        String courseToPrint = in.next();
        out.println("Command: DISPLAYSTATS ("+ courseToPrint + ")");
        if (courseToPrint.equals("ALL")) {
            printAllStats(courses, out, numCourses); //TODO: Fix this fuckery
        } else {
            for (int i = 0; i < numCourses; i++) {
                if (courses[i].getCourseNumber().equals(courseToPrint)) {
                    courses[i].printStats(out);
                }
            }
        }
    }

    /*  displayStudents()
     *  Parameters: FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out
     *  Returns: void
     *  Description: Searches the relevant Linked List for an ID. If it's found, print the information we have on the
     *  student. If not, print an error. */
    public static void displayStudents(FSCCourseRoster[] courses, int numCourses, Scanner in, PrintWriter out) {
        String courseNum = in.next();
        out.println("Command: DISPLAYSTUDENTS ("+ courseNum + ")");
        if (Student.getNumStudents() == 0) {
            out.println("\tERROR: there are no students currently in the system.");
            return;
        }
        if (courseNum.equals("ALL")) {
            for (int i = 0; i < numCourses; i++) {
                if (courses[i].isEmpty()) {
                    continue;
                } else {
                    out.print(courses[i].toString());
                }
            }
        } else {
            for (int i = 0; i < numCourses; i++) {
                if (courses[i].getCourseNumber().equals(courseNum)) {
                    if (courses[i].isEmpty()) {
                        out.printf("\tERROR: there are no student records for %s.\n",courseNum);
                    } else {
                        out.print(courses[i].toString());
                    }
                }
            }
        }
    }

    /*printAllStats()
    * Parameters: FSCCourseRoster[] courses, PrintWriter out, int numCourses
    * Returns: void
    * Description: Helper method for displayStats*/
    public static void printAllStats(FSCCourseRoster[] courses, PrintWriter out, int numCourses) {
//		// We need to traverse...so we need a help ptr
//		Student helpPtr = head;
//		// Traverse to correct insertion point
//		String output = "";
//		while (helpPtr != null) {
//			// Print the data value of the node
//			output += helpPtr.toString();
//			// Step one node over
//			helpPtr = helpPtr.getNext();
//		}
//		return output;

        if (Student.getNumStudents() == 0) {
            out.println("Statistical Results for All Courses:");
            out.printf("   Total number of student records: 0\n");
            out.printf("   Average Score: 0.00\n");
            out.printf("   Highest Score: 0.00\n");
            out.printf("   Lowest Score:  0.00\n");

            out.printf("   Total 'A' Grades: 0  (0.00%% of class)\n");
            out.printf("   Total 'B' Grades: 0  (0.00%% of class)\n");
            out.printf("   Total 'C' Grades: 0  (0.00%% of class)\n");
            out.printf("   Total 'D' Grades: 0  (0.00%% of class)\n");
            out.printf("   Total 'F' Grades: 0  (0.00%% of class)\n");
        } else {
        double sum = 0.0;
        double average = 0.0;
        double highest = 0.0;
        double lowest = 0.0;

        for (int i = 0; i < numCourses; i++) {
            Student helpPtr = courses[i].getHead();
            if (courses[i].isEmpty()) {
                continue;
            } else {
                while (helpPtr != null) {
                    lowest = helpPtr.getFinalGrade();
                    if (helpPtr.getFinalGrade() > highest) {
                        highest = helpPtr.getFinalGrade();
                    } else if (helpPtr.getFinalGrade() < lowest) {
                        lowest = helpPtr.getFinalGrade();
                    }
                    sum = sum + helpPtr.getFinalGrade();
                    helpPtr = helpPtr.getNext();
                }
            }
        }

        average = sum / (double)(Student.getNumStudents());

        out.printf("Statistical Results for All Courses:\n");
        out.printf("\tTotal number of student records: %d\n", Student.getNumStudents() - 1);
        out.printf("\tAverage Score: %3.2f\n", average);
        out.printf("\tHighest Score: %3.2f\n", highest);
        out.printf("\tLowest Score:%7.2f\n", lowest);
        if (Student.getNumStudents() == 0) {
            out.printf("\tTotal 'A' Grades: %1d (%6.2f%% of class)\n", gradeCounter('A', courses, numCourses),
                    (gradeCounter('A', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
            out.printf("\tTotal 'B' Grades: %1d (%6.2f%% of class)\n", gradeCounter('B', courses, numCourses),
                    (gradeCounter('B', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
            out.printf("\tTotal 'C' Grades: %1d (%6.2f%% of class)\n", gradeCounter('C', courses, numCourses),
                    (gradeCounter('C', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
            out.printf("\tTotal 'D' Grades: %1d (%6.2f%% of class)\n", gradeCounter('D', courses, numCourses),
                    (gradeCounter('D', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
            out.printf("\tTotal 'F' Grades: %1d (%6.2f%% of class)\n", gradeCounter('F', courses, numCourses),
                    (gradeCounter('F', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
        }
        out.printf("\tTotal 'A' Grades: %1d (%.2f%% of class)\n", gradeCounter('A', courses, numCourses),
                (gradeCounter('A', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
        out.printf("\tTotal 'B' Grades: %1d (%.2f%% of class)\n", gradeCounter('B', courses, numCourses),
                (gradeCounter('B', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
        out.printf("\tTotal 'C' Grades: %1d (%.2f%% of class)\n", gradeCounter('C', courses, numCourses),
                (gradeCounter('C', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
        out.printf("\tTotal 'D' Grades: %1d (%.2f%% of class)\n", gradeCounter('D', courses, numCourses),
                (gradeCounter('D', courses, numCourses) / (double)(Student.getNumStudents())) * 100);
        out.printf("\tTotal 'F' Grades: %1d (%.2f%% of class)\n", gradeCounter('F', courses, numCourses),
                (gradeCounter('F', courses, numCourses) / (double)(Student.getNumStudents())) * 100);

        }
    }

    // gradeCounter()
    // Parameters: char searchTerm, FSCStudent[] students, int numCourses
    // Returns: Integer
    // Description: Helper method for displayStats.
    public static int gradeCounter(char searchTerm, FSCCourseRoster[] courses, int numCourses) {
        int gradeCounter = 0;
        for (int i = 0; i < numCourses; i++) {
            Student helpPtr = courses[i].getHead();
            while (helpPtr != null) {
                if (helpPtr.getLetterGrade() == searchTerm && helpPtr.getFinalGrade() != 0) {
                    gradeCounter++;
                }
                helpPtr = helpPtr.getNext();
            }
        }

        return gradeCounter;
    }
}
