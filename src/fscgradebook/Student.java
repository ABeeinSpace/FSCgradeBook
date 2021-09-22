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

	public Student(String courseNumber, int ID, String firstName, String lastName, int[] examGrades, double finalGrade, char letterGrade) {
		this.courseNumber = courseNumber;
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.examGrades = examGrades;
		this.finalGrade = finalGrade;
		this.letterGrade = letterGrade;
		numStudents++;
		this.next = null;
	}

	public Student() {
	}

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

	public void decrementNumStudents() {
		numStudents--;
	}
	public void incrementNumStudents() {
		numStudents++;
	}

	public Student getNext() {
		return next;
	}

	public void setNext(Student next) {
		this.next = next;
	}
}
