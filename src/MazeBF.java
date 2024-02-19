import java.util.*;

public class MazeBF {
    private char[][] maze;
    private int rows, cols, endRow, endCol;
    private int startRow, startCol;
    private List<int[]> solutionPath;
    private final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public MazeBF(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.solutionPath = new ArrayList<>();
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
        // Priority queue for open cells to explore
        PriorityQueue<int[]> openCells = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        // Add the start cell to the open set with a heuristic cost
        openCells.offer(new int[]{startRow, startCol, manhattanDistance(startRow, startCol, endRow, endCol)});

        // Map to keep track of the cost to reach each cell
        int[][] cost = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
        }
        cost[startRow][startCol] = 0;

        // Map to keep track of the parent cell for each cell in the path
        int[][][] parent = new int[rows][cols][2];

        while (!openCells.isEmpty()) {
            int[] current = openCells.poll();
            int row = current[0];
            int col = current[1];
            int currentCost = current[2];

            // Check if we've reached the goal
            if (row == endRow && col == endCol) {
                // Reconstruct the path
                reconstructPath(parent);
                return true;
            }

            // Explore neighbors
            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                int newCost = currentCost + 1; // Cost of moving to a neighboring cell

                // Check if the neighbor is within bounds and can be visited
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && maze[newRow][newCol] != '*' && newCost < cost[newRow][newCol]) {
                    cost[newRow][newCol] = newCost;
                    int heuristic = manhattanDistance(newRow, newCol, endRow, endCol);
                    int totalCost = newCost + heuristic;
                    openCells.offer(new int[]{newRow, newCol, totalCost});
                    parent[newRow][newCol] = new int[]{row, col};
                }
            }
        }

        // No path found
        return false;
    }

    private void reconstructPath(int[][][] parent) {
        int row = endRow;
        int col = endCol;
        while (row != startRow || col != startCol) {
            solutionPath.add(new int[]{row, col});
            int[] current = parent[row][col];
            row = current[0];
            col = current[1];
        }
        solutionPath.add(new int[]{startRow, startCol}); // Add the start cell
        Collections.reverse(solutionPath); // Reverse the path to get it from start to end
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
        for (int i = 0; i <= solutionPath.size() - 1; i++) {
            int[] cell = solutionPath.get(i);
            if (cell == solutionPath.get(solutionPath.size()-1)) {
                System.out.print("(" + cell[0] + ", " + cell[1] + ")");
            } else {
                System.out.print("(" + cell[0] + ", " + cell[1] + "), ");
            }
        }
        System.out.println();
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
