package pdc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory class that produces (and closes) connections
 * one-by-one.  Client code is responsible for invoking
 * {@link #putConnection(Connection)} to close connections.
 * 
 */
public class ConnectionFactory implements ConnectionProvider {

	private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);

	public Connection getConnection() {
		Connection conn = null;
		try {
			LOGGER.info("Initializing connection");
			conn = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
		} catch (SQLException e) {
			LOGGER.error("Unable to initialize connection", e);
			throw new RuntimeException(e);
		}
		return conn;
	}

	public void putConnection(Connection conn) {
		LOGGER.info("Closing connection");
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void shutdown() {} 

}
