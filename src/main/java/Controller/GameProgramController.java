package Controller;

import Model.User;

import java.util.*;

public class GameProgramController {
    private static GameProgramController gameProgramController;
    private static User loginUser;

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User loginUser) {
        GameProgramController.loginUser = loginUser;
    }

    public static GameProgramController getInstance() {
        if (gameProgramController == null)
            gameProgramController = new GameProgramController();
        return gameProgramController;
    }

    public char[][] generateMaze(int height, int width) {
        char[][] maze = new char[height][width];
        for (int i = 0; i < height; i++) Arrays.fill(maze[i], '1');
        Random rand = new Random();
        int randomNumber1 = rand.nextInt(height);
        while (randomNumber1 % 2 == 0) randomNumber1 = rand.nextInt(height);
        int randomNumber2 = rand.nextInt(width);
        while (randomNumber2 % 2 == 0) randomNumber2 = rand.nextInt(width);
        maze[randomNumber1][randomNumber2] = '0';
        road(maze, randomNumber1, randomNumber2, width, height);
        maze[13][7] = '0';
        maze[1][13] = '0';
        maze[23][13] = '0';
        maze[23][1] = '0';
        maze[1][1] = '0';
        for (int j = 0; j < 200; ++j) {
            Random random = new Random();
            int randomNumberDelete1 = random.nextInt(24);
            int randomNumberDelete2 = random.nextInt(14);
            if (randomNumberDelete1 != 0 && randomNumberDelete2 != 0)
                maze[randomNumberDelete1][randomNumberDelete2] = '0';
        }
        return maze;
    }

    public void road(char[][] maze, int number1, int number2, int width, int height) {
        Integer[] randomDirections = randomNumber();
        for (int i = 0; i < 4; i++) {
            switch (randomDirections[i]) {
                case 1:
                    if (number1 - 2 <= 0) continue;
                    if (maze[number1 - 2][number2] != '0') {
                        maze[number1 - 2][number2] = '0';
                        maze[number1 - 1][number2] = '0';
                        road(maze, number1 - 2, number2, width, height);
                    }
                    break;
                case 2:
                    if (number2 + 2 >= width - 1) continue;
                    if (maze[number1][number2 + 2] != '0') {
                        maze[number1][number2 + 2] = '0';
                        maze[number1][number2 + 1] = '0';
                        road(maze, number1, number2 + 2, width, height);
                    }
                    break;
                case 3:
                    if (number1 + 2 >= height - 1) continue;
                    if (maze[number1 + 2][number2] != '0') {
                        maze[number1 + 2][number2] = '0';
                        maze[number1 + 1][number2] = '0';
                        road(maze, number1 + 2, number2, width, height);
                    }
                    break;
                case 4:
                    if (number2 - 2 <= 0) continue;
                    if (maze[number1][number2 - 2] != '0') {
                        maze[number1][number2 - 2] = '0';
                        maze[number1][number2 - 1] = '0';
                        road(maze, number1, number2 - 2, width, height);
                    }
                    break;
            }
        }

    }

    public Integer[] randomNumber() {
        ArrayList<Integer> randoms = new ArrayList<>();
        randoms.add(1);
        randoms.add(2);
        randoms.add(3);
        randoms.add(4);
        Collections.shuffle(randoms);
        return randoms.toArray(new Integer[4]);
    }
}
