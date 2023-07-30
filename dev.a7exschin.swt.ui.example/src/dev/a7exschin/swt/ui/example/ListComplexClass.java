package dev.a7exschin.swt.ui.example;

import java.util.LinkedList;

public class ListComplexClass {
	private String name;
	private String description;
	private LinkedList<String> assignedStrings;
	
	public LinkedList<String> getAssignedStrings() {
		return assignedStrings;
	}

	public void setAssignedStrings(LinkedList<String> assignedStrings) {
		this.assignedStrings = assignedStrings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ListComplexClass(String name, String description, LinkedList<String> list) {
		this.description = description;
		this.name = name;
		this.assignedStrings = list;
	}

}
