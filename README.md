README
=======
Description: A RushHour solver implemented in Java.  
Author:      Alkis Gotovos

Howto
------
* Build: make
* Clean: make clean
* Test:  test.sh number_of_threads test_id
* Run:   rush.sh

Arguments
----------
Script rush.sh reads input from stdin until the end-of-file is found.  
The input is formatted like the ML case with one extra (first) argument
for the number of threads.

An example input file is created when running make.  
(Run it using: rush.sh < input)
