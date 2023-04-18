package prueba;

public class RatInAMaze {

    static int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    static int[][] solution = new int[10][10];
    static int N = 10;

    public static void main(String[] args) {
        if (solveMaze(1, 1)) {
            for (int i = 1; i < N-1; i++) {
                for (int j = 1; j < N-1; j++) {
                    System.out.print(solution[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution found");
        }
    }

    public static boolean solveMaze(int x, int y) {
        if (x == 8 && y == 8) {
            solution[x][y] = 1;
            return true;
        }
        if (isSafe(x, y)) {
            solution[x][y] = 1;
            if (solveMaze(x + 1, y)) {
                return true;
            }
            if (solveMaze(x, y + 1)) {
                return true;
            }
            if (solveMaze(x - 1, y)) {
                return true;
            }
            if (solveMaze(x, y - 1)) {
                return true;
            }
            solution[x][y] = 0;
        }
        return false;
    }

    public static boolean isSafe(int x, int y) {
        if (x >= 1 && x < N-1 && y >= 1 && y < N-1 &&
                maze[x][y] == 0 &&
                solution[x][y] == 0) {
            return true;
        }
        return false;
    }
}

