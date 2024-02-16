import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MazeBF {
    private char[][] maze;
    private int rows, cols, endRow, endCol;
    private int startRow, startCol;
    private List<int[]> solutionPath;
    private boolean[][] visited;

    public MazeBF(char[][] maze) {
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
                } else if (maze[i][j] == 'G') {
                    endRow = i;
                    endCol = j;
                }
            }
        }
    }

    public boolean solveMazeBF() {
        return solveMazeBF(startRow, startCol);
    }

    public boolean solveMazeBF(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols || maze[row][col] == '*') {
            return false;
        }

        solutionPath.add(new int[]{row, col});
        visited[row][col] = false; // Unmark the cell as visited

        if (row == endRow && col == endCol) {
            return true;
        }

        // Calculate Manhattan distance for each neighbor
        PriorityQueue<int[]> neighbors = new PriorityQueue<>((a, b) ->
                manhattanDistance(a[0], a[1], endRow, endCol) - manhattanDistance(b[0], b[1], endRow, endCol));

        neighbors.offer(new int[]{row + 1, col}); // Down
        neighbors.offer(new int[]{row - 1, col}); // Up
        neighbors.offer(new int[]{row, col + 1}); // Right
        neighbors.offer(new int[]{row, col - 1}); // Left

        while (!neighbors.isEmpty()) {
            int[] next = neighbors.poll();
            if (solveMazeBF(next[0], next[1])) {
                return true;
            }
        }

        // If no solution found, backtrack
        solutionPath.remove(solutionPath.size() - 1);
        //visited[row][col] = false; // Unmark the cell as visited
        return false;
    }

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
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

        MazeBF solver = new MazeBF(maze);
        if (solver.solveMazeBF()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}
