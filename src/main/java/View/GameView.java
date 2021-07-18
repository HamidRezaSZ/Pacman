package View;

import Controller.GameProgramController;
import Model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameView extends Application {
    private static GameView gameView;
    public static boolean isMute;
    public GridPane gridPane;
    public static ArrayList<Rectangle> allRectangles = new ArrayList<>();
    private static Stage stage;
    public HBox hBoxButtons;
    public static int counterGhostEaten = 1;
    public static Image blueGhost;

    static {
        try {
            blueGhost = new Image(new FileInputStream("src/main/resources/images/c292241148800102d08043992edcc033.gif"));
        } catch (FileNotFoundException ignored) {
        }
    }

    public Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(300), e -> {
                keyListener();
                move();
            })
    );
    public Timeline lose = new Timeline(
            new KeyFrame(Duration.millis(1), e -> {
                checkLose();
            })
    );
    public Timeline score = new Timeline(
            new KeyFrame(Duration.millis(1), e -> {
                checkBackground();
                earnScore();
                checkScore();
            })
    );

    public Timeline ghosts1 = new Timeline(
            new KeyFrame(Duration.millis(600), e -> {
                moveGhost1();

            })
    );
    public Timeline ghosts2 = new Timeline(
            new KeyFrame(Duration.millis(600), e -> {
                moveGhost2();
            })
    );
    public Timeline ghosts3 = new Timeline(
            new KeyFrame(Duration.millis(600), e -> {
                moveGhost3();
            })
    );
    public Timeline ghosts4 = new Timeline(
            new KeyFrame(Duration.millis(600), e -> {
                moveGhost4();
            })
    );

    public Timeline eatGhosts = new Timeline(
            new KeyFrame(Duration.millis(300), e -> {
                eatGhost();
            })
    );

    public static Rectangle rectangles = new Rectangle();
    public static boolean pauseGame;
    public Button pauseButton;
    public Button backButton;
    public static Rectangle rect = new Rectangle();
    public static Rectangle rectanglesOfGhost1 = new Rectangle();
    public static Rectangle rectOfGhost1 = new Rectangle();
    public static Rectangle rectanglesOfGhost2 = new Rectangle();
    public static Rectangle rectOfGhost2 = new Rectangle();
    public static Rectangle rectanglesOfGhost3 = new Rectangle();
    public static Rectangle rectOfGhost3 = new Rectangle();
    public static Rectangle rectanglesOfGhost4 = new Rectangle();
    public static Rectangle rectOfGhost4 = new Rectangle();
    public static ArrayList<KeyCode> random1 = new ArrayList<>();
    public static ArrayList<KeyCode> random2 = new ArrayList<>();
    public static ArrayList<KeyCode> random3 = new ArrayList<>();
    public static ArrayList<KeyCode> random4 = new ArrayList<>();
    private static Scene scene;
    public Button generateButton;
    public VBox vBoxGameOver;
    public Label lifeLabel2;
    public GridPane page;
    public static char[][] thisMap;
    public final int[] start1 = {7};
    public final int[] start2 = {15};
    public Button muteButton;
    public Label lifeFixedLabel2;
    public static ImagePattern imagePattern;

    static {
        try {
            imagePattern = new ImagePattern(new Image(new FileInputStream("src/main/resources/images/image_proc" +
                    "essing20200508-13712-16d9r1g.png")));
        } catch (FileNotFoundException ignored) {
        }
    }

    public static ImagePattern bombImage;

    static {
        try {
            bombImage = new ImagePattern(new Image(new FileInputStream("src/main/resources/images/7e291c03cf2" +
                    "2c6a615e2af44bc1ab78f.png")));
        } catch (FileNotFoundException ignored) {
        }
    }

    public Label scoreFixedLabel;
    public Label scoreLabel;
    public HBox lifeLabels2;
    public final int[] startGhostOne = {1, 1};
    public final int[] startGhostTwo = {1, 25};
    public final int[] startGhostThree = {13, 25};
    public final int[] startGhostFour = {13, 1};
    public static KeyCode direction = KeyCode.RIGHT;
    public static KeyCode ghostOneDirection;
    public static KeyCode ghostTwoDirection;
    public static KeyCode ghostThreeDirection;
    public static KeyCode ghostFourDirection;
    public Button chooseButton;
    public HBox hBoxScore;
    public static File mediaFile = new File("src/main/resources/musics/01 - Super Mario Bros.mp3");
    public static Media media;

    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);
    public VBox vBoxGameBoard;
    public Label saveLabel;
    public static Image img4;

    static {
        try {
            img4 = new Image(new FileInputStream("src/main/resources/images/031cecba98dce3de84d48de416137dde0d2" +
                    "34897_00.gif"));
        } catch (FileNotFoundException ignored) {
        }
    }

    public static Image img3;

    static {
        try {
            img3 = new Image(new FileInputStream("src/main/resources/images/Cyan-ghost.gif"));
        } catch (FileNotFoundException ignored) {
        }
    }

    public static Image img2;

    static {
        try {
            img2 = new Image(new FileInputStream("src/main/resources/images/YMXv.gif"));
        } catch (FileNotFoundException ignored) {
        }
    }

    public static Image img1;

    static {
        try {
            img1 = new Image(new FileInputStream("src/main/resources/images/N0f.gif"));
        } catch (FileNotFoundException ignored) {
        }
    }

    public static Image img;

    static {
        try {
            img = new Image(new FileInputStream("src/main/resources/images/zRERkjv.gif"));
        } catch (FileNotFoundException ignored) {
        }
    }

    public static GameView getInstance() {
        if (gameView == null)
            gameView = new GameView();
        return gameView;
    }

    static {
        random1.add(KeyCode.UP);
        random1.add(KeyCode.DOWN);
        random1.add(KeyCode.LEFT);
        random1.add(KeyCode.RIGHT);
        random2.add(KeyCode.UP);
        random2.add(KeyCode.DOWN);
        random2.add(KeyCode.LEFT);
        random2.add(KeyCode.RIGHT);
        random3.add(KeyCode.UP);
        random3.add(KeyCode.DOWN);
        random3.add(KeyCode.LEFT);
        random3.add(KeyCode.RIGHT);
        random4.add(KeyCode.UP);
        random4.add(KeyCode.DOWN);
        random4.add(KeyCode.LEFT);
        random4.add(KeyCode.RIGHT);
        Collections.shuffle(random1);
        Collections.shuffle(random2);
        Collections.shuffle(random3);
        Collections.shuffle(random4);
        ghostOneDirection = random1.get(0);
        ghostThreeDirection = random3.get(0);
        ghostFourDirection = random4.get(0);
        ghostTwoDirection = random2.get(0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeMenu.mediaPlayer.stop();
        rect.setFill(new ImagePattern(img));
        rect.setWidth(48);
        rect.setHeight(48);
        rectOfGhost1.setFill(new ImagePattern(img1));
        rectOfGhost1.setWidth(48);
        rectOfGhost1.setHeight(48);
        rectOfGhost2.setFill(new ImagePattern(img2));
        rectOfGhost2.setRotationAxis(Rotate.Y_AXIS);
        rectOfGhost2.setRotate(180);
        rectOfGhost2.setWidth(48);
        rectOfGhost2.setHeight(48);
        rectOfGhost3.setFill(new ImagePattern(img3));
        rectOfGhost3.setWidth(48);
        rectOfGhost3.setHeight(48);
        rectOfGhost4.setFill(new ImagePattern(img4));
        rectOfGhost4.setWidth(48);
        rectOfGhost4.setHeight(48);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setAutoPlay(true);
        URL gameViewUrl = getClass().getResource("/fxml/gameBoard.fxml");
        Parent root = FXMLLoader.load(gameViewUrl);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.setId("gameViewScene");
        GameView.scene = scene;
        GameView.stage = stage;
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
    }

    @FXML
    public void initialize() {
        gameBoard();
        lifeLabel2.setText(String.valueOf(Life.lifeCounter));
    }

    public void eatGhost() {
        if (start1[0] == startGhostOne[0] && start2[0] == startGhostOne[1]) {
            ghosts1.stop();
            gridPane.getChildren().remove(rectOfGhost1);
            startGhostOne[0] = 1;
            startGhostOne[1] = 1;
            gridPane.add(rectOfGhost1, 1, 1);
            rectanglesOfGhost1 = rectOfGhost1;
            ghosts1.setDelay(Duration.seconds(5));
            ghosts1.play();
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).increaseScore(200 * counterGhostEaten);
            ++counterGhostEaten;
        } else if (start1[0] == startGhostTwo[0] && start2[0] == startGhostTwo[1]) {
            ghosts2.stop();
            gridPane.getChildren().remove(rectOfGhost2);
            startGhostTwo[0] = 1;
            startGhostTwo[1] = 25;
            gridPane.add(rectOfGhost2, 25, 1);
            rectanglesOfGhost2 = rectOfGhost2;
            ghosts2.setDelay(Duration.seconds(5));
            ghosts2.play();
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).increaseScore(200 * counterGhostEaten);
            ++counterGhostEaten;
        } else if (start1[0] == startGhostThree[0] && start2[0] == startGhostThree[1]) {
            ghosts3.stop();
            gridPane.getChildren().remove(rectOfGhost3);
            startGhostThree[0] = 13;
            startGhostThree[1] = 25;
            gridPane.add(rectOfGhost3, 25, 13);
            rectanglesOfGhost3 = rectOfGhost3;
            ghosts3.setDelay(Duration.seconds(5));
            ghosts3.play();
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).increaseScore(200 * counterGhostEaten);
            ++counterGhostEaten;
        } else if (start1[0] == startGhostFour[0] && start2[0] == startGhostFour[1]) {
            ghosts4.stop();
            gridPane.getChildren().remove(rectOfGhost4);
            startGhostFour[0] = 13;
            startGhostFour[1] = 1;
            gridPane.add(rectOfGhost4, 1, 13);
            rectanglesOfGhost4 = rectOfGhost4;
            ghosts4.setDelay(Duration.seconds(5));
            ghosts4.play();
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).increaseScore(200 * counterGhostEaten);
            ++counterGhostEaten;
        }
    }

    public void gameBoard() {
        gridPane.getChildren().clear();
        allRectangles.clear();
        char[][] maze = GameProgramController.getInstance().generateMaze(2 * 13 + 1, 2 * 7 + 1);
        thisMap = maze;
        for (int i = 0; i < 27; ++i) {
            for (int j = 0; j < 15; ++j) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(48);
                rectangle.setWidth(48);
                if (maze[i][j] == '0') {
                    rectangle.setFill(Color.WHITE);
                } else {
                    rectangle.setFill(Color.BLACK);
                }
                allRectangles.add(rectangle);
                gridPane.add(rectangle, i, j);
            }
        }
    }

    public void showGameBoard(MouseEvent mouseEvent) {
        gameBoard();
    }

    public void moveGhost1() {
        if (ghostOneDirection.equals(KeyCode.RIGHT)) {
            if (thisMap[startGhostOne[1] + 1][startGhostOne[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost1);
                rectOfGhost1.setRotate(0);
                try {
                    gridPane.add(rectOfGhost1, startGhostOne[1] + 1, startGhostOne[0]);
                } catch (IllegalArgumentException ignored) {

                }
                rectanglesOfGhost1 = rectOfGhost1;
                ++startGhostOne[1];
            } else {
                Collections.shuffle(random1);
                ghostOneDirection = random1.get(0);
                moveGhost1();
            }
        } else if (ghostOneDirection.equals(KeyCode.LEFT)) {
            if (thisMap[startGhostOne[1] - 1][startGhostOne[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost1);
                rectOfGhost1.setRotationAxis(Rotate.Y_AXIS);
                rectOfGhost1.setRotate(180);
                try {
                    gridPane.add(rectOfGhost1, startGhostOne[1] - 1, startGhostOne[0]);
                } catch (IllegalArgumentException ignored) {

                }
                rectanglesOfGhost1 = rectOfGhost1;
                --startGhostOne[1];
            } else {
                Collections.shuffle(random1);
                ghostOneDirection = random1.get(0);
                moveGhost1();
            }
        } else if (ghostOneDirection.equals(KeyCode.UP)) {
            if (thisMap[startGhostOne[1]][startGhostOne[0] - 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost1);
                try {
                    gridPane.add(rectOfGhost1, startGhostOne[1], startGhostOne[0] - 1);
                } catch (IllegalArgumentException ignored) {

                }
                rectanglesOfGhost1 = rectOfGhost1;
                --startGhostOne[0];
            } else {
                Collections.shuffle(random1);
                ghostOneDirection = random1.get(0);
                moveGhost1();
            }
        } else if (ghostOneDirection.equals(KeyCode.DOWN)) {
            if (thisMap[startGhostOne[1]][startGhostOne[0] + 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost1);
                try {
                    gridPane.add(rectOfGhost1, startGhostOne[1], startGhostOne[0] + 1);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost1 = rectOfGhost1;
                ++startGhostOne[0];
            } else {
                Collections.shuffle(random1);
                ghostOneDirection = random1.get(0);
                moveGhost1();
            }
        }
        Collections.shuffle(random1);
        ghostOneDirection = random1.get(0);
    }

    public void moveGhost2() {
        if (ghostTwoDirection.equals(KeyCode.RIGHT)) {
            if (thisMap[startGhostTwo[1] + 1][startGhostTwo[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost2);
                rectOfGhost2.setRotationAxis(Rotate.Y_AXIS);
                rectOfGhost2.setRotate(180);
                try {
                    gridPane.add(rectOfGhost2, startGhostTwo[1] + 1, startGhostTwo[0]);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost2 = rectOfGhost2;
                ++startGhostTwo[1];
            } else {
                Collections.shuffle(random2);
                ghostTwoDirection = random2.get(0);
                moveGhost2();
            }
        } else if (ghostTwoDirection.equals(KeyCode.LEFT)) {
            if (thisMap[startGhostTwo[1] - 1][startGhostTwo[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost2);
                rectOfGhost2.setRotate(0);
                try {
                    gridPane.add(rectOfGhost2, startGhostTwo[1] - 1, startGhostTwo[0]);
                } catch (IllegalArgumentException ignored) {

                }
                rectanglesOfGhost2 = rectOfGhost2;
                --startGhostTwo[1];
            } else {
                Collections.shuffle(random2);
                ghostTwoDirection = random2.get(0);
                moveGhost2();
            }
        } else if (ghostTwoDirection.equals(KeyCode.UP)) {
            if (thisMap[startGhostTwo[1]][startGhostTwo[0] - 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost2);
                try {
                    gridPane.add(rectOfGhost2, startGhostTwo[1], startGhostTwo[0] - 1);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost2 = rectOfGhost2;
                --startGhostTwo[0];
            } else {
                Collections.shuffle(random2);
                ghostTwoDirection = random2.get(0);
                moveGhost2();
            }
        } else if (ghostTwoDirection.equals(KeyCode.DOWN)) {
            if (thisMap[startGhostTwo[1]][startGhostTwo[0] + 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost2);
                try {
                    gridPane.add(rectOfGhost2, startGhostTwo[1], startGhostTwo[0] + 1);
                } catch (IllegalArgumentException ignored) {

                }
                rectanglesOfGhost2 = rectOfGhost2;
                ++startGhostTwo[0];
            } else {
                Collections.shuffle(random2);
                ghostTwoDirection = random2.get(0);
                moveGhost2();
            }
        }
        Collections.shuffle(random2);
        ghostTwoDirection = random2.get(0);
    }

    public void moveGhost3() {
        if (ghostThreeDirection.equals(KeyCode.RIGHT)) {
            if (thisMap[startGhostThree[1] + 1][startGhostThree[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost3);
                rectOfGhost3.setRotate(0);
                try {
                    gridPane.add(rectOfGhost3, startGhostThree[1] + 1, startGhostThree[0]);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost3 = rectOfGhost3;
                ++startGhostThree[1];
            } else {
                Collections.shuffle(random3);
                ghostThreeDirection = random3.get(0);
                moveGhost3();
            }
        } else if (ghostThreeDirection.equals(KeyCode.LEFT)) {
            if (thisMap[startGhostThree[1] - 1][startGhostThree[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost3);
                rectOfGhost3.setRotationAxis(Rotate.Y_AXIS);
                rectOfGhost3.setRotate(180);
                try {
                    gridPane.add(rectOfGhost3, startGhostThree[1] - 1, startGhostThree[0]);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost3 = rectOfGhost3;
                --startGhostThree[1];
            } else {
                Collections.shuffle(random3);
                ghostThreeDirection = random3.get(0);
                moveGhost3();
            }
        } else if (ghostThreeDirection.equals(KeyCode.UP)) {
            if (thisMap[startGhostThree[1]][startGhostThree[0] - 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost3);
                try {
                    gridPane.add(rectOfGhost3, startGhostThree[1], startGhostThree[0] - 1);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost3 = rectOfGhost3;
                --startGhostThree[0];
            } else {
                Collections.shuffle(random3);
                ghostThreeDirection = random3.get(0);
                moveGhost3();
            }
        } else if (ghostThreeDirection.equals(KeyCode.DOWN)) {
            if (thisMap[startGhostThree[1]][startGhostThree[0] + 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost3);
                try {
                    gridPane.add(rectOfGhost3, startGhostThree[1], startGhostThree[0] + 1);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost3 = rectOfGhost3;
                ++startGhostThree[0];
            } else {
                Collections.shuffle(random3);
                ghostThreeDirection = random3.get(0);
                moveGhost3();
            }
        }
        Collections.shuffle(random3);
        ghostThreeDirection = random3.get(0);
    }

    public void moveGhost4() {
        if (ghostFourDirection.equals(KeyCode.RIGHT)) {
            if (thisMap[startGhostFour[1] + 1][startGhostFour[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost4);
                rectOfGhost4.setRotate(0);
                try {
                    gridPane.add(rectOfGhost4, startGhostFour[1] + 1, startGhostFour[0]);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost4 = rectOfGhost4;
                ++startGhostFour[1];
            } else {
                Collections.shuffle(random4);
                ghostFourDirection = random4.get(0);
                moveGhost4();
            }
        } else if (ghostFourDirection.equals(KeyCode.LEFT)) {
            if (thisMap[startGhostFour[1] - 1][startGhostFour[0]] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost4);
                rectOfGhost4.setRotationAxis(Rotate.Y_AXIS);
                rectOfGhost4.setRotate(180);
                try {
                    gridPane.add(rectOfGhost4, startGhostFour[1] - 1, startGhostFour[0]);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost4 = rectOfGhost4;
                --startGhostFour[1];
            } else {
                Collections.shuffle(random4);
                ghostFourDirection = random4.get(0);
                moveGhost4();
            }
        } else if (ghostFourDirection.equals(KeyCode.UP)) {
            if (thisMap[startGhostFour[1]][startGhostFour[0] - 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost4);
                try {
                    gridPane.add(rectOfGhost4, startGhostFour[1], startGhostFour[0] - 1);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost4 = rectOfGhost4;
                --startGhostFour[0];
            } else {
                Collections.shuffle(random4);
                ghostFourDirection = random4.get(0);
                moveGhost4();
            }
        } else if (ghostFourDirection.equals(KeyCode.DOWN)) {
            if (thisMap[startGhostFour[1]][startGhostFour[0] + 1] != '1') {
                gridPane.getChildren().remove(rectanglesOfGhost4);
                try {
                    gridPane.add(rectOfGhost4, startGhostFour[1], startGhostFour[0] + 1);
                } catch (IllegalArgumentException ignored) {
                }
                rectanglesOfGhost4 = rectOfGhost4;
                ++startGhostFour[0];
            } else {
                Collections.shuffle(random4);
                ghostFourDirection = random4.get(0);
                moveGhost4();
            }
        }
        Collections.shuffle(random4);
        ghostFourDirection = random4.get(0);
    }

    public void move() {
        if (direction.equals(KeyCode.RIGHT)) {
            if (thisMap[start2[0] + 1][start1[0]] != '1') {
                gridPane.getChildren().remove(rectangles);
                gridPane.add(rect, start2[0] + 1, start1[0]);
                rectangles = rect;
                ++start2[0];
            }
        } else if (direction.equals(KeyCode.LEFT)) {
            if (thisMap[start2[0] - 1][start1[0]] != '1') {
                gridPane.getChildren().remove(rectangles);
                gridPane.add(rect, start2[0] - 1, start1[0]);
                rectangles = rect;
                --start2[0];
            }
        } else if (direction.equals(KeyCode.UP)) {
            if (thisMap[start2[0]][start1[0] - 1] != '1') {
                gridPane.getChildren().remove(rectangles);
                gridPane.add(rect, start2[0], start1[0] - 1);
                rectangles = rect;
                --start1[0];
            }
        } else if (direction.equals(KeyCode.DOWN)) {
            if (thisMap[start2[0]][start1[0] + 1] != '1') {
                gridPane.getChildren().remove(rectangles);
                gridPane.add(rect, start2[0], start1[0] + 1);
                rectangles = rect;
                ++start1[0];
            }
        }
    }

    public void keyListener() {
        gridPane.requestFocus();
        GameView.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    direction = KeyCode.RIGHT;
                    rectangles.setRotate(0);
                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    direction = KeyCode.LEFT;
                    rectangles.setRotationAxis(Rotate.Y_AXIS);
                    rectangles.setRotate(180);
                } else if (event.getCode().equals(KeyCode.UP)) {
                    direction = KeyCode.UP;
                    rectangles.setRotationAxis(Rotate.Z_AXIS);
                    rectangles.setRotate(270);
                } else if (event.getCode().equals(KeyCode.DOWN)) {
                    direction = KeyCode.DOWN;
                    rectangles.setRotationAxis(Rotate.Z_AXIS);
                    rectangles.setRotate(90);
                }
            }
        });
    }

    public void map(MouseEvent mouseEvent) {
        generateButton.setVisible(false);
        pauseButton.setVisible(true);
        backButton.setVisible(true);
        chooseButton.setVisible(false);
        lifeLabel2.setText(String.valueOf(User.getUserByUsername(GameProgramController.getLoginUser().getUsername())
                .getHeart()));
        rectangles = rect;
        addRects();
        timeline.setCycleCount(Animation.INDEFINITE);
        lose.setCycleCount(Animation.INDEFINITE);
        ghosts1.setCycleCount(Animation.INDEFINITE);
        ghosts2.setCycleCount(Animation.INDEFINITE);
        ghosts3.setCycleCount(Animation.INDEFINITE);
        ghosts4.setCycleCount(Animation.INDEFINITE);
        eatGhosts.setCycleCount(30);
        score.setCycleCount(Animation.INDEFINITE);
        fillScores();
        lose.play();
        score.play();
        timeline.play();
        ghosts1.setDelay(Duration.seconds(2));
        ghosts2.setDelay(Duration.seconds(2));
        ghosts3.setDelay(Duration.seconds(2));
        ghosts4.setDelay(Duration.seconds(2));
        playGhosts();
    }

    private void addRects() {
        gridPane.add(rect, 15, 7);
        gridPane.add(rectOfGhost1, 1, 1);
        gridPane.add(rectOfGhost2, 25, 1);
        gridPane.add(rectOfGhost3, 25, 13);
        gridPane.add(rectOfGhost4, 1, 13);
        rectanglesOfGhost1 = rectOfGhost1;
        rectanglesOfGhost2 = rectOfGhost2;
        rectanglesOfGhost3 = rectOfGhost3;
        rectanglesOfGhost4 = rectOfGhost4;
    }

    public void checkLose() {
        if (start1[0] == startGhostOne[0] && start2[0] == startGhostOne[1] ||
                start1[0] == startGhostTwo[0] && start2[0] == startGhostTwo[1] ||
                start1[0] == startGhostThree[0] && start2[0] == startGhostThree[1] ||
                start1[0] == startGhostFour[0] && start2[0] == startGhostFour[1]) {
            timeline.pause();
            pauseGhosts();
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).decreaseHeart();
            lifeLabel2.setText(String.valueOf(User.getUserByUsername(GameProgramController.getLoginUser().getUsername())
                    .getHeart()));
            if (User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).getHeart() == 0) {
                timeline.stop();
                ghosts1.stop();
                ghosts2.stop();
                ghosts3.stop();
                ghosts4.stop();
                score.stop();
                gameView = null;
                vBoxGameBoard.getChildren().remove(hBoxButtons);
                vBoxGameBoard.getChildren().remove(hBoxScore);
                vBoxGameBoard.getChildren().remove(gridPane);
                vBoxGameOver.setVisible(true);
                vBoxGameOver.setMinHeight(300);
                vBoxGameOver.setMinWidth(300);
            }
            start1[0] = 7;
            start2[0] = 15;
            startGhostOne[0] = 1;
            startGhostOne[1] = 1;
            startGhostTwo[0] = 1;
            startGhostTwo[1] = 25;
            startGhostThree[0] = 13;
            startGhostThree[1] = 25;
            startGhostFour[0] = 13;
            startGhostFour[1] = 1;
            gridPane.getChildren().remove(rect);
            gridPane.getChildren().remove(rectOfGhost1);
            gridPane.getChildren().remove(rectOfGhost2);
            gridPane.getChildren().remove(rectOfGhost3);
            gridPane.getChildren().remove(rectOfGhost4);
            addRects();
            startAgain();
        }
    }

    public void playGhosts() {
        ghosts1.play();
        ghosts2.play();
        ghosts3.play();
        ghosts4.play();
    }

    public void pauseGhosts() {
        ghosts1.pause();
        ghosts2.pause();
        ghosts3.pause();
        ghosts4.pause();
    }

    private void startAgain() {
        GameView.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    direction = KeyCode.RIGHT;
                    rectangles.setRotate(0);
                    timeline.play();
                    playGhosts();

                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    direction = KeyCode.LEFT;
                    rectangles.setRotationAxis(Rotate.Y_AXIS);
                    rectangles.setRotate(180);
                    timeline.play();
                    playGhosts();
                } else if (event.getCode().equals(KeyCode.UP)) {
                    direction = KeyCode.UP;
                    rectangles.setRotationAxis(Rotate.Z_AXIS);
                    rectangles.setRotate(270);
                    timeline.play();
                    playGhosts();
                } else if (event.getCode().equals(KeyCode.DOWN)) {
                    direction = KeyCode.DOWN;
                    rectangles.setRotationAxis(Rotate.Z_AXIS);
                    rectangles.setRotate(90);
                    timeline.play();
                    playGhosts();
                }
            }
        });
    }

    public void checkScore() {
        int counter = 0;
        for (int i = 0; i < 27; ++i) {
            for (int j = 0; j < 15; ++j) {
                if (thisMap[i][j] == 'S') {
                    ++counter;
                    break;
                }
            }
            if (counter != 0)
                break;
        }
        if (counter == 0) {
            timeline.pause();
            pauseGhosts();
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).increaseHeart();
            lifeLabel2.setText(String.valueOf(GameProgramController.getLoginUser().getHeart()));
            fillScores();
            startAgain();
        }
    }

    public void fillScores() {
        for (int i = 0; i < 27; ++i) {
            for (int j = 0; j < 15; ++j) {
                if (thisMap[i][j] == '0') {
                    thisMap[i][j] = 'S';
                }
            }
        }
        for (int j = 0; j < 3; ++j) {
            Random random = new Random();
            int randomNumberDelete1 = random.nextInt(26);
            int randomNumberDelete2 = random.nextInt(14);
            if (randomNumberDelete1 != 0 && randomNumberDelete2 != 0)
                if (thisMap[randomNumberDelete1][randomNumberDelete2] == 'S') {
                    thisMap[randomNumberDelete1][randomNumberDelete2] = 'B';
                }
        }
    }

    public void checkBackground() {
        for (int i = 0; i < 27; ++i) {
            for (int j = 0; j < 15; ++j) {
                if (thisMap[i][j] == '0') {
                    allRectangles.get(i * 15 + j).setWidth(48);
                    allRectangles.get(i * 15 + j).setHeight(48);
                    allRectangles.get(i * 15 + j).setFill(Color.WHITE);
                } else if (thisMap[i][j] == 'S') {
                    allRectangles.get(i * 15 + j).setWidth(22);
                    allRectangles.get(i * 15 + j).setHeight(22);
                    allRectangles.get(i * 15 + j).setFill(imagePattern);

                } else if (thisMap[i][j] == 'B') {
                    allRectangles.get(i * 15 + j).setWidth(48);
                    allRectangles.get(i * 15 + j).setHeight(48);
                    allRectangles.get(i * 15 + j).setFill(bombImage);
                }
            }
        }
    }

    public void earnScore() {
        if (thisMap[start2[0]][start1[0]] == 'S') {
            thisMap[start2[0]][start1[0]] = '0';
            User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).increaseScore(5);
            scoreLabel.setText(String.valueOf(User.getUserByUsername(GameProgramController.getLoginUser().getUsername())
                    .getScore()));
        } else if (thisMap[start2[0]][start1[0]] == 'B') {
            thisMap[start2[0]][start1[0]] = '0';
            lose.stop();
            eatGhosts.play();
            rectOfGhost1.setFill(new ImagePattern(blueGhost));
            rectOfGhost2.setFill(new ImagePattern(blueGhost));
            rectOfGhost3.setFill(new ImagePattern(blueGhost));
            rectOfGhost4.setFill(new ImagePattern(blueGhost));
            Timeline blueGhosts = new Timeline(
                    new KeyFrame(Duration.millis(1), e -> {
                        rectOfGhost1.setFill(new ImagePattern(img1));
                        rectOfGhost2.setFill(new ImagePattern(img2));
                        rectOfGhost3.setFill(new ImagePattern(img3));
                        rectOfGhost4.setFill(new ImagePattern(img4));
                        counterGhostEaten = 1;
                    })
            );
            blueGhosts.setCycleCount(1);
            blueGhosts.setDelay(Duration.seconds(10));
            lose.setDelay(Duration.seconds(10));
            lose.play();
            blueGhosts.play();
        }
    }

    public void mainMenuGameOverFunction(MouseEvent mouseEvent) {
        stage.setScene(WelcomeMenu.getScene());
    }

    public void newGameGameOverFunction(MouseEvent mouseEvent) throws Exception {
        allRectangles.clear();
        gameView = null;
        GameView.getInstance().start(stage);
    }

    public void mute(MouseEvent mouseEvent) {
        mediaPlayer.setMute(!isMute);
        if (!isMute)
            muteButton.setText("UnMute");
        else
            muteButton.setText("Mute");
        isMute = !isMute;
    }

    public void pauseGame(MouseEvent mouseEvent) {
        if (!pauseGame) {
            timeline.pause();
            pauseButton.setText("Play");
            pauseGhosts();
        } else {
            timeline.play();
            pauseButton.setText("Pause");
            playGhosts();
        }
        pauseGame = !pauseGame;
    }

    public void back(MouseEvent mouseEvent) {
        timeline.stop();
        pauseGhosts();
        lose.stop();
        score.stop();
        allRectangles.clear();
        gameView = null;
        stage.setScene(WelcomeMenu.getScene());
    }
}
