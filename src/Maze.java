import java.util.ArrayList;
import java.util.List;

public class Maze {
    private char[][] maze;
    private int rows, cols;
    private int startRow, startCol;
    private List<int[]> solutionPath;
    private boolean[][] visited;

    public Maze(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.solutionPath = new ArrayList<>();
        this.visited = new boolean[rows][cols];
        findStart();
    }

    private void findStart() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }
    }

    public boolean solveMaze() {
        return solveMaze(startRow, startCol);
    }

    public boolean solveMaze(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols || maze[row][col] == '*') {
            return false;
        }

        if (maze[row][col] == 'G') {
            solutionPath.add(new int[]{row, col});
            return true;
        }

        if (visited[row][col]) {
            return false;
        } else {
            visited[row][col] = true;
        }

        if (solveMaze(row, col + 1) || solveMaze(row + 1, col) || solveMaze(row, col - 1) || solveMaze(row - 1, col)) {
            solutionPath.add(new int[]{row, col});
            return true;
        }

        return false;
    }

    public void printSolution() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("Solution found: ");
        for (int i = solutionPath.size() - 1; i >= 0; i--) {
            int[] cell = solutionPath.get(i);
            if (cell == solutionPath.get(0)) {
             System.out.print("(" + cell[0] + ", " + cell[1] + ")");
            } else {
                System.out.print("(" + cell[0] + ", " + cell[1] + "), ");
            }
        }
    }

    public static void main(String[] args) {
        char[][] maze = {
                {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', ' ', ' ', ' ', ' ', '*'},
                {'*', ' ', '*', ' ', ' ', ' ', '*', ' ', '*', '*', '*', ' ', '*'},
                {'*', ' ', ' ', 'S', '*', '*', '*', ' ', ' ', ' ', ' ', ' ', '*'},
                {'*', ' ', '*', ' ', ' ', ' ', ' ', ' ', '*', '*', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', '*', '*', ' ', '*', ' ', ' ', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', ' ', ' ', '*', '*', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', '*', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*', 'G', '*'},
                {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'}
        };

        Maze solver = new Maze(maze);
        if (solver.solveMaze()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}