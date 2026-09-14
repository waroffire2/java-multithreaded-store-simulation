# Java Multithreaded Store Simulation

A Java concurrency simulation that models customers moving through a capacity-limited store using multiple threads and shared resources.

## Features

- Simulates 20 concurrent customer threads and a manager thread
- Enforces first-come, first-served store entry
- Limits the number of customers allowed inside the store
- Assigns customers to multiple self-checkout registers
- Models cafeteria tables and seat assignment
- Implements customer priority behavior
- Coordinates customer exit ordering
- Uses Java thread operations and atomic variables for synchronization

## Concurrency Concepts

This project demonstrates:

- Java Threads
- Thread coordination
- Race-condition prevention
- AtomicInteger and AtomicBoolean
- Busy waiting
- FCFS queues
- Shared resource management
- `join()`
- `yield()`
- `sleep()`

The synchronization logic was implemented without using `synchronized`, semaphores, or `wait()/notify()`.

## Files

- `Main.java` — starts the simulation and creates the threads
- `Store.java` — maintains shared store resources and state
- `CustomerThread.java` — controls individual customer behavior
- `ManagerThread.java` — controls manager behavior and store coordination

## Technologies

- Java
- Multithreading
- Concurrent programming
