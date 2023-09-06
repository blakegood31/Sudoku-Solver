# Sudoku-Solver
This is a Java program that solves sudoku puzzles. To run the program, run the file "SudokuSolver.java", paste the mazes you would like to solve 
into the console, and hit enter to solve them. 

Input
---------------
- The input to the program is the number of mazes to be solved, followed with the mazes themselves
- Each line of the mazes represents a row of the maze
- Pre-filled spaces are represented by a number, and empty spaces are represented by an underscore '_'
- Example input:<br>
1<br>
\_\_\_\_\_\_\_\_4<br>
1\_\_\_\_9\_7\_<br>
\_\_37\_28\_\_<br>
\_\_\_\_7\_26\_<br>
4\_\_\_\_\_\_\_8<br>
\_91\_6\_\_\_\_<br>
\_\_42\_36\_\_<br>
\_3\_14\_\_\_9<br>
9\_\_\_\_\_\_\_\_<br>

- A test file containing 3 puzzles can be found in the file "Sudoku Solver Test Puzzles.txt

Output
----------
- After solving the mazes, the program will print out one of 3 things for each maze:
1) The total number of solutions to the sudoku if there are multiple solutions
2) The solution to the sudoku if there is only 1 solution
3) The sudoku doesn't have any solutions
