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
		this.connections = new ArrayBlockingQueue<>(this.numConnections);
		this.initialize();
	}

	/**
	 * Initializes the connection pool in parallel.
	 */
	private void initialize() {
		LOGGER.info("Initializing " + this.numConnections + " connections in parallel...");
		for (int i = 0; i < this.numConnections; i++) {
			final int iter = i;
			Thread connectionThread = new Thread() {
				public void run() {
					try {
						LOGGER.info("Initializing connection number " + (iter + 1) + "...");
						Connection conn = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
						connections.offer(conn);
						LOGGER.info("Done initializing connection number " + (iter + 1) + "...");
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
			};
			connectionThread.start();
		}
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
		try {
			return connections.take();
		} catch (InterruptedException e) {
			LOGGER.error("Getting connection failed: ", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the given {@link Connection} to the connection pool.
	 * 
	 * @param conn
	 */
	public void putConnection(Connection conn) {
		try {
			connections.put(conn);
		} catch (InterruptedException e) {
			LOGGER.error("Getting connection failed: ", e);
			throw new RuntimeException(e);
		}
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
