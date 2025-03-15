package pdc.entities;

import java.util.List;
import java.util.UUID;

import net.datafaker.Faker;

/**
 * A factory class for generating random person
 * data.
 */
public class PersonFactory {

	/**
	 * Generates a random person with a unique UUID,
	 * address and, 0 to 4 email addresses.
	 * 
	 * @return
	 */
	public static Person randomPerson() {

		Faker faker = new Faker();
		Address address = new Address(
				faker.address().streetAddress(),
				faker.address().city(),
				faker.address().state(),
				faker.address().zipCode());
		Person p = new Person(
				UUID.randomUUID(),
				faker.name().firstName(),
				faker.name().lastName(), 
				address
				);
		
		//generate between 0 and 4 emails
		List<String> emails = faker.collection(
					() -> faker.internet().emailAddress())
				.len(0, 4)
				.generate();
		for(String email : emails) {
			p.addEmail(email);
		}
		
		return p;
	}
}
