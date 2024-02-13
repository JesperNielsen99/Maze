public class Maze {

    public void generateMaze() {
        String maze = """
                *************
                * * * *     *
                * *   * *** *
                *  S***     *
                * *     *** *
                * * *** *   *
                * * *   *** *
                * * *** * * *
                *         *G*
                *************
                """;

        char[][] mazeArray = new char[13][9];
        String[] lines = maze.split("\n");
        for (int i = 0; i < lines.length -1; i++) {
            for (int j = 0; j < lines[i].length() -1; j++) {
                char c = lines[i].charAt(j);
                mazeArray[i][j] = c;
                //System.out.print(c);
            }
            //System.out.println();
        }
        System.out.println(maze);
    }

}
