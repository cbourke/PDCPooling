package pdc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * A basic database connection pool.
 * 
 */
public class ConnectionPool implements ConnectionProvider {

	private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

	private static final int DEFAULT_NUM_CONNECTIONS = 10;

	private final int numConnections;
	private BlockingQueue<Connection> connections;

	public ConnectionPool() {
		this(DEFAULT_NUM_CONNECTIONS);
	}

	public ConnectionPool(int capacity) {
		this.numConnections = capacity;
	}

	/**
	 * Initializes the connection pool.
	 */
	private void initialize() {
		LOGGER.info("Initializing " + this.numConnections + " connections...");
		this.connections = new ArrayBlockingQueue<>(this.numConnections);
		for (int i = 0; i < this.numConnections; i++) {
			try {
				LOGGER.info("Initializing connection number " + (i + 1) + "...");
				Connection conn = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
				connections.offer(conn);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		LOGGER.info("Done initializing " + this.numConnections + " connections.");
	}

	/**
	 * Returns a database {@link Connection} from the pool (which should *not* be
	 * closed, but instead should be put back in the pool using
	 * {@link #putConnection()}). This method will block until a connection becomes
	 * available.
	 * 
	 * @return
	 */
	public Connection getConnection() {
		if(this.connections == null) {
			this.initialize();
		}
		return connections.poll();
	}

	/**
	 * Returns the given {@link Connection} to the connection pool.
	 * 
	 * @param conn
	 */
	public void putConnection(Connection conn) {
		connections.offer(conn);
	}

	/**
	 * Shuts down all connections currently in the pool.
	 * 
	 */
	public void shutdown() {

		while (!this.connections.isEmpty()) {
			LOGGER.info("Shutting down connection...");
			Connection c = connections.poll();
			try {
				c.close();
			} catch (SQLException e) {
				LOGGER.error(e);
				throw new RuntimeException(e);
			}
		}
	}

}
