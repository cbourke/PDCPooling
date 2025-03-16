package pdc.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pdc.Persistor;
import pdc.database.ConnectionPool;
import pdc.database.ConnectionProvider;

public class ParallelPersistor implements Persistor {

	private static final Logger LOGGER = LogManager.getLogger(ParallelPersistor.class);

	private final int numRecords;
	/**
	 * Used for both the number of workers (threads) and connections in the pool.
	 */
	private final int numThreads;
	private final ConnectionProvider connectionProvider;

	public ParallelPersistor(int numRecords, int numThreads) {
		this.numRecords = numRecords;
		this.numThreads = numThreads;
		this.connectionProvider = new ConnectionPool(numThreads);
	}

	@Override
	public void run() {
		long start, end;

		LOGGER.info("Starting Parallel Demo...");
		start = System.currentTimeMillis();

		ExecutorService threadPool = Executors.newFixedThreadPool(this.numThreads);

		LOGGER.info("Creating " + this.numThreads + " PersistorWorkers...");
		List<PersistorWorker> workers = new ArrayList<>();
		for (int i = 0; i < this.numThreads; i++) {
			workers.add(new PersistorWorker(i + 1, this.numRecords / this.numThreads, this.connectionProvider));
		}
		LOGGER.info("Starting " + this.numThreads + " PersistorWorkers...");
		for (PersistorWorker worker : workers) {
			threadPool.submit(worker);
		}
		try {
			// shut down...
			threadPool.shutdown();
			// and await for at most 1 hour if any workers are active
			threadPool.awaitTermination(1, TimeUnit.HOURS);
			end = System.currentTimeMillis();
			double time = (end - start) / 1000.0;
			System.out.printf("Done: %.2f seconds\n", time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			threadPool.shutdown();
			this.connectionProvider.shutdown();
		}

	}

}
