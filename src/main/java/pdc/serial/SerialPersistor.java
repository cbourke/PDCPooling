package pdc.serial;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pdc.Persistor;
import pdc.database.ConnectionFactory;
import pdc.database.ConnectionProvider;
import pdc.entities.Person;
import pdc.entities.PersonFactory;
import pdc.entities.PersonPersistor;

public class SerialPersistor implements Persistor {

	private static final Logger LOGGER = LogManager.getLogger(SerialPersistor.class);

	private final int numRecords;
	private final ConnectionProvider connectionProvider;

	public SerialPersistor(int numRecords) {
		this.numRecords = numRecords;
		this.connectionProvider = new ConnectionFactory();
	}

	public void run() {

		LOGGER.info("Starting Serial Demo...");
		long start = System.currentTimeMillis();
		for (int i = 0; i < this.numRecords; i++) {
			Person p = PersonFactory.randomPerson();
			LOGGER.info("Inserting person #" + i + ": " + p.getUuid() + "...");
			Connection conn = this.connectionProvider.getConnection();
			PersonPersistor.persistPerson(p, conn);
			this.connectionProvider.putConnection(conn);
		}
		long end = System.currentTimeMillis();
		double time = (end - start) / 1000.0;
		System.out.printf("Done: %.2f seconds\n", time);

	}

}
