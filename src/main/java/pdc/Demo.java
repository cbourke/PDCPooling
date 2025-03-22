package pdc;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import pdc.serial.SerialPersistor;
import pdc.parallel.ParallelPersistor;

/**
 * The main class to launch the demo application.
 * 
 */
public class Demo {

	public static void main(String args[]) {

		ArgumentParser parser = ArgumentParsers.newFor("Demo").build().defaultHelp(true)
				.description("Runs the PDC Pooling Demonstration.");
		parser.addArgument("--demoType").choices("serial", "parallel").setDefault("serial")
				.help("Which type to run, either serial or parallel.");
		parser.addArgument("--numberOfThreads").type(Integer.class).setDefault(10)
		.help("Number of threads/workers (parallel only)");
		parser.addArgument("--numberOfConnections").type(Integer.class).setDefault(5)
				.help("Number of connections (parallel only)");
		parser.addArgument("--numberOfRecords").type(Integer.class).setDefault(10)
				.help("Number of records to insert into the database.");

		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		}

		int numberOfConnections = ns.getInt("numberOfConnections");
		int numberOfThreads = ns.getInt("numberOfThreads");
		int numberOfRecords = ns.getInt("numberOfRecords");
		String demoType = ns.getString("demoType");

		Configurator.initialize(new DefaultConfiguration());
		Configurator.setRootLevel(Level.DEBUG);

		Persistor persistor = null;
		if (demoType.equals("serial")) {
			persistor = new SerialPersistor(numberOfRecords);
		} else if (demoType.equals("parallel")) {
			persistor = new ParallelPersistor(numberOfRecords, numberOfConnections, numberOfThreads);
		}
		persistor.run();

	}
}
