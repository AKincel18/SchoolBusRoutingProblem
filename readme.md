# School Bus Routing Problem
A program that solves the problem of transporting students to school by school buses. 
Two algorithms have been implemented to solve the described problem:
* brute force algorithm
* nearest neighbour algorithm
## Input
The input files must be placed in the [*inputs*](https://github.com/AKincel18/SchoolBusRoutingProblem/tree/master/inputs) folder. You need three files with the following names: *Buses.txt*, *Pupils.txt*, and *Schools.txt*. Each file in the first two columns has the (x, y) coordinates of a given object (bus, student or school). In the third column:
* the *Buses.txt* file contains the bus capacity
* the *Pupils.txt* has the id of the school the student attends
* the *Schools.txt* file contains the school id
## Ouput
The program is console-based and the results appear on the console after the program ends. The routes of individual buses are presented, additionally the times and distances found by two different algorithms are compared.