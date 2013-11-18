import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/*
 * James Van Gaasbeck
 * PID: J2686979
 * COP 3503 : Computer Science II
 * Professor Sean Szumlanski
 * Assignment 7 : Maze Solver
 * Due: Wednesday, November 27, 11:59pm
 * 
 */

/*
 * To implement a recursive backtracking solution we need to first identify three cases.
 * 
 * 1. Reject: We are going the wrong way and the current path we are on won't lead us to the end. 
 * 		So we need to go back to where we first made the decision to go down this path.
 * 
 * 2. Accept: We have reached the end destination and we found the solution.
 * 
 * 3. Step: We are at a point between the start and the end, continue the next step, going over all possible options.
 */
public class Maze {

	// static array that will be set to the maze, and then manipulated during
	// the backtracking, and then set equal to the maze variable Sean gave us.
	public static char[][] myMaze = null;

	public static void solve(char[][] maze) {
		myMaze = maze;
		// boolean to end the backtracking if we found the path to the end
		boolean result = false;

		// driver loops
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				// make the call to our recursive function.
				if (walkTheMaze(i, j)) {
					// if this specific call to the recursive 'walkTheMaze()'
					// method
					// is true, that means we have found the exit, so set our
					// result
					// variable to true and break out of the inner for() loop.
					result = true;
					break;
				}
				// if the result is true, we've found the path, so break out of
				// the outer loop as well so we can return the correct
				// maze/path.
				if (result) {
					break;
				}
			}
		}
		// set the maze we were working with equal to the maze Sean wants.
		maze = myMaze;
	}

	// Recursive backtracking function.
	private static boolean walkTheMaze(int x, int y) {
		// boolean that will be set to true if the current recursive call was
		// the start of the maze, this is set to true to stop the recursive
		// nature of the method overwriting the first cells 's'.
		boolean wasStart = false;
		// rejection case.
		if (myMaze[x][y] == '#' || myMaze[x][y] == '.') {
			return false;
		}
		// acceptance case
		if (myMaze[x][y] == 'e') {
			return true;
		}
		// the start of the maze
		if (myMaze[x][y] == 's') {
			wasStart = true;
		} else {
			// otherwise, put a '.' in the cell to signify the path to the exit.
			myMaze[x][y] = '.';
		}

		// check to the right
		if (walkTheMaze(x, y + 1)) {
			return true;
		}
		// check above
		else if (walkTheMaze(x - 1, y)) {
			return true;
		}
		// check to the left
		else if (walkTheMaze(x, y - 1)) {
			return true;
		}
		// check below
		else if (walkTheMaze(x + 1, y)) {
			return true;
		}

		// make sure we don't overwrite the 's' in the starting cell of the maze
		if (!wasStart)
			myMaze[x][y] = ' ';

		// if it's not true return false.
		return false;
	}

	public static double difficultyRating() {
		return 2.5;
	}

	public static double hoursSpent() {
		return 3;
	}

	// A method for printing mazes.
	// seans code
	public static void printMaze(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j]);

			System.out.println();
		}

		System.out.println();
	}

	// this is seans code
	// A method for reading mazes from a text file into a 2D char array. Assumes
	// there are no blank lines in the input file, and all lines are the same
	// length. Returns a reference to the 2D char array containing the maze.
	public static char[][] loadMaze(String filename) throws Exception {
		Scanner in = new Scanner(new File(filename));
		Queue<String> q = new ArrayDeque<String>();

		while (in.hasNextLine())
			q.add(in.nextLine());

		char[][] maze = new char[q.size()][q.element().length()];

		for (int i = 0; !q.isEmpty(); i++) {
			String s = q.remove();

			for (int j = 0; j < s.length(); j++)
				maze[i][j] = s.charAt(j);
		}

		return maze;
	}

	// this is seans code
	// A method for comparing two 2D char arrays. Returns 'true' if they're
	// identical, 'false' otherwise.
	public static boolean mazeCompare(char[][] a, char[][] b) {
		if (a == null && b == null)
			return true;

		if (a == null || b == null)
			return false;

		if (a.length != b.length)
			return false;

		for (int i = 0; i < a.length; i++) {
			if (a[i].length != b[i].length)
				return false;

			for (int j = 0; j < a[i].length; j++)
				if (a[i][j] != b[i][j])
					return false;
		}

		return true;
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 6; i++) {
			// Load the test case maze and its solution.
			char[][] maze = null;
			try {
				maze = loadMaze("/Users/jjvg/Google Drive/Fall 2013/CS_2/Assignment 7/MazeSolver/TestCases/maze0"
						+ i + ".txt");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			char[][] soln = null;
			try {
				soln = loadMaze("/Users/jjvg/Google Drive/Fall 2013/CS_2/Assignment 7/MazeSolver/TestCases/soln0"
						+ i + ".txt");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Solve the maze. (This is the method you must write yourself.)
			Maze.solve(maze);

			// Check whether your solution is correct.
			System.out.println("Test Case #" + i + ": "
					+ (mazeCompare(maze, soln) ? "PASS" : "FAIL"));
		}
	}

}
