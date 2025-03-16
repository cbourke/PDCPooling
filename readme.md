
# PDC Pooling

This project demonstrates utilizing parallel computing as well
as connection pooling to speed up the persistence of records to a
database.

This module is part of the [PDC@UNL](https://go.unl.edu/PDCatUNL) 
project which aims to introduce Parallel and Distributed Computing
(PDC) topics and exercises in early computing education. 

## Setup

### Database

1. You'll need to install the database (available in `sql/database.sql`) in
   a network connected SQL database.
2. Update `pdc.database.Config` to provide your database's URL, username and password

### Running

* The main demo class is `pdc.Demo`
* Provide command line arguments to run either the serial (default) or 
  parallel and how many person records (default 10) to insert into 
  the database.    For the parallel demo, you can also specify how 
  many threads to run on.  Examples:
  
  * Serial demo with 42 records:  
  `--demoType serial --numberOfRecords 42`
  
  * Parallel demo with 10 threads and 100 records:    
  `--demoType parallel --numberOfThreads 10 --numberOfRecords 100`

## Resources

### Libraries

* Apache Log4j <https://logging.apache.org/log4j/2.x/index.html>
* Argparse4j 9.2.0 <https://argparse4j.github.io/>
* MySQL Connector/J 9.2.0 <https://dev.mysql.com/downloads/connector/j/>
* DataFaker 2.4.2 <https://github.com/datafaker-net/datafaker>