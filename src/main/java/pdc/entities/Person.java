package pdc.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class representing a person, their address and emails.
 * 
 */
public class Person {

	private final Integer personId;
	private final UUID uuid;
	private final String firstName;
	private final String lastName;
	private final Address address;
	private final List<String> emails;

	public Person(Integer personId, UUID uuid, String firstName, String lastName, Address address) {
		this.personId = personId;
		this.uuid = uuid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = new ArrayList<>();
	}

	public Person(UUID uuid, String firstName, String lastName, Address address) {
		this(null, uuid, firstName, lastName, address);
	}

	public void addEmail(String email) {
		this.emails.add(email);
	}

	public Integer getPersonId() {
		return personId;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return address;
	}

	public List<String> getEmails() {
		return new ArrayList<>(emails);
	}

	public String toString() {
		return String.format("%s, %s (%s) %s\n\t%s", this.lastName, this.firstName, this.uuid, this.address,
				this.emails);
	}

}
