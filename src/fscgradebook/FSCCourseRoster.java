package fscgradebook;

import java.io.PrintWriter;
import java.util.Objects;

public class FSCCourseRoster {
	private Student head;
	private String courseNumber;

	public FSCCourseRoster(Student head, String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public FSCCourseRoster() {
	}

	public boolean isEmpty() {
		return head == null;
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

	private boolean searchID(Student p ,int searchTerm) {
		Student hp = p;
		/*We loop while hp.getNext() does NOT equal null because if we tried to execute line 42 against a null value
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
		/*We loop while hp.getNext() does NOT equal null because if we tried to execute line 42 against a null value
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
	}

	private Student insert(Student head, Student newStudent) {
		// IF there is no list, newNode will be the first node, so just return it
		if (head == null) {
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
			}
			// ELSE, the data is perhaps somewhere else in the list...so we must traverse and look for it
			else {
				// We need to traverse to find the data we want to delete...so we need a help ptr
				Student helpPtr = head;
				// Traverse to correct deletion point
				while (helpPtr.getNext() != null) {
					if (helpPtr.getNext().getID() == ID) {

						helpPtr.setNext(helpPtr.getNext().getNext());
						break; // we deleted the value and should break out of the while loop
					}
					helpPtr = helpPtr.getNext();
				}
			}
			// return the possibly updated head of the list
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
			out.println("Statistical Results for All Courses:\n");
			out.printf("   Total number of student records: 0\n");
			out.printf("   Average Score: 0.00\n");
			out.printf("   Highest Score: 0.00\n");
			out.printf("   Lowest Score:  0.00\n");

			out.printf("   Total 'A' Grades: 0  (0%% of class)\n");
			out.printf("   Total 'B' Grades: 0  (0%% of class)\n");
			out.printf("   Total 'C' Grades: 0  (0%% of class)\n");
			out.printf("   Total 'D' Grades: 0  (0%% of class)\n");
			out.printf("   Total 'F' Grades: 0  (0%% of class)\n");
			out.println();
		}
//		else {
//			double sumOfGrades = 0.0;
//			double averageGrade = 0.0;
//			double highestGrade = 0;
//			double lowestGrade = getFinalGrade();
//			for (int i = 0; i < FSCStudent.getNumStudents(); i++) {
//				if (students[i].getFinalGrade() != 0) {
////					FSCStudent student = students[i];
//					if (students[i].getFinalGrade() > highestGrade) {
//						highestGrade = students[i].getFinalGrade();
//					} else if (students[i].getFinalGrade() < lowestGrade) {
//						lowestGrade = students[i].getFinalGrade();
//					}
//					sumOfGrades += students[i].getFinalGrade();
//				}
//			}
//			averageGrade = sumOfGrades / Student.getNumStudents();
//			out.printf("Statistical Results of %s:\n", courseNumber);
//			out.printf("   Total number of student records: %d\n", Student.getNumStudents());
//			out.printf("   Average Score: %3.2f\n", averageGrade);
//			out.printf("   Highest Score: %3.2f\n", highestGrade);
//			out.printf("   Lowest Score:%7.2f\n", lowestGrade);
//
//			out.printf("   Total 'A' Grades: %3d (%6.2f%% of class)\n", gradeCounter(students, 'A'), (gradeCounter(students, 'A') / (double)Student.getNumStudents()) * 100);
//			out.printf("   Total 'B' Grades: %3d (%6.2f%% of class)\n", gradeCounter(students, 'B'), (gradeCounter(students, 'B') / (double)Student.getNumStudents()) * 100);
//			out.printf("   Total 'C' Grades: %3d (%6.2f%% of class)\n", gradeCounter(students, 'C'), (gradeCounter(students, 'C') / (double)Student.getNumStudents()) * 100);
//			out.printf("   Total 'D' Grades: %3d (%6.2f%% of class)\n", gradeCounter(students, 'D'), (gradeCounter(students, 'D') / (double)Student.getNumStudents()) * 100);
//			out.printf("   Total 'F' Grades: %3d (%6.2f%% of class)\n", gradeCounter(students, 'F'), (gradeCounter(students, 'F') / (double)Student.getNumStudents()) * 100);
//			out.println();
//		}

	}

	@Override
	public String toString() {
		String output = "";
		output += String.format("Course roster for %s\n", courseNumber);
		Student helpPtr = head;
		while (helpPtr != null) {
			output += helpPtr.toString();
			helpPtr = helpPtr.getNext();
		}
		return output;
	}
}
