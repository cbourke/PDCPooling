package pdc.database;

import java.sql.Connection;

/**
 * Provides and possibly manages database connections.
 */
public interface ConnectionProvider {

	/**
	 * Provides a database connection 
	 * 
	 * @return
	 */
	public Connection getConnection();
	
	/**
	 * Manages the return of a connection (either by
	 * closing it or returning it to a shared pool).
	 * 
	 * @param conn
	 */
	public void putConnection(Connection conn);

	/**
	 * Shuts down any managed connections.
	 */
	public void shutdown();
}
