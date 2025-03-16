package pdc.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class providing functionality to persist (save)
 * person records to the database.
 */
public class PersonPersistor {

	private static final Logger LOGGER = LogManager.getLogger(PersonPersistor.class);

	/**
	 * Creates a record in the person (and address and email tables) for the given
	 * person.
	 * 
	 * @param p
	 */
	public static void persistPerson(Person p, Connection conn) {

		LOGGER.info("Saving person " + p.getUuid() + "...");

		String insertPersonQuery = "insert into Person (uuid,firstName,lastName) values (?, ?, ?)";
		String insertAddressQuery = "insert into Address (personId, street,city,state,zip) values (?, ?, ?, ?, ?)";
		String insertEmailQuery = "insert into Email (address, personId) values (?, ?)";
		PreparedStatement ps = null;
		Integer personId = null;
		try {
			ps = conn.prepareStatement(insertPersonQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getUuid().toString());
			ps.setString(2, p.getFirstName());
			ps.setString(3, p.getLastName());
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			personId = keys.getInt(1);
			keys.close();
			ps.close();

			ps = conn.prepareStatement(insertAddressQuery);
			ps.setInt(1, personId);
			ps.setString(2, p.getAddress().getStreet());
			ps.setString(3, p.getAddress().getCity());
			ps.setString(4, p.getAddress().getState());
			ps.setString(5, p.getAddress().getZip());
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement(insertEmailQuery);
			ps.setInt(2, personId);
			for (String email : p.getEmails()) {
				ps.setString(1, email);
				ps.executeUpdate();
			}
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return;

	}

}
