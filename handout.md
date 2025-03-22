
# PDC Pooling

This project demonstrates utilizing parallel computing as well
as connection pooling to speed up the persistence of records to a
database.

# Overview

Connecting to and interacting with a database server
takes a lot of computing resources.  In this module
we will look at two ways of improving performance when
working with a database: using thread workers and 
connection pooling.

## Multithreaded Work Distribution

Suppose we need to insert 100 records into a table.  
A serial application might insert one record at a time
which may take a noticeable amount of time.  However,
if we were to distribute those 100 tasks among (say)
10 threads where each thread was responsible for only
inserting 10 records each, we may reduce the required
time by up to 10% of what it took before.  

To do this, we can define a number of thread *workers* 
to distribute the workload.

## Database Connections

When connecting to a database the initial
connection setup is one of the most expensive operations
to be performed.  A network connection has to be established,
the user has to be authenticated, memory and other
resources have to be allocated on the server,  etc.  
There is additional overhead to properly close the connection as well.

In a serial application you may potentially have to open
(and close) the connection for each query you submit to
the database server.  This means paying the cost of creating
(and closing) the database connection **for every query**.

One way of reducing the amount of work overall is to *reuse*
a connection without closing it.  A serial application could
do this, but if a single connection is being passed around
it becomes unclear who is responsible for the connection, its
maintenance, its clean up, etc.  If the connection fails, 
it becomes the responsibility of *every* piece of code to ensure
that it is reopened or that the error is properly handled.

The management of connections becomes even more challenging in
a multithreaded application.  If multiple threads each 
require a connection, they would have to open (and manage) their
own.  If the number of threads exceeds the number of allowed
connections to the server this would quickly lead to resource
starvation.  

A good solution is to use a **connection pool**.  

## Connection Pooling

A *connection pool* is a cache of database connections.  
In general, an application will start a connection pool
by opening up (say) 10 connections and adding them to
the pool which the application will then use.  Instead
of opening a connection, individual methods or threads
will "check out" one of the connections from the pool.
When no longer needed, instead of closing it, the connection
is returned to the pool so that other threads may resuse it.

The pool is responsible for managing the connections.
For example, when all connections have been checked out
and are in use, it needs a way to manage further requests
especially in a multithreaded environment.  A general
solution will be for the pool to *block* the thread requesting
a connection until a connection has been returned to
the pool and becomes available.  The pool may also be
responsible for the initial creation and eventual *shutdown*
of the thread pool and its connections when the
application begins and ends respectively.

# Demonstration

We have provided a demonstration driver program, `pdc.Demo`
that you can run with several command line arguments.
The demonstration will randomly generate a specified
number of `Person` records to insert into a database including
the person's address and a collection of random emails 
(in separate tables).  The demonstration will output
an estimate of how long it took to insert all the records
(including creating and closing connections).

Before starting, you may need to setup the database and
connection (see `readme.md`).

## Serial Demonstration

To run the *serial* demonstration program, run the demo
program with the following command line arguments.  
This example runs the serial demonstration inserting 100 
records: 

`--demoType serial --numberOfRecords 100`

## Parallel Demonstration

To run the *serial* demonstration program, run the demo
program with the following command line arguments.  
This example runs the serial demonstration inserting 100 
records using 10 thread workers and 5 database connections
in the connection pool.

`--demoType parallel --numberOfThreads 10 --numberOfConnections 5 --numberOfRecords 100`

## Observations

1. Run the serial demonstration, inserting 10, 100, 1000 and
   record the time it took.  Then, without actually running 
   it, estimate how much time it would take to insert 1 million
   records.

2. Repeat the experiment with the parallel demonstration with
   10 threads and 5 connections (insert 10, 100, 1000 records) 
   and record the times it took for each.  Predict how much time 
   it would take to insert 1 million records.  Run the program 
   for 1 million records and see if your prediction was accurate.

## Activities

TODO