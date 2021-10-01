package fscgradebook;

public class Student {

	private String courseNumber;
	private int ID;
	private String firstName;
	private String lastName;
	private int[] examGrades;
	private double finalGrade;
	private char letterGrade;
	private static int numStudents;
	private Student next;

	//region Constructors
	public Student(String courseNumber, int ID, String firstName, String lastName, int[] examGrades, double finalGrade, char letterGrade) {
		this.courseNumber = courseNumber;
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.examGrades = examGrades;
		this.finalGrade = finalGrade;
		this.letterGrade = letterGrade;
		numStudents++;
	}

	/*Defined an empty constructor here, so we can create help pointers more easily.
	* NOTE: We don't increment numStudents here because we aren't technically creating a student that should be
	* recorded, we're just creating a pointer to a student. If we incremented, we're going to have a bad time later
	* on.*/
	public Student() {
	}
	//endregion


	//region Getters and Setters
	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int[] getExamGrades() {
		return examGrades;
	}

	public void setExamGrades(int[] examGrades) {
		this.examGrades = examGrades;
	}

	public double getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}

	public char getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(char letterGrade) {
		this.letterGrade = letterGrade;
	}



	public Student getNext() {
		return next;
	}

	public void setNext(Student next) {
		this.next = next;
	}
	//endregion

	//region Supporting Methods

	public static int getNumStudents() {
		return numStudents;
	}
	public static void decrementNumStudents() {
		numStudents--;
	}
	public static void incrementNumStudents() {
		numStudents++;
	}

	@Override
	public String toString() {
		String output = "";
		output = output + String.format("Student Record for %s %s (ID# %d):\n", firstName, lastName, ID);
		output = output + String.format("\tCourse: %s\n", courseNumber);
		output = output + String.format("\t\tExam 1:      % 2d\n", (int)examGrades[0]);
		output = output + String.format("\t\tExam 2:      % 2d\n", (int)examGrades[1]);
		output = output + String.format("\t\tFinal Exam:  % 2d\n", (int)examGrades[2]);
		output = output + String.format("\t\tFinal Grade:  %3.2f\n", finalGrade);
		output = output + String.format("\t\tLetter Grade:%2c\n", letterGrade);

		return output;
	}

	public String displayStudentsToString() {
		String output = "";
		output = output + String.format("\t%s %s (ID# %d):\n", firstName, lastName, ID);
		output = output + String.format("\t\tExam 1:      % 2d\n", (int)examGrades[0]);
		output = output + String.format("\t\tExam 2:      % 2d\n", (int)examGrades[1]);
		output = output + String.format("\t\tFinal Exam:  % 2d\n", (int)examGrades[2]);
		output = output + String.format("\t\tFinal Grade:  %3.2f\n", finalGrade);
		output = output + String.format("\t\tLetter Grade:%2c\n", letterGrade);

		return output;
	}
	//endregion
}
