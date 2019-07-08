# ConcurrentProgramming - Java
This is my answer for WIF3003 Concurrent Programming Lab Exercises.
<br><br>
Lab 1 - Thread, Runnable, Synchronized Introduction<br>
Lab 2 - Sequencial and Concurrent Programming Exercise<br>
Lab 3 - Concurrent Programming using synchronization mechanism<br>
Lab 4 - Concurrent Programming using Lock Condition & Synchronized, ExecutorService Intoduction<br>
Lab 5 - Volatile and Atomic<br>
Lab 6 - Lambda expression Exercise<br>
Lab 7 - Parallel Programming using fork join framework<br>
<br>
<b>Extra Exercise on Fork and Join Framework</b>
1. Change Char to Numberic Value in String<br>
2. Fibonanci<br>
<br>

## Volatile variable
Write in main memory (not CPU cache) & Read from main memory (not CPU cache)
<br>

## Atomic
Do or Don't & Complete at once or not at all
<br>

## Visibility Guarantee
  1. If Thread A writes to a volatile variable and Thread B subsequently reads the same volatile variable, then all variables visible to Thread A before writing the volatile variable, will also be visible to Thread B after it has read the volatile variable. <br>
  2. If Thread A reads a volatile variable, then all all variables visible to Thread A when reading the volatile variable will also be re-read from main memory.
<br>

## Happens Before Guarantee
Ensure Visibility by avoiding reorder.
1. Writing to a volatile variable:
    * Read/Write a normal variable BEFORE writing to a volatile variable
	> Cannot be reordered after writing to a volatile variable<br>
 	> Writing to a normal variable must "Happen Before"
	* Read/Write a normal variable AFTER writing to a volatile variable	
	> May be reordered before writing to a volatile variable

2. Reading a volatile variable
	* Read/Write a normal variable AFTER reading a volatile variable
	> Cannot be reordered before reading of a volatile variable
	* Read/Write a normal variable BEFORE reading a volatile	
	> May be reordered after reading of a volatile variable<br>
	> Read a volatile variable must "Happen Before"



