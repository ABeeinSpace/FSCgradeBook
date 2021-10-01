package fscgradebook;

import java.io.PrintWriter;
import java.util.Objects;

public class FSCCourseRoster {
	private Student head;
	private String courseNumber;
	private int numStudents;

	public FSCCourseRoster(Student head, String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public FSCCourseRoster() {
	}

	public boolean isEmpty() {
		return head == null;
	}
	public Student getHead() {
		return head;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public boolean searchID(int searchTerm) {
		return searchID(head, searchTerm);
	}

	private boolean searchID(Student p,int searchTerm) {
		Student hp = p;
		/*We loop while hp does NOT equal null because if we tried to execute line 42 against a null value
		Java would get angry at us and we would crash and burn.*/
		while (hp != null) {
			if (hp.getID() == searchTerm) {
				/*We have to check the last name in case two students share a first name. */
					return true;
			}
			hp = hp.getNext();
		}
		return false;
	}
	public boolean searchName(String firstName, String lastName) {
		return searchName(head, firstName, lastName);
	}

	private boolean searchName(Student p, String firstName, String lastName) {
		Student hp = p;
		/*We loop while hp does NOT equal null because if we tried to execute line 42 against a null value
		Java would get angry at us and we would crash and burn.*/
		while (hp != null) {
			if (Objects.equals(hp.getFirstName(), firstName)) {
				/*We have to check the last name in case two students share a first name. */
				if (Objects.equals(hp.getLastName(), lastName)) {
					return true;
				}
			}
			hp = hp.getNext();

		}
		return false;
	}

	public Student findNode(int data) {
		Student bro = findNode(head, data);
		return bro;
	}

	private Student findNode(Student p, int data) {
		Student helpPtr = p;
		while (helpPtr != null) {
			if (helpPtr.getID() == data)
				return helpPtr;
			helpPtr = helpPtr.getNext();
		}
		return null;
	}
	public Student findNode(String firstName, String lastName) {
		Student bro = findNode(head, firstName, lastName);
		return bro;
	}

	private Student findNode(Student p, String firstName, String lastName) {
		Student helpPtr = p;
		while (helpPtr != null) {
			if (helpPtr.getFirstName().equals(firstName)) {
				if (helpPtr.getLastName().equals(lastName))	{
					return helpPtr;
				}
			}
			helpPtr = helpPtr.getNext();
		}
		return null;
	}

	public void insert(Student newStudent) {
		head = insert(head, newStudent);
		numStudents++;
	}

	private Student insert(Student head, Student newStudent) {
		// IF there is no list, newNode will be the first node, so just return it
		if (head == null || head.getID() > newStudent.getID()) {
			head = newStudent;
			return head;
		}

		// ELSE, we have a list. Insert the new node at the correct location
		else {
			// We need to traverse to the correct insertion location...so we need a help ptr
			Student helpPtr = head;
			// Traverse to correct insertion point
			while (helpPtr.getNext() != null) {
				if (helpPtr.getNext().getID() > newStudent.getID())
					break; // we found our spot and should break out of the while loop
				helpPtr = helpPtr.getNext();
			}
			// Now make the new node. Set its next to point to the successor node.
			// And then make the predecessor node point to the new node
			newStudent.setNext(helpPtr.getNext());
//			Student newNode = new Student(data, helpPtr.getNext());
			helpPtr.setNext(newStudent);
			numStudents++;
		}
		// Return head
		return head;
	}

	public void delete(int ID) {
		head = delete(head, ID);
	}

	private Student delete(Student head, int ID) {
		// We can only delete if the list has nodes (is not empty)
		if (!isEmpty()) {
			// IF the first node (at the head) has the data value we are wanting to delete
			// we found it. Delete by skipping the node and making head point to the next node.
			if (head.getID() == ID) {
				head = head.getNext();
				numStudents--;
			}
			// ELSE, the data is perhaps somewhere else in the list...so we must traverse and look for it
			else {
				// We need to traverse to find the data we want to delete...so we need a help ptr
				Student helpPtr = head;
				// Traverse to correct deletion point
				while (helpPtr.getNext() != null) {
					if (helpPtr.getNext().getID() == ID) {
						helpPtr.setNext(helpPtr.getNext().getNext());
						numStudents--;
						break; // we deleted the value and should break out of the while loop
					}
					helpPtr = helpPtr.getNext();
				}
			}
			// return the possibly updated head of the list
			numStudents--;
			return head;
		}
		return head;
	}

	public void printRoster() {

	}

	public void printStats(PrintWriter out) {
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
			Student helpPtr = head;
			double sum = 0.0;
			double average = 0.0;
			double highest = 0.0;
			double lowest = 0.0;


			while (helpPtr != null) {
				lowest = helpPtr.getFinalGrade();
				if (helpPtr.getFinalGrade() > highest) {
					highest = helpPtr.getFinalGrade();
				} else if (helpPtr.getFinalGrade() < lowest) {
					lowest = helpPtr.getFinalGrade();
				}
				sum += helpPtr.getFinalGrade();
				helpPtr = helpPtr.getNext();
			}
			average = (double) sum / (numStudents);

			out.printf("Statistical Results of %s:\n", courseNumber);
			out.printf("\tTotal number of student records: %d\n", numStudents - 1);
			out.printf("\tAverage Score: %3.2f\n", average);
			out.printf("\tHighest Score: %3.2f\n", highest);
			out.printf("\tLowest Score:%7.2f\n", lowest);
			if (numStudents == 0) {
				out.printf("\tTotal 'A' Grades: %1d (%.2f%% of class)\n", gradeCounter('A'),
						(gradeCounter('A') / (double)(numStudents)) * 100);
				out.printf("\tTotal 'B' Grades: %1d (%.2f%% of class)\n", gradeCounter('B'),
						(gradeCounter('B') / (double)(numStudents)) * 100);
				out.printf("\tTotal 'C' Grades: %1d (%.2f%% of class)\n", gradeCounter('C'),
						(gradeCounter('C') / (double)(numStudents)) * 100);
				out.printf("\tTotal 'D' Grades: %1d (%.2f%% of class)\n", gradeCounter('D'),
						(gradeCounter('D') / (double)(numStudents)) * 100);
				out.printf("\tTotal 'F' Grades: %1d (%.2f%% of class)\n", gradeCounter('F'),
						(gradeCounter('F') / (double)(numStudents)) * 100);
			}
			out.printf("\tTotal 'A' Grades: %1d (%.2f%% of class)\n", gradeCounter('A'),
					(gradeCounter('A') / (double)(numStudents - 1)) * 100);
			out.printf("\tTotal 'B' Grades: %1d (%.2f%% of class)\n", gradeCounter('B'),
					(gradeCounter('B') / (double)(numStudents - 1)) * 100);
			out.printf("\tTotal 'C' Grades: %1d (%.2f%% of class)\n", gradeCounter('C'),
					(gradeCounter('C') / (double)(numStudents - 1)) * 100);
			out.printf("\tTotal 'D' Grades: %1d (%.2f%% of class)\n", gradeCounter('D'),
					(gradeCounter('D') / (double)(numStudents - 1)) * 100);
			out.printf("\tTotal 'F' Grades: %1d (%.2f%% of class)\n", gradeCounter('F'),
					(gradeCounter('F') / (double)(numStudents - 1)) * 100);

		}
	}

	// searchByID()
	// Parameters: FSCStudent[] students, char searchTerm
	// Returns: Integer
	// Description: Iterates through the students[] array and returns the amount of a given letter grade that exists in the array.
	public int gradeCounter(char searchTerm) {
		int gradeCounter = 0;
		Student helpPtr = head;
		while (helpPtr != null) {
			if (helpPtr.getLetterGrade() == searchTerm && helpPtr.getFinalGrade() != 0) {
				gradeCounter++;
			}
			helpPtr = helpPtr.getNext();
		}
		return gradeCounter;
	}


	@Override
	public String toString() {
		String output = "";
		output += String.format("Course Roster for %s:\n", courseNumber);
		Student helpPtr = head;
		while (helpPtr != null) {
			output += helpPtr.displayStudentsToString();
			helpPtr = helpPtr.getNext();
		}
		return output;
	}
}
