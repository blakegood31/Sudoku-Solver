import java.io.*;
import java.util.*;

public class SudokuSolver 
{
	public static void main(String[] args)
	{
		/*
		 * Main method for the program. The main method processes the input from the console and
		 * translates it into the form used to solve the puzzles, solves the puzzles, and displays the results. 
		 * 
		 * Input from the console is stored in the ArrayList 'puzzles' after it has been processed into 
		 * 2-D arrays representing each puzzle 
		 */
		//Creates variables to be used with buffered reader
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		ArrayList<String> lines = new ArrayList<String>();
		int numLines = 100;
		int entries = 0;
		//Reads input from the console and adds each line to an array list as a string
		try {
			while((line = in.readLine()) != null && entries < numLines){ //While loop that reads each line of the input. "entries<count" makes the loop stop when we have read all lines of the puzzles
					lines.add(line);
					if(lines.size() == 1){
						numLines = (Integer.parseInt(lines.get(0)))*9; //Sets 'numLines' equal to the number of lines there are in the sudoku puzzles
					}
					else if(lines.size() > 1){
						entries++; //Counts how many lines of the sudoku puzzles we have read from the input
					}
				}
			in.close();
		}
		catch(IOException ioe) {
			System.out.println("ERROR: " + ioe);
		}
		//Changes all underscores to 0
		String puzzleRow = "";
		for(int i = 1; i<lines.size(); i++){
			puzzleRow = lines.get(i);
			for(int j = 0; j<(lines.get(i)).length(); j++){
				if(puzzleRow.charAt(j) == '_'){
					puzzleRow = puzzleRow.substring(0, j) + "0" + puzzleRow.substring(j + 1); //Replaces underscore with 0
				}
			}
			lines.set(i, puzzleRow);
		}
		
		//Fills an arrayList with 2D-Arrays. Each 2D Array represents a puzzle.
		ArrayList <int[][]> puzzles = new ArrayList<int[][]>();
		int lineCount = 1;
		for(int i = 0; i<Integer.parseInt(lines.get(0)); i++){
			int[][] puzzleTemp = new int[9][9]; //Temporary 2D array to be filled by the current puzzle
			for(int r = 0; r<9; r++){//This loop fills 'puzzleTemp' with all the numbers for the current puzzle
				for(int c = 0; c<9; c++){
					String tempStr = "" + lines.get(lineCount).charAt(c);
					int tempInt = Integer.parseInt(tempStr);
					puzzleTemp[r][c] = tempInt;
				}
				lineCount++; //Increments lineCount so we move to next line of input
			}
			puzzles.add(puzzleTemp); // Adds current puzzle to arrayList 'puzzles' once all cells have been filled
		}
	
		int ansIndex = 0; //Holds the index of the solved puzzle for the current sudoku in the arrayList 'answers' 
		//Runs the method to solve each puzzle
		for(int i = 0; i<puzzles.size(); i++){
			solutions = 0;
			sudokuSolver(puzzles.get(i), 0, 0);
			//Sets the index of the solved Sudoku in ArrayList 'answers' for current puzzle 
			if(ansIndex == 0 && solutions>0){
				ansIndex = solutions - 1;
			}
			else if(ansIndex>0 && solutions>0){
				ansIndex = ansIndex + solutions;
			}
			//Sets 2D array 'matrix' to the current solved sudoku if there is one. Otherwise it creates an empty matrix  
			int[][] matrix;
			if(answers.size()>0){
				matrix = answers.get(ansIndex);
			}
			else{
				matrix = new int[9][9];
			}
			//Outputs the solution to the console
			if(solutions>0){
				if(solutions == 1){//Prints out the solved puzzle if there is only 1 solution 
				System.out.println("Puzzle " + (i+1) + " solution is"); 
				for(int r = 0; r<9; r++){
					for(int c = 0; c<9; c++){
						System.out.print(matrix[r][c]);
					}
					System.out.println("");
				}
			}
				else if(solutions > 1){//Tells how many solutions there are if there is more than 1
					System.out.println("Puzzle " + (i+1) + " has " + solutions + " solutions");
				}
			}
			else if(solutions == 0){ //Prints out 'no solution' if puzzle cannot be solved
				System.out.println("Puzzle " + (i+1) + " has no solutions");
			}
			System.out.println("\n");
		}
	}
	
