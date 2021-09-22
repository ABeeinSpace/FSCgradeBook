package fscgradebook;

public class FSCCourseRoster {
	private Student head;
	private String courseNumber;

	public FSCCourseRoster(Student head, String courseNumber) {
		this.courseNumber = courseNumber;
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
		while (hp.getNext() != null) {
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
		while (hp.getNext() != null) {
			if (hp.getFirstName() == firstName) {
				/*We have to check the last name in case two students share a first name. */
				if (hp.getLastName() == lastName) {
					return true;
				}
				hp = hp.getNext();
			}
			hp = hp.getNext();
		}
		return false;
	}

	public int findNode() {

	}
	private int findNode() {

	}

	public Student insert() {

	}

	private Student insert() {

	}
	public Student delete() {

	}

	private Student delete() {

	}

	public void printRoster() {

	}

	public void printStats() {

	}
}
