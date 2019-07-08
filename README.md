# ConcurrentProgrammming - Java
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
Extra Exercise on Fork and Join Framework<br>
1. Change Char to Numberic Value in String<br>
2. Fibonanci<br>

<br>
<h2> Volatile variable </h2>
<p>	Write in main memory (not CPU cache) & Read from main memory (not CPU cache)</p>
<br>
<h2> Atomic </h2>
<p>	Do or Don't & Complete at once or not at all</p>
<br>
<h2>Visibility Guarantee</h2>
<p>
  1. If Thread A writes to a volatile variable and Thread B subsequently reads the same volatile variable, then all variables visible to Thread A before writing the volatile variable, will also be visible to Thread B after it has read the volatile variable. <br>
  2. If Thread A reads a volatile variable, then all all variables visible to Thread A when reading the volatile variable will also be re-read from main memory. </p>
<br>
<h2>Happens Before Guarantee</h2>
<p> Ensure Visibility by avoiding reorder <br>
   <h4>1. Writing to a volatile variable:</h4><br>
    a. Read/Write a variable BEFORE read a volatile<br>
		-> Cannot reorder after write of a volatile<br>
		-> must "Happen Before"<br>
		b. Read/Write a variable AFTER write a volatile<br>
		-> May reorder before write of a volatile<br>
  <br>
   <h4>2. Reading a volatile variable</h4><br>
  	a. Read/Write a variable AFTER read a volatile<br>
		-> Cannot reorder before read of a volatile<br>
		b. Read/Write a variable BEFORE read a volatile<br>
		-> May reorder after read of a volatile<br>
    -> Read a Volatile must "Happen Before"<br>
</p>

