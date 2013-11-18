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
 * Due: Sunday, November 17, 11:59pm
 * 
 */

/*
 * To implement a recursive backtracking solution we need to first identify three cases.
 * 1. Reject: We are going the wrong way and the current path we are on won't lead us to the end. 
 * 		So we need to go back to where we first made the decision to go down this path.
 * 2. Accept: We have reached the end destination and we found the solution.
 * 3. Step: We are at a point between the start and the end, continue the next step, going over all possible options.
 */
public class Maze {
	// static int x = 1;
	// static int y = 1;
	public static char[][] myMaze = null;

	public static void solve(char[][] maze) {
		myMaze = maze;
		boolean result = false;

		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				if(walkTheMaze(i, j)){
					result = true;
					break;
				}
				if(result){
					//myMaze[1][1] = 's';
					break;
				}
			}
		}
		// walkTheMaze(1,1);
		
		maze = myMaze;

	}

	private static boolean walkTheMaze(int x, int y) {
		boolean wasStart = false;
		// reject case
		if (myMaze[x][y] == '#' || myMaze[x][y] == '.') {
			return false;
		}

		if (myMaze[x][y] == 'e') {
			return true;
		}

		if (myMaze[x][y] == 's') {
			wasStart = true;
		} else {
			myMaze[x][y] = '.';
		}

		if (walkTheMaze(x, y + 1)) {
			return true;
		} else if (walkTheMaze(x - 1, y)) {
			return true;
		} else if (walkTheMaze(x, y - 1)) {
			return true;
		} else if (walkTheMaze(x + 1, y)) {
			return true;
		}
		
		if(!wasStart)
			myMaze[x][y] = ' ';

		return false;
	}

	public static double difficultyRating() {
		return 2.5;
	}

	public static double hoursSpent() {
		return 5;
	}

	// A method for printing mazes.
	public static void printMaze(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j]);

			System.out.println();
		}

		System.out.println();
	}

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
