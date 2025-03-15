package pdc.entities;

/**
 * A class representing a physical address.
 * 
 */
public class Address {

	private final String street;
	private final String city;
	private final String state;
	private final String zip;

	public Address(String street, String city, String state, String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}
	
	public String toString() {
		return String.format("%s %s %s %s", this.street, this.city, this.state, this.zip);
	}

}
