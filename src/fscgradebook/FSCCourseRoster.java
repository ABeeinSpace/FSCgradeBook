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

	public Student insert() {

	}

	private Student insert(Student head, int id) {
		// IF there is no list, newNode will be the first node, so just return it
		if (head == null || head.getID() > id) {
//			head = new LLnode(data, head);
			return head;
		}

		// ELSE, we have a list. Insert the new node at the correct location
		else {
			// We need to traverse to the correct insertion location...so we need a help ptr
			Student helpPtr = head;
			// Traverse to correct insertion point
			while (helpPtr.getNext() != null) {
				if (helpPtr.getNext().getID() > id)
					break; // we found our spot and should break out of the while loop
				helpPtr = helpPtr.getNext();
			}
			// Now make the new node. Set its next to point to the successor node.
			// And then make the predecessor node point to the new node
			LLnode newNode = new LLnode(data, helpPtr.getNext());
			helpPtr.setNext(newNode);
		}
		// Return head
		return head;
	}

	public Student delete(int ID) {
		delete(head, ID);
	}

	private Student delete(Student head, int ID) {
		// We can only delete if the list has nodes (is not empty)
		if (!isEmpty()) {
			// IF the first node (at the head) has the data value we are wanting to delete
			// we found it. Delete by skipping the node and making head point to the next node.
			if (head.getData() == data) {
				head = head.getNext();
			}
			// ELSE, the data is perhaps somewhere else in the list...so we must traverse and look for it
			else {
				// We need to traverse to find the data we want to delete...so we need a help ptr
				LLnode helpPtr = head;
				// Traverse to correct deletion point
				while (helpPtr.getNext() != null) {
					if (helpPtr.getNext().getData() == data) {
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

	public void printStats() {

	}
}
