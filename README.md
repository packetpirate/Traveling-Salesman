# Traveling-Salesman
A graphical representation of a genetic algorithm designed to solve the Traveling Salesman problem.

## Description
The Traveling Salesman problem is a search problem in which, given a list of "cities", represented by (x,y) coordinate pairs, you must find the shortest distance you can travel to visit each city. This project implements a genetic algorithm described in [this article](http://www.theprojectspot.com/tutorial-post/applying-a-genetic-algorithm-to-the-travelling-salesman-problem/5).

I decided to use this as a learning project so I could implement a very basic genetic algorithm to familiarize myself with how they are structured. I went one step further and, using JavaFX, implemented a GUI to allow the user to add "cities" to the grid manually and then display the optimized path after the genetic algorithm has evolved the original population.

## Updates
 - THe user can now graph the results of the evolution process, showing the progress of the fittest member of each generation's distance. The data is displayed on a JavaFX LineChart generated in a separate window after the algorithm is run.
 - All UI components now have relevant functionality implemented. A clear button has also been added to allow the user to easily clear all cities from the grid and reset the fittest solution.

## TODO
 - Allow user to look at each generation by clicking on the timeline in the graph. This will allow you to view solutions for past generations.
