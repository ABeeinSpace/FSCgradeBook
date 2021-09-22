/*Aidan Border
* planes4u@outlook.com
* */

package fscgradebook;

import java.io.*;
import java.util.*;

public class Main {

    /* main()
    *  Parameters: String[] args
    *  Returns: Void
    *  Description: Method invoked by the Java runtime when we run the project or run Main.java directly.
    *  Special: Throws a FileNotFoundException */
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

        /*Main program execution loop.
        * This loop will run until the QUIT command is read, at which point we're done here. We should immediately
        * start cleaning up after ourselves and preparing to exit.
        * An alternative to a while true here would be a do-while. I may end up doing that instead of the while true
        * because it's a small bit easier to guarantee we're not executing longer than we're supposed to.*/
        while (true) {
            switch (in.next()) {
                case "ADDRECORD":
//                    addRecord();
                    out.println("Running ADDRECORD...");
                    break;
                case "DELETERECORD":
                    out.println("Running DELETERECORD...");
//                    deleteRecord();
                    break;
                case "SEARCHBYID":
                    out.println("Running SEARCHBYID...");
//                    searchByID();
                    break;
                case "SEARCHBYNAME":
                    out.println("Running SEARCHBYNAME...");
//                    searchByName();
                    break;
                case "DISPLAYSTATS":
                    out.println("Running DISPLAYSTATS...");
//                    displayStats();
                    break;
                case "DISPLAYSTUDENTS":
                    out.println("Running DISPLAYSTUDENTS...");
//                    displayStudents();
                    break;
                /*This case is kinda special since we need to do our cleanup work here. If I'm going to just return
                from main() here I'm going to exit directly after the return statement.*/
                case "QUIT":
                    in.close();
                    out.close();
                    return;
            }
        }
    }
}