	static ArrayList<int[][]> answers = new ArrayList<int[][]>(); //Creates an arraylist that holds all the solved sudokus
	static int solutions = 0; //Creates an int 'solutions' that is used to count how many solutions each puzzle has 
	
	//Method that solves the puzzle
	public static boolean sudokuSolver(int[][] puzzle, int row, int col)
	{
		/*
		 * Recursive method that is used to solve the sudoku puzzles 
		 * 
		 * Input: A 2-D array representing the puzzles, and the current row and column to be solved 
		 * 
		 * Output: Boolean 'done'. Value is never used, just used for recursion 
		 */
		boolean lastNum = false; //Boolean that is set to true if the last cell of the puzzle is already filled in
		boolean done = false; //Boolean that is never set to true. Only used for recursion
		//Checks if cell being checked is already filled 
		if(puzzle[row][col] != 0){
			if(col == 8){
				if(row == 8){
					solutions++; //Adds 1 to number of solutions once base case has been met 
					lastNum = true; //Sets 'lastNum' to true when last cell of puzzle is already filled in (This helps avoid counting a puzzle as having extra solutions later on if the last cell is already filled in)
					//Puts the solved puzzle into arrayList 'answers' if a solution has been found 
					final int[][] tempPuz = new int[9][9];
					for(int r = 0; r<9; r++){
						for(int c = 0; c<9; c++){
							tempPuz[r][c] = puzzle[r][c];
						}
					}
					answers.add(tempPuz); //Adds the solved sudoku to 'answers'
				}
				//Moves on to first cell in the next row if current cell is filled and column = 8
				else{
					return sudokuSolver(puzzle, row+1, 0);
				}
			}
			//Moves on to next cell in the current row if current cell is filled and column < 8
			else{
				return sudokuSolver(puzzle, row, col+1);
			}
		}

		//For loop that checks all possible answers for current cell 
		for(int i = 1; i<10; i++){
			if(goodNum(i, row, col, puzzle) && lastNum == false){ //Checks if current number is valid answer for current cell 
				puzzle[row][col] = i; //Sets current cell to 'i' if 'i' is a valid number
				if(col == 8){
					if(row == 8){
						solutions++; //Adds 1 to number of solutions once base case has been met
						//Adds solved puzzle to an arraylist of answers if solution has been found 
						final int[][] tempPuz = new int[9][9];
						for(int r = 0; r<9; r++){
							for(int c = 0; c<9; c++){
								tempPuz[r][c] = puzzle[r][c];
							}
						}
						answers.add(tempPuz); //Adds 'tempPuz' to 'answers' arraylist
					}
					//Moves on to first cell in next row if a valid answer has been found for current cell and column = 8 to check answers for that cell
					else{
						done = sudokuSolver(puzzle, row+1, 0);
					}
				}
				//Moves on next cell in current row if a valid answer has been found for current cell and column < 8 to check answers for that cell
				else{
					done = sudokuSolver(puzzle, row, col+1);
				}
			}
				puzzle[row][col] = 0;
		}
		return done;
	}
	
	//Method to check if number is valid for current cell
	public static boolean goodNum(int num, int row, int col, int puzzle[][])
	{
		/*
		 * Method that checks to see if a number is valid for the current cell being solved 
		 * 
		 * Input: The number being checked, the current row and column being solved, a 2-D array representing the puzzle 
		 * 
		 * Output: True if the number being checked is a valid number, False otherwise 
		 */
		//Checks to see if number is found elsewhere in the current row
		for(int i = 0; i<9; i++){
			if(puzzle[row][i] == num){
				return false; //Returns false if number is found in current row 
			}
		}
		//Checks to see if number is found elsewhere in current column
		for(int i = 0; i<9; i++){
			if(puzzle[i][col] == num){
				return false; //Returns false if number is found in current column 
			}
		}
		//Checks to see if number is found elsewhere in current 3x3 grid
		int startRow = (row/3)*3;
		int startCol = (col/3)*3;
		for(int r = startRow; r<(startRow+3); r++){
			for(int c = startCol; c<(startCol+3); c++){
				if(puzzle[r][c] == num){
					return false; //Returns false if number is found in current 3x3 grid 
				}
			}
		}
		return true; //Returns true if number passes all the checks
	}
}
