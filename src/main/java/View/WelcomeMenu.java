package View;

import Controller.GameProgramController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class WelcomeMenu extends Application {
    private static Scene scene;
    private static Stage stage;
    public static File mediaFile = new File("src/main/resources/musics/Danny-Cocke-Afterdark.mp3");
    public static Media media;

    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);
    public VBox vBoxExit;
    public VBox vBoxWelcome;
    public static boolean isMute;
    public Button muteButton;

    public void play(MouseEvent mouseEvent) throws Exception {
        User user = new User("gust");
        GameProgramController.setLoginUser(user);
        Life.getInstance().start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeMenu.stage = stage;
        URL welcomeUrl = getClass().getResource("/fxml/welcomePage.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        WelcomeMenu.scene = scene;
        stage.setScene(scene);
        root.setId("pageOne");
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setAutoPlay(true);
        stage.setTitle("Pacman");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/images/4916518.png")));
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.show();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void Exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void ExitFunction(MouseEvent mouseEvent) {
        vBoxExit.setVisible(true);
        vBoxWelcome.setVisible(false);
    }

    public void nothing(MouseEvent mouseEvent) {
        vBoxExit.setVisible(false);
        vBoxWelcome.setVisible(true);
    }

    public void mute(MouseEvent mouseEvent) {
        mediaPlayer.setMute(!isMute);
        if (!isMute)
            muteButton.setText("UnMute");
        else
            muteButton.setText("Mute");
        isMute = !isMute;
    }
}
