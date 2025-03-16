package pdc.parallel;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pdc.database.ConnectionProvider;
import pdc.entities.Person;
import pdc.entities.PersonFactory;
import pdc.entities.PersonPersistor;

public class PersistorWorker implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(PersistorWorker.class);

	private final int id;
	private final int numRecords;
	private final ConnectionProvider connectionProvider;

	public PersistorWorker(int id, int numRecords, ConnectionProvider connectionProvider) {
		this.id = id;
		this.numRecords = numRecords;
		this.connectionProvider = connectionProvider;
	}

	@Override
	public void run() {

		LOGGER.info("Persistor Worker " + id + " starting: inserting " + this.numRecords + " people...");
		for (int i = 0; i < this.numRecords; i++) {
			LOGGER.info("Persistor Worker " + id + " adding person " + (i + 1) + "...");
			Connection conn = this.connectionProvider.getConnection();
			Person p = PersonFactory.randomPerson();
			PersonPersistor.persistPerson(p, conn);
			this.connectionProvider.putConnection(conn);
		}
		LOGGER.info("Persistor Worker " + id + " completed");

	}

}
